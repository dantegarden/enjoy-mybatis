package com.enjoy.myorm.orm.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @description:
 * @author: lij
 * @create: 2019-10-06 11:36
 */
public class Resources {

    private static ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();

    public static InputStream getResourceAsStream(String resource) throws IOException {
        return getResourceAsStream(null, resource);
    }

    public static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
        InputStream in = classLoaderWrapper.getResourceAsStream(resource, loader);
        if (in == null) {
            throw new IOException("Could not find resource " + resource);
        }
        return in;
    }




}
