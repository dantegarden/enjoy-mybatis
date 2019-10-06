package com.enjoy.myorm.orm.session;

import com.enjoy.myorm.orm.binding.MappedProxy;
import com.enjoy.myorm.orm.config.Configuration;
import com.enjoy.myorm.orm.config.MappedStatement;
import com.enjoy.myorm.orm.executor.DefaultExecutor;
import com.enjoy.myorm.orm.executor.Executor;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @description:
 * @author: lij
 * @create: 2019-10-05 22:54
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    private final Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        super();
        this.configuration = configuration;
        this.executor = new DefaultExecutor(configuration);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        List<T> list = this.selectList(statement, parameter);
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new RuntimeException("query do not return a single result");
        }
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        MappedStatement smt = this.configuration.getStatementMap().get(statement);
        return executor.query(smt, parameter);
    }

    @Override
    public int update(String statement, Object parameter) {
        MappedStatement smt = this.configuration.getStatementMap().get(statement);
        return executor.update(smt, parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        MappedProxy mappedProxy = new MappedProxy(this);
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, mappedProxy);
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }
}
