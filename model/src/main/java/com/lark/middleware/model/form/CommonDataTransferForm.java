/**
 * hnlark.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.lark.middleware.model.form;

import java.util.Date;

/**
 * 
 * @author MOUBK
 * @version $Id: CommonDataTransferForm.java, v 0.1 2016年4月27日 下午3:59:23 MOUBK Exp $
 */
public class CommonDataTransferForm extends SearchForm {

    private Long   id;

    private String code;

    private String keyWord;

    private Date   startTime;

    private Date   endTime;

    private String type;

    private Long   buyerId;

    private Long   userId;

    public CommonDataTransferForm() {

    }

    public CommonDataTransferForm(SearchForm searchForm) {
        setPageNo(searchForm.getPageNo());
        setPageSize(searchForm.getPageSize());
        setName(searchForm.getName());
        setStatus(searchForm.getStatus());
    }

    public CommonDataTransferForm(PageForm pageForm) {
        setPageNo(pageForm.getPageNo());
        setPageSize(pageForm.getPageSize());
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
