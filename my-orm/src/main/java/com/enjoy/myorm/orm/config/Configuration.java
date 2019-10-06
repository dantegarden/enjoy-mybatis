package com.enjoy.myorm.orm.config;

import com.enjoy.myorm.orm.binding.MappedInterface;
import com.enjoy.myorm.orm.datasource.ORMDataSource;
import lombok.Data;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:  读取mybatis-config.xml、所有配置文件，包括db.properties、mapper.xml
 * @author: lij
 * @create: 2019-10-05 22:41
 */
@Data
public class Configuration {
    private String driver;
    private String url;
    private String username;
    private String password;

    private Map<String, MappedStatement> statementMap = new HashMap<String, MappedStatement>();
    private Map<String, MappedInterface> interfaceMap = new HashMap<>();
    private DataSource dataSource;

    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }
}
