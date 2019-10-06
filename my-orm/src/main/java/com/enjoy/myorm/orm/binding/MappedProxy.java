package com.enjoy.myorm.orm.binding;

import com.enjoy.myorm.orm.config.MappedStatement;
import com.enjoy.myorm.orm.config.enums.MapperMethodEnum;
import com.enjoy.myorm.orm.session.SqlSession;
import com.enjoy.myorm.orm.util.ReflectUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;

/**
 * @description:
 * @author: lij
 * @create: 2019-10-05 23:03
 */
public class MappedProxy implements InvocationHandler {

    private SqlSession sqlSession;

    public MappedProxy(SqlSession sqlSession) {
        super();
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        if (Object.class.equals(method.getDeclaringClass()) || ReflectUtil.isDefaultMethod(method)) {
            //过滤Object本身的方法 和 静态、抽象的方法
            return method.invoke(this, args);
        }
        String namespace = method.getDeclaringClass().getName();
        String id = namespace + "." + method.getName();

        MappedStatement mappedStatement = sqlSession.getConfiguration().getStatementMap().get(id);
        Enum methodType = mappedStatement.getMethodType();
        //根据返回值类型判断用哪个查询方法
        if (MapperMethodEnum.SELECT.equals(methodType)) {
            if (Collection.class.isAssignableFrom(returnType)) {
                return sqlSession.selectList(id, args == null ? null : args);
            } else {
                return sqlSession.selectOne(id, args == null ? null : args);
            }
        } else if (MapperMethodEnum.INSERT.equals(methodType)) {
            return sqlSession.update(id, args == null ? null : args);
        } else if (MapperMethodEnum.UPDATE.equals(methodType)) {
            return sqlSession.update(id, args == null ? null : args);
        } else if (MapperMethodEnum.DELETE.equals(methodType)) {
            return sqlSession.update(id, args == null ? null : args);
        } else {
            throw new RuntimeException("no mapper method found, please check your mapper.xml");
        }
    }

}
