package com.lark.middleware.task.schedule.api;

import com.lark.middleware.task.schedule.api.result.IHandleResult;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * Created by houenxun on 16/8/22.
 * 任务处理器
 */
public interface ITaskHandler extends InitializingBean {
    /**
     * 处理任务, 如果成功返回null 或者IHandleResult.SUCCESS;如果失败
     *
     * @param task
     * @return
     */
    IHandleResult handle(ITask task);

    /**
     * 获取处理的事件类型
     *
     * @return
     */
    List<String> getHandlerTypes();

    /**
     * 该方法不要被重装,否则可能出现处理器注册失败
     * @throws Exception
     */
    @Override
    default void afterPropertiesSet() throws Exception {
        List<String> typeList = this.getHandlerTypes();
        if (CollectionUtils.isNotEmpty(typeList)) {
            for (String type : typeList) {
                this.register(type);
            }
        }
    }

    /**
     * 该方法不要被重载
     * @param type
     */
     default  void register(String type) {
        TaskHandlerFactory.register(type, this);
    }


}
