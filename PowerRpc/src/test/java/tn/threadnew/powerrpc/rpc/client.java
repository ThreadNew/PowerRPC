package tn.threadnew.powerrpc.rpc;

import org.junit.Test;
import tn.threadnew.powerrpc.info.UserInfo;
import tn.threadnew.powerrpc.register.manage.ZkConnectManager;
import tn.threadnew.powerrpc.rpc.netty.client.proxy.Proxy;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/11/4 22:29
 * @Version: 1.0
 */
public class client {
    private Proxy proxy = Proxy.newInstance();

    @Test
    public void Test() throws Exception {
        ZkConnectManager zk = new ZkConnectManager();
        zk.load("zk.properties");
        proxy.setRegister(zk).connect();
        UserInfo info = proxy.instance(UserInfo.class, "1.0", "info");
        System.out.println(info.add(2, 3));
        UserInfo info1 = proxy.call(UserInfo.class, "1.0", "info", new ResponseCallBack() {
            @Override
            public void done(Object o) {
                Response re = (Response) o;
                System.out.println("data  " + re.getData());
            }
        });
        info1.acc("che c", 7);
        Thread.sleep(50000);

        proxy.close();
    }

}
