package tn.threadnew.powerrpc.common.resource.impl;


import tn.threadnew.powerrpc.common.lang.ClassUtils;
import tn.threadnew.powerrpc.common.resource.Resource;

import java.io.IOException;
import java.io.InputStream;


/**
 * @Author: ThreadNew
 * @Description: TODO  propertis的资源流
 * @Date: 2020/10/23 20:27
 * @Version: 1.0
 */
public class ProResource implements Resource {
    // the path of file
    private final String path;
    //get the classLoader
    private final ClassLoader classLoader;

    public ProResource(String location) {
        this(location, null);
    }

    public ProResource(String location, ClassLoader classLoader) {
        this.path = location;
        this.classLoader = classLoader == null ? ClassUtils.getClassLoader() : classLoader;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return classLoader.getResourceAsStream(path);
    }
}
