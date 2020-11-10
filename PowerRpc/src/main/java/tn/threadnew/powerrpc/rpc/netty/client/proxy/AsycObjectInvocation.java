package tn.threadnew.powerrpc.rpc.netty.client.proxy;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tn.threadnew.powerrpc.rpc.DefaultFuture;
import tn.threadnew.powerrpc.rpc.Request;
import tn.threadnew.powerrpc.rpc.ResponseCallBack;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: ThreadNew
 * @Description: TODO 异步代理接口
 * @Date: 2020/11/5 23:04
 * @Version: 1.0
 */
public class AsycObjectInvocation implements InvocationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectInvocation.class);
    private Channel channel;
    private String version;
    private String group;
    private ResponseCallBack responseCallBack;

    public AsycObjectInvocation(Channel channel, String version, String group, ResponseCallBack responseCallBack) {
        this.channel = channel;
        this.version = version;
        this.group = group;
        this.responseCallBack = responseCallBack;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
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
        if (responseCallBack != null) {
            defaultFuture.setCallBack(responseCallBack);
        } else {
            throw new RuntimeException("callBack is null");
        }
        channel.writeAndFlush(request).sync();
        LOGGER.info("the request is {} and the channel {}", request.toString(), channel.toString());
        if (returnType.isPrimitive()) {//基本数据类型返回默认值其他对象返回null
            if (returnType == Integer.TYPE || returnType == int.class) {
                return 0;
            } else if (returnType == Double.TYPE || returnType == double.class) {
                return 0D;
            } else if (returnType == Short.TYPE || returnType == short.class) {
                return (short) 0;
            } else if (returnType == Byte.TYPE || returnType == byte.class) {
                return (byte) 0;
            } else if (returnType == Long.TYPE || returnType == long.class) {
                return 0L;
            } else if (returnType == Float.TYPE || returnType == float.class) {
                return 0f;
            } else if (returnType == Character.TYPE || returnType == char.class) {
                return '0';
            }
        }
        return null;
    }
}
