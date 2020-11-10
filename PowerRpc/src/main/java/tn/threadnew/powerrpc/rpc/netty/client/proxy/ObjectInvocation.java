package tn.threadnew.powerrpc.rpc.netty.client.proxy;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tn.threadnew.powerrpc.rpc.DefaultFuture;
import tn.threadnew.powerrpc.rpc.Request;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


/**
 * @Author: ThreadNew
 * @Description: TODO 同步代理
 * @Date: 2020/11/3 21:09
 * @Version: 1.0
 */
public class ObjectInvocation implements InvocationHandler {
    private static final Logger LOGGER= LoggerFactory.getLogger(ObjectInvocation.class);
    private Channel channel;
    private String version;
    private String group;

    public ObjectInvocation(Channel channel, String version, String group) {
        this.channel = channel;
        this.version = version;
        this.group = group;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //
        Class<?> clazz = method.getDeclaringClass();
        String name = method.getName();
        //toString
        if ("equals".equals(name)) {
            return proxy == args[0];
        } else if ("hashCode".equals(name)) {
            return System.identityHashCode(proxy);
        } else if ("toString".equals(name)) {
            return proxy.getClass().getName() + "@" +
                    Integer.toHexString(System.identityHashCode(proxy)) +
                    ", with InvocationHandler " + this;
        }
        Request request = new Request();
        request.setClassName(clazz.getSimpleName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        //分组和版本
        request.setVersion(version);
        request.setGroup(group);
        DefaultFuture defaultFuture = new DefaultFuture(channel, request);
        channel.writeAndFlush(request).sync();
        LOGGER.info("the request is {} and the channel {}",request.toString(),channel.toString());
        return defaultFuture.get();
    }
}
