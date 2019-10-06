package com.enjoy.myorm.orm.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.enjoy.myorm.orm.config.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @description: 数据源
 * @author: lij
 * @create: 2019-10-05 23:08
 */
public class ORMDataSource {

    private static DruidDataSource dataSource = ORMDataSourceInstance.dataSource;
    private ORMDataSource() { }

    public static class ORMDataSourceInstance {
        private static DruidDataSource dataSource = new DruidDataSource();
    }

    public static DataSource getInstance(Configuration configuration){
        dataSource.setDriverClassName(configuration.getDriver());
        dataSource.setUrl(configuration.getUrl());
        dataSource.setUsername(configuration.getUsername());
        dataSource.setPassword(configuration.getPassword());
        return dataSource;
    }

}
