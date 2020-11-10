package tn.threadnew.powerrpc.rpc;

import org.apache.commons.lang.StringUtils;
import tn.threadnew.powerrpc.rpc.netty.annotation.impl.RpcInfos;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/11/2 19:38
 * @Version: 1.0
 */
public class RpcContext {
    private final static Map<String, RpcInfos> DMAPS = new ConcurrentHashMap<>();

    public static void put(String key, RpcInfos info) {
        if (StringUtils.isNotBlank(key))
            DMAPS.put(key, info);
    }

    public static RpcInfos get(String key) {
        return DMAPS.get(key);
    }
}
