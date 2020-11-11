package tn.threadnew.powerrpc.rpc;

import org.junit.Test;
import tn.threadnew.powerrpc.register.manage.ZkConnectManager;
import tn.threadnew.powerrpc.rpc.netty.server.proxy.ServerProxy;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/11/4 22:05
 * @Version: 1.0
 */
public class Server {
    private ServerProxy sp = ServerProxy.newInstance();

    @Test
    public void start() throws Exception {
        ZkConnectManager zk = new ZkConnectManager();
        zk.load("zk.properties");
        sp.scan("tn.threadnew.powerrpc.info").setRegister(zk).setConfig(zk.getConfig()).connect();
       // Thread.sleep(5000000);
    }
}
