package com.lark.middleware.mybatisgen.domain;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by houenxun on 16/4/15.
 * 列信息
 */
public class ColumnInfo {
    /**
     * 列名称
     */
    private String columnName;
    /**
     * 列对应的jdbc类型
     */
    private String jdbcType;
    /**
     * 列对应的java类型
     */
    private String javaType;
    private String getMethod;
    private String propName;

    private String setMethod;

    /**
     * 列类型 定义见ColumnTypeEnum
     */
    private String type;
    /**
     * 备注
     */
    private String comment;
    /**
     * 列最大长度
     */
    private Integer length;
    /**
     * 非空
     */
    private Boolean isNullable;
    /**
     * 默认值
     */
    private Object defaultValue;

    public void initColumnType(){
        for(ColumnTypeEnum typeEnum : ColumnTypeEnum.values()){
            if(typeEnum.getCode().equalsIgnoreCase(this.propName)){
                this.type = typeEnum.getCode();
            }
        }
        if(StringUtils.isBlank(type)){
            this.type = ColumnTypeEnum.normal.getCode();
        }
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getGetMethod() {
        return getMethod;
    }

    public void setGetMethod(String getMethod) {
        this.getMethod = getMethod;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }


    public String getSetMethod() {
        return setMethod;
    }

    public void setSetMethod(String setMethod) {
        this.setMethod = setMethod;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public String getSimpleComment(){
        if(StringUtils.isBlank(comment)){
            return null;
        }
        int start = comment.indexOf(" ");
        if (start <= 0) {
            start = comment.indexOf("   ");
        }
        if (start <= 0) {
            start = comment.indexOf(";");
        }

        if (start <= 0) {
            start = comment.indexOf(":");
        }
        if (start <= 0) {
            start = comment.indexOf(",");
        }
        if(start > 0){
            return comment.substring(0,start);
        }else{
            return comment;
        }

    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Boolean getNullable() {
        return isNullable;
    }

    public void setNullable(Boolean nullable) {
        isNullable = nullable;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "ColumnInfo{" +
                "columnName='" + columnName + '\'' +
                ", jdbcType='" + jdbcType + '\'' +
                ", javaType='" + javaType + '\'' +
                ", getMethod='" + getMethod + '\'' +
                ", propName='" + propName + '\'' +
                ", setMethod='" + setMethod + '\'' +
                ", type='" + type + '\'' +
                ", comment='" + comment + '\'' +
                ", length='" + length + '\'' +
                ", isNullable=" + isNullable +
                ", defaultValue=" + defaultValue +
                '}';
    }
}
