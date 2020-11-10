package tn.threadnew.powerrpc.register.manage;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tn.threadnew.powerrpc.common.lang.ClassUtils;
import tn.threadnew.powerrpc.common.lang.Constans;
import tn.threadnew.powerrpc.register.conf.Config;
import tn.threadnew.powerrpc.register.Register;
import tn.threadnew.powerrpc.register.loadblance.LoadBlance;
import tn.threadnew.powerrpc.register.loadblance.LocalCtl;
import tn.threadnew.powerrpc.register.loadblance.NodeValue;
import tn.threadnew.powerrpc.register.loadblance.impl.RandomBlance;
import tn.threadnew.powerrpc.register.loadblance.impl.RoundBlance;
import tn.threadnew.powerrpc.register.manage.Listener.RoundListenner;
import tn.threadnew.powerrpc.register.conf.ConfigParse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


/**
 * @Author: ThreadNew
 * @Description: TODO zookeeper注册中心连接
 * @Date: 2020/10/24 15:41
 * @Version: 1.0
 */
public class ZkConnectManager implements Register {
    private final static Logger LOGGER = LoggerFactory.getLogger(ZkConnectManager.class);
    private ConfigParse configParse;
    private CuratorFramework client;
    private RetryPolicy retryPolicy;
    private CuratorCache cache;
    private SharedCount sharedCount;
    private Config config;
    private HashMap<String, LoadBlance> blanceMap = new HashMap<>();
    private String root = "";


    public ZkConnectManager() {
        this.retryPolicy = new ExponentialBackoffRetry(1000, 3);
        this.configParse = new ConfigParse();
        this.config = configParse.getConfig();
        this.root = config.get(Constans.POWER_PRC_ROOT, String.class);
    }

    public ZkConnectManager load(String path) throws IOException {
        this.config = configParse.load(path);
        return this;
    }

    //负载均衡
    public void initBlance() {
        blanceMap.put("round", new RoundBlance(this.client, this.sharedCount));
        blanceMap.put("random", new RandomBlance(this.client));
    }

    public ZkConnectManager setConfigParse(ConfigParse configParse) {
        this.configParse = configParse;
        return this;
    }

    public ZkConnectManager addRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
        return this;
    }

    @Override
    public synchronized Object choice() {
        Object target = null;
        LoadBlance loadBlance = this.blanceMap.get(this.config.get(Constans.POWER_PRC_BLANCE, String.class));
        LOGGER.info("get the loadblance from zookeeper is {}",loadBlance.toString());
        try {
            target = loadBlance.loadBlance(root);
            if (target == null) {
                target = LocalCtl.getRandom();
                LOGGER.info("get the loadblance from local is {}",target.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }


    @Override
    public void start() {
        try {
            client = CuratorFrameworkFactory.builder().connectString(config.get(Constans.POWER_REGISTRY_PRC_SERVER, String.class))
                    .retryPolicy(this.retryPolicy)
                    .sessionTimeoutMs(config.get(Constans.POWER_PRC_SERVER_SESSION_TIMEOUT, Integer.class))
                    .connectionTimeoutMs(config.get(Constans.POWER_PRC_SERVER_CONNECT_TIMEOUT, Integer.class))
                    .build();
            client.start();

            CuratorCacheListener listener = CuratorCacheListener.builder()
                    .forCreates(node -> {
                        if (node.getPath().trim().startsWith(root + "/")) {
                            LOGGER.info("the event of create ");
                            LocalCtl.add(node.getPath(), ClassUtils.byteToObject(node.getData(), NodeValue.class));
                        }
                    })
                    .forChanges((oldNode, node) -> {
                        LOGGER.info("the event of changes ");
                        if (oldNode.getPath().trim().startsWith(root + "/") && LocalCtl.containsKey(oldNode.getPath())) {
                            LocalCtl.add(node.getPath(), ClassUtils.byteToObject(node.getData(), NodeValue.class));
                        }
                    }).forDeletes(oldNode -> {
                        LOGGER.info("the event of delete ");
                        if (oldNode.getPath().trim().startsWith(root + "/") && LocalCtl.containsKey(oldNode.getPath())) {
                            LocalCtl.remove(oldNode.getPath());
                        }
                    })
                    .build();
            cache = CuratorCache.build(this.client, root);
            cache.listenable().addListener(listener);
            cache.start();
            sharedCount = new SharedCount(this.client, root, 15);
            sharedCount.addListener(new RoundListenner());
            sharedCount.start();
            //初始化负载均衡的算法
            initBlance();
        } catch (Exception e) {
            e.printStackTrace();
            stop();

        }
    }

    @Override
    public void stop() {
        if (client != null) this.client.close();
        if (cache != null) cache.close();
        if (sharedCount != null) {
            try {
                sharedCount.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void register(Object obj) {
        try {
            String path = root + "/" + config.get(Constans.POWER_PRC_SERVER_NAME, String.class);
            LOGGER.info(" the registered path is {}",path);
            byte[] bytes = ClassUtils.convertToByte(obj);
            client.create().orSetData().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Config getConfig() {
        return config;
    }
}
