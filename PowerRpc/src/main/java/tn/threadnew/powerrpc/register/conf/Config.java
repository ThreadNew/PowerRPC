package tn.threadnew.powerrpc.register.conf;

import org.apache.commons.lang.StringUtils;
import tn.threadnew.powerrpc.common.lang.ClassUtils;
import tn.threadnew.powerrpc.common.lang.Constans;
import tn.threadnew.powerrpc.register.Configuration;

import java.util.HashMap;

/**
 * @Author: ThreadNew
 * @Description: TODO   配置通用类
 * @Date: 2020/10/24 13:15
 * @Version: 1.0
 */
public class Config implements Configuration {
    private final IMap imp = new IMap();

    private Config() {
        init();
    }

    public static Config newInstance() {
        return new Config();
    }

    //init
    public void init() {
        imp.put(Constans.POWER_REGISTRY_PRC_SERVER, Constans.DEFAULT_REGISTRY_PRC_SERVER_IP);
        imp.put(Constans.POWER_PRC_SERVER_PORT, Constans.DEFAULT_PRC_SERVER_PORT);
        imp.put(Constans.POWER_PRC_ROOT, Constans.DEFAULT_ROOT);
        imp.put(Constans.POWER_PRC_BLANCE, Constans.DEFAULT_BLANCE);
        imp.put(Constans.POWER_PRC_SERVER_SESSION_TIMEOUT, Constans.DEFAULT_SESSION_TIMEOUT);
        imp.put(Constans.POWER_PRC_SERVER_CONNECT_TIMEOUT, Constans.DEFAULT_CONNECT_TIMEOUT);
    }

    public Config put(String key, Object value) {
        imp.put(key, value);
        return this;
    }

    public int size() {
        return imp.size();
    }

    public <T> T get(String key, Class<T> value) {
        return imp.get(key, value);
    }

    private class IMap {
        private final HashMap<String, Object> maps = new HashMap<>();

        //添加
        public void put(String key, Object value) {
            if (StringUtils.isNotBlank(key) && value != null) {
                maps.put(key, value);
            }
        }

        public <T> T get(String key, Class<T> type) {
            return ClassUtils.convert(maps.get(key), type);
        }

        public String getString(String key) {
            return get(key, String.class);
        }

        public int size() {
            return maps.size();
        }
    }
}
