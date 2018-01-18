package com.lark.middleware.task.schedule.core;

import com.lark.middleware.task.schedule.api.*;
import com.lark.middleware.util.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by houenxun on 16/8/22.
 * 任务调度器,不断的维护
 */
public class AutoConfigTaskSchedule implements ITaskSchedule, InitializingBean, DisposableBean {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 配置信息
     */
    protected TaskScheduleConfig taskScheduleConfig;
    /**
     * 配置组
     */
    protected String configGroup;
    /**
     * 配置项目监听器
     */
    protected ConfigListenner configListenner;

    /**
     * 任务收集
     */
    protected ITaskCollector taskCollector;
    /**
     * 任务更新器
     */
    protected ITaskUpdator taskUpdator;
    /**
     * 阻塞队列
     */
    protected BlockingQueue<Runnable> queue;


    /**
     * 任务收集线程
     */
    protected Thread collectorThread;
    /**
     * 任务缓存池
     */
    protected ITaskPool taskPool;

    protected NextExecTimeCalculator nextExecTimeCalculator;
    /**
     * 任务处理线程池
     */
    protected ThreadPoolExecutor threadPool;
    /**
     * 是否是运行状态
     */
    /**
     * 是否是运行状态
     */
    protected volatile AtomicBoolean running = new AtomicBoolean(false);


    protected Object startLock = new Object();

    protected static int collectorThreadId = 0;

    @Override
    public void stop() {
        synchronized (startLock) {
            if (!running.getAndSet(false)) {
                logger.warn("任务调度已经结束");
                return;
            }
            long beginTime = System.currentTimeMillis();
            logger.warn("开始停止调度器");
            if (null != queue) {
                queue.clear();
            }


            if (null != collectorThread) {
                collectorThread.interrupt();
            }

            if (null != threadPool) {
                try {
                    threadPool.shutdownNow();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
            if (null != taskPool) {
                taskPool.stop();
                taskPool = null;
            }
            logger.warn("完成停止调度器,  共耗时{}ms", System.currentTimeMillis() - beginTime);
        }
    }

    @Override
    public void start() {
        synchronized (startLock) {
            if (running.getAndSet(true)) {
                logger.warn("任务调度已经启动");
                return;
            }
            long beginTime = System.currentTimeMillis();
            logger.warn("开始启动调度器");
            /**
             * 获取当前机器的ip
             */
            String ip = IpUtil.getLocalIp();

            if (CollectionUtils.isNotEmpty(taskScheduleConfig.getExecIps()) && !taskScheduleConfig.getExecIps().contains(ip)) {
                logger.warn("ip白名单中不包含当前机器ip, 关闭schedule, execIps ={}", taskScheduleConfig.getExecIps());
                this.stop();
                return;
            }

            if (CollectionUtils.isNotEmpty(taskScheduleConfig.getNonExecIps()) && taskScheduleConfig.getNonExecIps().contains(ip)) {
                logger.warn("ip黑名单中包含当前机器ip, 关闭schedule, nonExecIps ={}", taskScheduleConfig.getNonExecIps());
                this.stop();
                return;
            }

            if (null == taskPool) {
                taskPool = new DefaultTaskPool();
            }
            taskPool.start();
            if (null == nextExecTimeCalculator) {
                nextExecTimeCalculator = new NextExecTimeCalculator();
            }
            // 启动拉取线程
            collectorThread = new Thread(new Collector());
            // 设置线程名称
            collectorThread.setName("schedule-collector-" + ++ collectorThreadId);
            collectorThread.start();
            
            /**
             * 初始化线程池和线程队列
             */
            queue = new LinkedBlockingDeque<>();
            threadPool = new ThreadPoolExecutor(0, taskScheduleConfig.getProcessThreadCount(), 1, TimeUnit.HOURS, queue);

            logger.warn("完成调度器的启动, 共耗时{}ms", System.currentTimeMillis() - beginTime);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.register();
        if(StringUtils.isNotBlank(configGroup)){
            if(null == taskScheduleConfig){
                taskScheduleConfig = new TaskScheduleConfig();
            }
            configListenner = new ConfigListenner(this, taskScheduleConfig, configGroup);
            configListenner.register();
        }

        this.start();
    }

    @Override
    public void destroy() throws Exception {
        this.stop();

        Thread.sleep(1000L);
    }

    public TaskScheduleConfig getTaskScheduleConfig() {
        return taskScheduleConfig;
    }

    public void setTaskScheduleConfig(TaskScheduleConfig taskScheduleConfig) {
        this.taskScheduleConfig = taskScheduleConfig;
    }

    public ITaskCollector getTaskCollector() {
        return taskCollector;
    }

    public void setTaskCollector(ITaskCollector taskCollector) {
        this.taskCollector = taskCollector;
    }

    public ITaskUpdator getTaskUpdator() {
        return taskUpdator;
    }

    public void setTaskUpdator(ITaskUpdator taskUpdator) {
        this.taskUpdator = taskUpdator;
    }

    /**
     * 任务拉取线程
     */
    class Collector implements Runnable {
        @Override
        public void run() {
            try {
                /**
                 * 启动后先休眠X秒钟再开始拉取任务,防止处理器未注册
                 */
                Thread.sleep(taskScheduleConfig.getWaitTimeOnStart() * 1000);
            } catch (Throwable t) {
                logger.error("", t);
            }
            while (running.get()) {
                try {
                    /**
                     * 获取任务池中的任务数量,并计算休眠的时间
                     */
                    int count = taskPool.size();

                    if (count > taskScheduleConfig.getMaxQueueSize()) {
                        logger.error("线程队列已满,拉取线程休眠");
                        Thread.sleep(taskScheduleConfig.getWaitTimeIfMaxQueu());
                        continue;
                    } else if (count > taskScheduleConfig.getMinQueueSize()) {
                        Thread.sleep(taskScheduleConfig.getDefaultWaitTime());
                    }

                    /**
                     * 获取正在处理的任务列表,防止拉取时,这部分任务被重复拉取
                     */

                    ICollectorContext context = assemblyCollectionContext(taskScheduleConfig, new ArrayList<>(taskPool.getIds()));

                    /**
                     * 进行任务拉取
                     */
                    List<ITask> list = taskCollector.collectTasks(context);

                    if (CollectionUtils.isEmpty(list)) {
                        /**
                         * 没有任务进行休眠
                         */
                        logger.info("当前没有任务,拉取线程休眠 time={}, context={}", taskScheduleConfig.getWeitTimeIfNoTask(), context);
                        Thread.sleep(taskScheduleConfig.getWeitTimeIfNoTask());
                    } else {
                        /**
                         * 将任务添加到执行队列中
                         */
                        logger.info("本次拉取{}个任务, context={}", list.size(), context);
                        for (ITask task : list) {
                            threadPool.execute(new RunnableTask(task, taskPool, nextExecTimeCalculator, taskUpdator, taskScheduleConfig));
                        }

                    }
                } catch (Throwable t) {
                    logger.error("拉取线程处理异常", t);
                }

            }


        }
    }

    /**
     * 组装查询context
     *
     * @param taskScheduleConfig
     * @param excludeIds
     * @return
     */

    protected ICollectorContext assemblyCollectionContext(TaskScheduleConfig taskScheduleConfig, List<Long> excludeIds) {
        DefaultCollectorContext context = new DefaultCollectorContext();
        context.setExcludeIds(excludeIds);

        context.setCurrentHashCodes(taskScheduleConfig.getCurrentHashCodes());
        context.setExecIps(taskScheduleConfig.getExecIps());
        context.setNonExecIps(taskScheduleConfig.getNonExecIps());
        context.setMaxExecCount(taskScheduleConfig.getMaxExecCount());
        context.setSingleFeatchCount(taskScheduleConfig.getSingleFeatchCount());
        return context;
    }

    public String getConfigGroup() {
        return configGroup;
    }

    public void setConfigGroup(String configGroup) {
        this.configGroup = configGroup;
    }
}


