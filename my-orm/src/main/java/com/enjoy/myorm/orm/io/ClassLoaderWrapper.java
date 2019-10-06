package com.enjoy.myorm.orm.io;

import java.io.InputStream;

/**
 * @description: 类加载器的封装
 * @author: lij
 * @create: 2019-10-06 11:43
 */
public class ClassLoaderWrapper {

    private ClassLoader systemClassLoader;

    public ClassLoaderWrapper() {
        this.systemClassLoader = ClassLoader.getSystemClassLoader();
    }

    ClassLoader[] getClassLoaders(ClassLoader classLoader) {
        return new ClassLoader[]{
                classLoader,
                Thread.currentThread().getContextClassLoader(),
                getClass().getClassLoader(),
                systemClassLoader};
    }

    public InputStream getResourceAsStream(String resource, ClassLoader classLoader) {
        return getResourceAsStream(resource, getClassLoaders(classLoader));
    }

    public InputStream getResourceAsStream(String resource, ClassLoader[] classLoader) {
        for (ClassLoader cl : classLoader) {
            if (null != cl) {
                InputStream returnValue = cl.getResourceAsStream(resource);
                if (null == returnValue) {
                    returnValue = cl.getResourceAsStream("/" + resource);
                }
                if (null != returnValue) {
                    return returnValue;
                }
            }
        }
        return null;
    }
}
