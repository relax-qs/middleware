package com.lark.middleware.mybatisgen.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by houenxun on 16/4/14.
 */
@XStreamAlias("dbConfig")
public class DbConfig {
    private String driver;
    private String url;
    private String user;
    private String pwd;

    private String tablePrefix;

    private String schema ;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    @Override
    public String toString() {
        return "DbConfig{" +
                "driver='" + driver + '\'' +
                ", url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", pwd='" + pwd + '\'' +
                ", tablePrefix='" + tablePrefix + '\'' +
                ", schema='" + schema + '\'' +
                '}';
    }
}
