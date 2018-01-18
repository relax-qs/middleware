package com.lark.middleware.task.schedule.core;

import com.lark.middleware.task.schedule.api.IComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by houenxun on 16/8/23.
 */
public class StatisticalInfo implements IComponent {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected int historySize = 16;
    protected ProcessInfo total; // 总的处理信息
    protected volatile ProcessInfo current; // 当前的处理信息
    // 历史的处理信息
    protected List<ProcessInfo> history = new LinkedList<>();

    protected Timer timer;

    /**
     * 是否是运行状态
     */
    protected volatile AtomicBoolean running = new AtomicBoolean(false);

    public ProcessInfo getTotal() {
        return total;
    }

    public StatisticalInfo() {

    }

    public void setTotal(ProcessInfo total) {
        this.total = total;
    }

    public ProcessInfo getCurrent() {
        return current;
    }

    public void setCurrent(ProcessInfo current) {
        this.current = current;
    }

    public List<ProcessInfo> getHistory() {
        return history;
    }

    public void setHistory(List<ProcessInfo> history) {
        this.history = history;
    }


    void addLoad() {
        current.addLoad();
        total.addLoad();
    }

    @Override
    public void stop() {
        if (!running.getAndSet(true)) {
            logger.warn("统计器已停止");
            return;
        }
        timer.cancel();
    }

    @Override
    public void start() {
        if (running.getAndSet(true)) {
            logger.warn("统计器已启动");
            return;
        }
        current = new ProcessInfo();
        history.add(current);
        total = new ProcessInfo();
        timer = new Timer();
        timer.schedule(new MyTask(), 0, 1000 * 30);
    }

    void addSuccess() {
        current.addSuccess();
    }

    void addFailed() {
        current.addFailed();
    }

    @Override
    public String toString() {
        return "StatisticalInfo{" +
                "total=" + total +
                ", current=" + current +
                ", history=" + history +
                '}';
    }

    class MyTask extends TimerTask {

        @Override
        public void run() {
            // 打印信息
            current = new ProcessInfo();
            history.add(current);
            if (history.size() > historySize) {
                history.remove(historySize - 1);
            }

            logger.warn("总的处理信息{}", total);

            logger.warn("每隔3分钟内的处理信息{}", history);

        }

    }


}
