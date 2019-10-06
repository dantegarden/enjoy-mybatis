package com.enjoy.myorm.orm.session;


import java.io.InputStream;
import java.io.Reader;

/**
 * @description: 建造者模式
 * @author: lij
 * @create: 2019-10-06 11:23
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream inputStream){
        return new SqlSessionFactory(inputStream);
    }

}
