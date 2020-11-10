package tn.threadnew.powerrpc.register.loadblance.impl;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.shared.SharedCount;
import tn.threadnew.powerrpc.common.lang.ClassUtils;
import tn.threadnew.powerrpc.register.loadblance.LoadBlance;
import tn.threadnew.powerrpc.register.loadblance.LocalCtl;
import tn.threadnew.powerrpc.register.loadblance.NodeValue;

import java.util.List;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/10/25 9:13
 * @Version: 1.0
 */
public class RoundBlance implements LoadBlance<NodeValue> {
    private CuratorFramework client;
    private SharedCount sharedCount;

    public RoundBlance(CuratorFramework client, SharedCount sharedCount) {
        this.client = client;
        this.sharedCount = sharedCount;
    }

    @Override
    public NodeValue loadBlance(Object obj) throws Exception {
        NodeValue nv = null;
        if (obj != null) {
            String path = (String) obj;
            List<String> childs = client.getChildren().inBackground(new BackgroundCallback() {
                @Override
                public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                    sharedCount.setCount(LocalCtl.getRound() + 1);
                }
            }).forPath(path);
            nv = ClassUtils.byteToObject(client.getData().forPath(path + "/" + childs.get(LocalCtl.getRound() % childs.size())), NodeValue.class);
        }
        return nv;
    }
}
