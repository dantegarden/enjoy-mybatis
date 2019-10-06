package com.enjoy.myorm.orm.executor;

import com.enjoy.myorm.orm.config.MappedStatement;

import java.util.List;
/**
 * 核心接口之一，定义了数据库操作最基本的方法
 */
public interface Executor {

    <E> List<E> query(MappedStatement statement, Object parameter);

    int update(MappedStatement statement, Object parameter);
}
