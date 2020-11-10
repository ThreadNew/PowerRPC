package tn.threadnew.powerrpc.rpc.netty.client.proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import tn.threadnew.powerrpc.register.Register;
import tn.threadnew.powerrpc.register.loadblance.NodeValue;
import tn.threadnew.powerrpc.rpc.ResponseCallBack;
import tn.threadnew.powerrpc.rpc.netty.client.handle.ChannelInitalization;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/11/3 22:08
 * @Version: 1.0
 */
public class Proxy {
    private Channel channel; //通道
    //注册中心
    private Register register;
    //
    private final static Bootstrap bootstrap = new Bootstrap();
    private final static EventLoopGroup group = new NioEventLoopGroup();
    private final static ReentrantLock lock = new ReentrantLock();
    private final static Condition condition = lock.newCondition();

    private Proxy() {

    }

    public static Proxy newInstance() {
        return new Proxy();
    }

    public Proxy setRegister(Register register) {
        this.register = register;
        return this;
    }


    //创建代理对象
    public <T> T instance(Class<T> clazz, String version, String group) {
        return (T) java.lang.reflect.Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new ObjectInvocation(channel, version, group));
    }

    //异步接口
    public <T> T call(Class<T> clazz, String version, String group, ResponseCallBack callBack) {
        return (T) java.lang.reflect.Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new AsycObjectInvocation(channel, version, group, callBack));

    }

    //
    public Proxy connect() {
        lock.lock();
        try {
            register.start();
            NodeValue nv = (NodeValue) register.choice();
            if (nv != null) {
                bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.SO_TIMEOUT, 5000).option(ChannelOption.SO_KEEPALIVE, true)
                        .handler(new ChannelInitalization());
                ChannelFuture sync = bootstrap.connect(new InetSocketAddress(nv.getIp(), nv.getPort())).addListeners(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        channel = channelFuture.channel();
                        condition.notify();
                    }
                });
                condition.await(6000, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
        return this;
    }

    public void close() {
        channel.closeFuture();
        group.shutdownGracefully();
        register.stop();
    }
}
