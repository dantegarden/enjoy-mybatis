package com.enjoy.myorm.orm.binding;

import lombok.Data;

import java.lang.reflect.Method;

/** 对应一个方法
 * @description:
 * @author: lij
 * @create: 2019-10-06 19:16
 */
@Data
public class MappedMethod {
    private String methodName;
    private String sourceId;
    private Class<?> returnType;
    private Class<?>[] parameterTypes;
    private int parameterCount;
    private Method method;

    public MappedMethod(Method method, String namespace){
        this.methodName = method.getName();
        this.sourceId = method.getName() + "." + namespace;
        this.returnType = method.getReturnType();
        this.parameterCount = method.getParameterCount();
        this.parameterTypes = method.getParameterTypes();
        this.method = method;
    }
}
