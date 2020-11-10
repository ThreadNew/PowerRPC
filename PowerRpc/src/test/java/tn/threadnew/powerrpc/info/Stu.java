package tn.threadnew.powerrpc.info;

import tn.threadnew.powerrpc.rpc.netty.annotation.RpcService;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/11/4 22:04
 * @Version: 1.0
 */
@RpcService(version = "1.0", timeout = 5000, group = "info")
public class Stu implements UserInfo {

    @Override
    public String getName(String name) {
        return name;
    }

    @Override
    public int add(int a, int b) {
        return a + b;
    }

    public String acc(String a, int b) {
        return a + "   cen " + b;
    }


}
