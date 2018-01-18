package com.lark.middleware.task.chedule.test;

import com.lark.middleware.model.BusinessException;
import com.lark.middleware.model.enums.CommonAttachmentEnum;
import com.lark.middleware.task.schedule.api.ITask;
import com.lark.middleware.task.schedule.api.ITaskHandler;
import com.lark.middleware.task.schedule.api.result.IHandleResult;
import org.junit.Assert;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by houenxun on 16/8/23.
 */
@Component
public class TaskHandler1 implements ITaskHandler {

    //@Autowired
    //TaskScheduleConfig taskScheduleConfig;
    @Override
    public IHandleResult handle(ITask task) {
        Assert.assertTrue(this.getHandlerTypes().contains(task.getType()));
        if ((task.getExecCount() + task.getId()) % 2 == 0) {
            throw new BusinessException(CommonAttachmentEnum.PARAM_ERROR.getAttachment()).addParam("task.getExecCount()", task.getExecCount()).addParam("task.getId()", task.getId());
        }


        if ((task.getId()) % 3 == 0) {
            throw new BusinessException(CommonAttachmentEnum.PARAM_ERROR.getAttachment()).addParam("task.getId()", task.getId());
        }

        return IHandleResult.SUCCESS;

    }

    @Override
    public List<String> getHandlerTypes() {
        return Arrays.asList("test1", "test2");
    }
}
