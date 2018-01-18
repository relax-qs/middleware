package com.lark.middleware.mybatisgen.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by houenxun on 16/4/14.
 */
@XStreamAlias("config")
public class Config {

    private  DbConfig dbConfig;
    private Map<String, Object> params;

    private List<Generate> generates;
    private Set<String> tables;

    public DbConfig getDbConfig() {
        return dbConfig;
    }

    public void setDbConfig(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public List<Generate> getGenerates() {
        return generates;
    }

    public void setGenerates(List<Generate> generates) {
        this.generates = generates;
    }

    public Set<String> getTables() {
        return tables;
    }

    public void setTables(Set<String> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return "Config{" +
                "dbConfig=" + dbConfig +
                ", params=" + params +
                ", generates=" + generates +
                ", tables=" + tables +
                '}';
    }
}
