package com.lark.middleware.mybatisgen.config;

import java.util.Map;

/**
 * Created by houenxun on 16/4/14.
 */
public class Generate {
    private String id;
    private String target;
    private String template;
    private Map<String, String> params;
    /**
     * 创建策略 CreateStrategyEnum
     */
    private String createStrategy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getCreateStrategy() {
        return createStrategy;
    }

    public void setCreateStrategy(String createStrategy) {
        this.createStrategy = createStrategy;
    }

    @Override
    public String toString() {
        return "Generate{" +
                "id='" + id + '\'' +
                ", target='" + target + '\'' +
                ", template='" + template + '\'' +
                ", params=" + params +
                ", createStrategy='" + createStrategy + '\'' +
                '}';
    }


}
