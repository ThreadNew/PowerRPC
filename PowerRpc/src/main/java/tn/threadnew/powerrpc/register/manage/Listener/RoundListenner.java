package tn.threadnew.powerrpc.register.manage.Listener;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.shared.SharedCountListener;
import org.apache.curator.framework.recipes.shared.SharedCountReader;
import org.apache.curator.framework.state.ConnectionState;
import tn.threadnew.powerrpc.register.loadblance.LocalCtl;

/**
 * @Author: ThreadNew
 * @Description: TODO  负载均衡轮续要用的算法
 * @Date: 2020/10/25 8:26
 * @Version: 1.0
 */
public class RoundListenner implements SharedCountListener {
    @Override
    public void countHasChanged(SharedCountReader sharedCount, int newCount) throws Exception {
        //更改本地缓存的值
        LocalCtl.setRoud(newCount);
    }


    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
    }
}
