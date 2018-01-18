package com.lark.middleware.model.form;


import com.lark.middleware.model.query.BaseQuery;

/**
 * Created by houenxun on 16/1/29.
 *
 * 分页相关的基础form
 */
public class PageForm {
    protected Integer pageSize = BaseQuery.DEFAULT_PAGE_SIZE;
    protected Integer pageNo = BaseQuery.DEFAULT_PAGE;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
