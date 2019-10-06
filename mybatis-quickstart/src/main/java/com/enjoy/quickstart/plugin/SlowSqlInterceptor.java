package com.enjoy.quickstart.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.jdbc.PreparedStatementLogger;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;
import java.util.Properties;

/**
 * @description: 实例mybatis插件开发，打印sql执行时间，判断是否是慢查询语句
 * @author: lij
 * @create: 2019-10-05 17:27
 */
@Intercepts({
        @Signature(type= Executor.class, method="query", args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type= Executor.class, method="update", args={MappedStatement.class, Object.class})
})
public class SlowSqlInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(SlowSqlInterceptor.class);

    private Long threshold = 3000L;

    /**拦截处理**/
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTimeMillis = System.currentTimeMillis();
        Object ret = invocation.proceed(); //执行原方法
        long endTimeMillis = System.currentTimeMillis();
        long runTimeMillis = endTimeMillis - startTimeMillis;

        Object[] args = invocation.getArgs(); //拿到query方法的入参
        MappedStatement ms = (MappedStatement) args[0];
        log.debug("{}: spend {}ms {}", ms.getId(), runTimeMillis, runTimeMillis >= threshold?", is slow sql":"");
//        第一个Interceptos签名，可以这样获取sql信息
//        Statement stmt = (Statement) args[0]; //这里得到的是Statement的动态代理对象
//        MetaObject metaObject = SystemMetaObject.forObject(stmt);
//        PreparedStatementLogger psmtLogger = (PreparedStatementLogger) metaObject.getValue("h"); //拿到被代理对象的包装类
//        Statement statement = psmtLogger.getPreparedStatement(); //返回真实的statement
//        System.out.println("慢查询SQL ：" + statement.toString() + ", 执行时间：" + runTimeMillis +"ms");
        return ret; //把原结果返回
    }
    /**生成动态代理对象**/
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
    /**获得plugin配置上的参数**/
    @Override
    public void setProperties(Properties properties) {
        this.threshold = Long.valueOf(properties.getProperty("threshold"));
    }
}
