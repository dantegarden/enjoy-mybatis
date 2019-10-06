package com.enjoy.myorm.orm.executor;

import com.enjoy.myorm.orm.config.Configuration;
import com.enjoy.myorm.orm.config.MappedStatement;
import com.enjoy.myorm.orm.executor.resultset.ResultSetContext;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: lij
 * @create: 2019-10-05 23:07
 */
public class DefaultExecutor implements Executor {

    private Configuration configuration;

    private ObjectFactory objectFactory;
    private ObjectWrapperFactory objectWrapperFactory;
    private ReflectorFactory reflectorFactory;

    public DefaultExecutor(Configuration configuration) {
        super();
        this.configuration = configuration;
        this.objectFactory= new DefaultObjectFactory();
        this.objectWrapperFactory = new DefaultObjectWrapperFactory();
        this.reflectorFactory = new DefaultReflectorFactory();
    }
    /**核心查询方法**/
    @Override
    public <E> List<E> query(MappedStatement statement, Object parameter) {
        List<E> resultList = new ArrayList<E>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = configuration.getConnection();
            //创建预编译PreparedStatement对象，从MappedStatement获取sql语句
            preparedStatement = connection.prepareStatement(statement.getSql());
            //处理sql中的占位符
            parameterSize(preparedStatement, parameter);
            //执行查询操作获取resultSet
            resultSet = preparedStatement.executeQuery();
            //将结果集通过反射，填充到list中
            handleResult(resultSet, resultList, statement.getResultType());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    @Override
    public int update(MappedStatement statement, Object parameter) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int ret = 0;
        try {
            connection = configuration.getConnection();
            connection.setAutoCommit(false);
            //创建预编译PreparedStatement对象，从MappedStatement获取sql语句
            preparedStatement = connection.prepareStatement(statement.getSql());
            //处理sql中的占位符
            parameterSize(preparedStatement, parameter);
            //执行查询操作获取resultSet
            ret = preparedStatement.executeUpdate();
            return ret;
        } catch (Exception e) {
            if(connection!=null){
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if(connection!=null){
                    connection.commit();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 对PreparedStatement中的占位符进行处理
     * //TODO 现在只能按传入参数顺序绑定
     * @param statement
     * @param parameter
     * @return void
     */
    private void parameterSize(PreparedStatement statement, Object parameter) throws SQLException {
        if(parameter instanceof Object[]){
            Object[] parameters = (Object[]) parameter;
            for (int i = 0; i < parameters.length; i++) {
                singleParameterSize(statement,i+1,  parameters[i]);
            }
        }else{
            singleParameterSize(statement,1,  parameter);
        }
    }

    private void singleParameterSize(PreparedStatement statement, int index, Object parameter) throws SQLException {
        if (parameter instanceof Integer) {
            statement.setInt(index, (Integer) parameter);
        } else if (parameter instanceof Long) {
            statement.setLong(index, (Long) parameter);
        } else if (parameter instanceof String) {
            statement.setString(index, (String) parameter);
        }
    }

    /**
     * 读取ResultSet中的数据,并转换成目标对象
     *
     * @param resultSet
     * @param ret
     * @param className
     * @return void
     */
    private <E> void handleResult(ResultSet resultSet, List<E> ret, String className) {
        Class<E> clazz = null;
        //通过反射获取类的对象
        try {
            clazz = (Class<E>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                //通过反射实例化对象
                E e = objectFactory.create(clazz);
                MetaObject metaObject = MetaObject.forObject(e, objectFactory, objectWrapperFactory, reflectorFactory);
                ObjectWrapper objectWrapper = metaObject.getObjectWrapper();

                ResultSetContext rsc = new ResultSetContext();
                rsc.handleResultColumn(resultSet);

                //使用反射工具将ResultSet中的数据填充到entity中
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    String propertyName = field.getName();
                    if(rsc.hasColumn(propertyName)){
                        Class<?> propertyType = field.getType();
                        Object value = rsc.getColumn(propertyName);
                        value = propertyType.cast(value); //类型转换
                        PropertyTokenizer propertyTokenizer = new PropertyTokenizer(propertyName);
                        objectWrapper.set(propertyTokenizer, value);
                    }else{
                        continue;
                    }
                }
                ret.add((E) e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
