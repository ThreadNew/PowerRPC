package tn.threadnew.powerrpc.register.loadblance.impl;

import org.apache.curator.framework.CuratorFramework;
import tn.threadnew.powerrpc.common.lang.ClassUtils;
import tn.threadnew.powerrpc.register.loadblance.LoadBlance;
import tn.threadnew.powerrpc.register.loadblance.NodeValue;
import java.util.List;
import java.util.Random;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/10/25 9:28
 * @Version: 1.0
 */
public class RandomBlance implements LoadBlance<NodeValue> {
    private CuratorFramework client;


    public RandomBlance(CuratorFramework client) {
        this.client = client;

    }

    @Override
    public NodeValue loadBlance(Object obj) throws Exception {
        NodeValue nv = null;
        if (obj != null) {
            String path = (String) obj;
            List<String> childs = client.getChildren().forPath(path);
            Random random = new Random();
            nv = ClassUtils.byteToObject(client.getData().forPath(path + "/" + childs.get(random.nextInt(childs.size()))), NodeValue.class);
        }
        return nv;
    }
}
