package com.lark.middleware.model.form;

import java.util.Date;

/**
 * Created by houenxun on 16/7/15.
 * 带有时间间隔的分页form
 */
public class IntervalPageForm extends PageForm {
    private Date startTime;
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        if(null != startTime && startTime > 0) {
            this.startTime = new Date(startTime);
        }
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        if(null != endTime && endTime > 0) {
            this.endTime = new Date(endTime);
        }
    }
}
