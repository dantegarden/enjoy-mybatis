package com.enjoy.quickstart.generator;

import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: java程序执行mybatis代码生成器
 * @author: lij
 * @create: 2019-09-28 21:16
 */
public class MyBatisGenerator {

    public static void generate(boolean overwrite){
        List<String> warnings = new ArrayList<>();
        String genCfg = "generatorConfig.xml";
        File configFile = new File(MyBatisGenerator.class.getClassLoader().getResource(genCfg).getFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(configFile);
        } catch (XMLParserException | IOException e) {
            e.printStackTrace();
        }
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        org.mybatis.generator.api.MyBatisGenerator myBatisGenerator = null;
        try {
            myBatisGenerator = new org.mybatis.generator.api.MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (InvalidConfigurationException | SQLException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
