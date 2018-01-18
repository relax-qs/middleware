package com.lark.middleware.task.schedule.web;

import com.lark.middleware.task.schedule.api.ITaskSchedule;
import com.lark.middleware.task.schedule.api.ScheduleFactory;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * Created by houenxun on 16/9/28.
 */
public class ScheduleContextListener implements ServletContextListener {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 停止任务调度
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        long beginTime = System.currentTimeMillis();
        logger.warn("开始停止所有的调度器");
        List<ITaskSchedule> scheduleList = ScheduleFactory.getSchedules();
        if (CollectionUtils.isNotEmpty(scheduleList)) {
            for (ITaskSchedule schedule : scheduleList) {
                schedule.stop();
            }
        }

        logger.warn("接收停止所有的调度器,共耗时{}ms", System.currentTimeMillis() - beginTime);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.warn("容器开始启动");
    }
}
