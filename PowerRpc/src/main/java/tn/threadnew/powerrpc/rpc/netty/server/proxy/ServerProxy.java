package tn.threadnew.powerrpc.rpc.netty.server.proxy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import tn.threadnew.powerrpc.common.lang.Constans;
import tn.threadnew.powerrpc.register.Configuration;
import tn.threadnew.powerrpc.register.Register;
import tn.threadnew.powerrpc.register.loadblance.NodeValue;
import tn.threadnew.powerrpc.rpc.netty.server.handle.ChannelInitalization;
import tn.threadnew.powerrpc.rpc.scan.Scan;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/11/4 21:12
 * @Version: 1.0
 */
public class ServerProxy {
    private final static NioEventLoopGroup group = new NioEventLoopGroup();
    private final static NioEventLoopGroup work = new NioEventLoopGroup(10);
    private final static ServerBootstrap bootstrap = new ServerBootstrap();
    private final static Scan scan = Scan.me;
    private Register register;
    private Configuration configuration;
    private final static ReentrantLock lock = new ReentrantLock();
    private final static Condition condition = lock.newCondition();

    private ServerProxy() {

    }

    public static ServerProxy newInstance() {
        return new ServerProxy();
    }

    //scan the package
    public ServerProxy scan(String path) {
        this.scan.scanPacket(path);
        return this;
    }

    //set the register
    public ServerProxy setRegister(Register register) {
        this.register = register;
        return this;
    }

    //set th config
    public ServerProxy setConfig(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    // connect
    public ServerProxy connect() {
        lock.lock();
        try {
            register.start();
            String ip = InetAddress.getLocalHost().getHostAddress();
            Integer port = configuration.get(Constans.POWER_PRC_SERVER_PORT, Integer.class);
            NodeValue nv = new NodeValue(ip , port, 40);
            bootstrap.group(group, work).channel(NioServerSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
                    .localAddress(ip, port).childHandler(new ChannelInitalization());
            bootstrap.bind().addListeners(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    register.register(nv);
                    condition.notify();
                }
            }).sync();
            condition.await(50000, TimeUnit.MILLISECONDS);

        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
        return this;
    }

    public ServerProxy close() {
        try {
            work.shutdownGracefully().sync();
            group.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        register.stop();
        return this;
    }
}
