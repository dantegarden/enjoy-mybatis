package com.enjoy.myorm.orm.session;

import com.enjoy.myorm.orm.config.Configuration;

import java.util.List;

public interface SqlSession {
    /**
     * 根据传入的条件查询单一结果
     *
     * @param statement 方法对应的sql语句，namespace.id
     * @param parameter 要传入到sql语句中的查询参数
     * @return T
     */
    <T> T selectOne(String statement, Object parameter);

    /**
     * 查询集合
     *
     * @param statement 方法对应的sql语句，namespace.id
     * @param parameter 要传入到sql语句中的查询参数
     * @return java.util.List<E>
     */
    <E> List<E> selectList(String statement, Object parameter);

    /**
     * 更新
     *
     * @param statement 方法对应的sql语句，namespace.id
     * @param parameter 要传入到sql语句中的查询参数
     * @return java.util.List<E>
     */
    int update(String statement, Object parameter);

    /**
     * 获取mapper对象
     *
     * @param type 对象的类型
     * @return T
     */
    <T> T getMapper(Class<T> type);

    Configuration getConfiguration();
}
