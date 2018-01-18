package com.lark.middleware.mybatisgen.config;

import com.lark.middleware.mybatisgen.CreateStrategyEnum;
import com.thoughtworks.xstream.XStream;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by houenxun on 16/4/14.
 */
public class ConfigBuilder {
    public ConfigBuilder() {
    }

    public Config build(InputStream inputStream) throws Exception {
        XStream xstream = new XStream();
        xstream.alias("config", Config.class);
        xstream.alias("params", Map.class);
        xstream.alias("param", Map.Entry.class);
        xstream.alias("name", String.class);
        xstream.alias("value", String.class);
        xstream.alias("generates", List.class);
        xstream.alias("gen", Generate.class);
        xstream.alias("tables", Set.class);
        xstream.alias("table", String.class);
        Config config = (Config) xstream.fromXML(inputStream);
        this.check(config);
        return config;
    }

    private void check(Config config) {
        for (Generate generate : config.getGenerates()) {
            CreateStrategyEnum createStrategyEnum = CreateStrategyEnum.valueOf(generate.getCreateStrategy());
            if (null == createStrategyEnum) {
                throw new RuntimeException("生成策略不存在 generate=" + generate);
            }
        }

    }
}

