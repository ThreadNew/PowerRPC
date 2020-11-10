package tn.threadnew.powerrpc.common.lang;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ThreadNew
 * @Description: TODO  工具类常量
 * @Date: 2020/10/23 21:36
 * @Version: 1.0
 */
public class Constans {
    //
    public static final String POWER_REGISTRY_PRC_SERVER = "power.rpc.registry.server.ip";
    public static final String POWER_PRC_SERVER_NAME = "power.rpc.server.name";
    public static final String POWER_PRC_SERVER_PORT = "power.rpc.server.port";
    public static final String POWER_PRC_ROOT = "power.rpc.root";
    public static final String POWER_PRC_BLANCE = "power.rpc.blance";
    public static final String POWER_PRC_SERVER_CONNECT_TIMEOUT = "power.rpc.server.connect.timeout";
    public static final String POWER_PRC_SERVER_SESSION_TIMEOUT = "power.rpc.server.session.timeout";
    public static final String DEFAULT_REGISTRY_PRC_SERVER_IP = "127.0.0.1:2181";
    public static final int DEFAULT_CONNECT_TIMEOUT = 5000;
    public static final int DEFAULT_SESSION_TIMEOUT = 60000;
    public static final String DEFAULT_ROOT = "/zk";
    public static final String DEFAULT_BLANCE = "random";
    public static final int DEFAULT_GET_TIMEOUT = 5000;
    public static final int DEFAULT_PRC_SERVER_PORT = 9000;
    public static final int RESPONSE_OK = 0;
    public static final int RESPONSE_ERROR = 1;
    public static final int DEFAULT_HANDLE_POOLSIZE = 5;


}
