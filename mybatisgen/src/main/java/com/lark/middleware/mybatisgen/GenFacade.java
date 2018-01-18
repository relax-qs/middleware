package com.lark.middleware.mybatisgen;

import com.lark.middleware.mybatisgen.config.Config;
import com.lark.middleware.mybatisgen.config.ConfigBuilder;
import com.lark.middleware.mybatisgen.config.StreamHelper;

import java.io.InputStream;

/**
 * Created by houenxun on 16/4/14.
 * 门面类
 */
public class GenFacade {
    /**
     * 根据配置文件生产代码
     * @param configFile
     * @throws Exception
     */
    public static  void gen(String configFile) throws Exception{
        InputStream inputStream = StreamHelper.openStream(configFile);

        ConfigBuilder builder = new ConfigBuilder();
        Config config = builder.build(inputStream);
        System.out.println("read config success "+config);
        inputStream.close();
        MyBatisGenCore.batchGen(config);
        System.out.print("all done");
    }

    /**
     * 根据配置文件清理生成的配置文件
     * @version 1.0.7
     * @param configFile
     * @throws Exception
     */
    public static void clear(String configFile)throws Exception{

        InputStream inputStream = StreamHelper.openStream(configFile);

        ConfigBuilder builder = new ConfigBuilder();
        Config config = builder.build(inputStream);
        System.out.println("read config success "+config);
        inputStream.close();

        MyBatisGenCore.batchClear(config);
        System.out.print("all done");

    }
}
