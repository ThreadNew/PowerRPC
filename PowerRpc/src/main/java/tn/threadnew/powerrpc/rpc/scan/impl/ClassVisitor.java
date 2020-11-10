package tn.threadnew.powerrpc.rpc.scan.impl;

import tn.threadnew.powerrpc.rpc.RpcContext;
import tn.threadnew.powerrpc.rpc.netty.annotation.RpcService;
import tn.threadnew.powerrpc.rpc.netty.annotation.impl.RpcInfos;
import tn.threadnew.powerrpc.rpc.scan.Visitor;

import java.lang.annotation.Annotation;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/11/2 19:22
 * @Version: 1.0
 */
public class ClassVisitor implements Visitor {
    @Override
    public void visitor(Object o) {
        if (o instanceof Class) {
            Class clazz = (Class) o;
            if (clazz.isAnnotationPresent(RpcService.class)) {
                Annotation annotation = clazz.getAnnotation(RpcService.class);
                if (annotation instanceof RpcService) {
                    RpcService rpc = (RpcService) annotation;
                    Class[] interfaces = clazz.getInterfaces();
                    for (Class fa : interfaces) {
                        String key = rpc.group() + "||" + rpc.version() + "||" + fa.getSimpleName();
                        RpcInfos info = new RpcInfos(clazz, rpc.group(), rpc.version(), clazz.getSimpleName());
                        RpcContext.put(key, info);
                    }
                }
            }
        }
    }
}
