package com.lark.middleware.task.chedule.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.lark.middleware.task.schedule.api.ICollectorContext;
import com.lark.middleware.task.schedule.api.ITask;
import com.lark.middleware.task.schedule.api.ITaskCollector;
import com.lark.middleware.task.schedule.api.ITaskUpdator;
import com.lark.middleware.task.schedule.api.TaskStatusEnum;

/**
 * Created by houenxun on 16/8/23.
 */
@Component(value = "taskCollector")
public class TaskDAO implements ITaskCollector, ITaskUpdator {
    volatile List<ITask> list = new ArrayList<>();
    Object               lock = new Object();

    public TaskDAO() {
        for (int i = 0; i < 10000; i++) {
            TestTask testTask = new TestTask();
            testTask.setExecCount(0);
            testTask.setId((long) i);
            testTask.setType("test" + ((i % 4) + 1));
            testTask.setNextExecTime(new Date());
            testTask.setStatus(TaskStatusEnum.init.getCode());
            list.add(testTask);
        }

    }

    @Override
    public List<ITask> collectTasks(ICollectorContext context) {
        List<ITask> result = new ArrayList<>();
        synchronized (lock) {
            list.sort(((o1, o2) -> {
                Date d1 = ((TestTask) o1).getNextExecTime();

                Date d2 = ((TestTask) o2).getNextExecTime();

                return d1.compareTo(d2);
            }));

            Iterator<ITask> it = list.iterator();

            while (it.hasNext()) {
                TestTask task = (TestTask) it.next();
                if (task.getNextExecTime().before(new Date()) && task.getExecCount() < context.getMaxExecCount()
                    && task.getStatus() != TaskStatusEnum.success.getCode()) {
                    result.add(task);
                    it.remove();
                }
                if (result.size() >= context.getSingleFeatchCount()) {
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public boolean updateTask(ITask task) {
        synchronized (lock) {
            list.add(task);
        }
        return true;
    }

    @Override
    public boolean delayTaskFromNow(Long taskId, long duration, TimeUnit unit) {
        return false;
    }

    @Override
    public boolean delayTaskFromNextExecTime(Long taskId, long duration, TimeUnit unit) {
        return false;
    }

    @Override
    public int delayTaskFromNow(String bizType, String bizIdentify, long duration, TimeUnit unit) {
        return 0;
    }

    @Override
    public int delayTaskFromNextExecTime(String bizType, String bizIdentify, long duration, TimeUnit unit) {
        return 0;
    }
}
