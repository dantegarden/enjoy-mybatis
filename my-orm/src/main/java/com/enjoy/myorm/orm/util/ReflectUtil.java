package com.enjoy.myorm.orm.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @description:
 * @author: lij
 * @create: 2019-10-06 19:12
 */
public class ReflectUtil {
    public static boolean isDefaultMethod(Method method) {
        return (method.getModifiers()
                & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC)) == Modifier.PUBLIC
                && method.getDeclaringClass().isInterface();
    }
}
