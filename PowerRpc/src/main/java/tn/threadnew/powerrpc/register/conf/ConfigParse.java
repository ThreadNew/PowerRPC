package tn.threadnew.powerrpc.register.conf;

import org.apache.commons.lang.StringUtils;
import tn.threadnew.powerrpc.common.lang.Constans;
import tn.threadnew.powerrpc.common.resource.Resource;
import tn.threadnew.powerrpc.common.resource.load.Loader;
import tn.threadnew.powerrpc.common.resource.load.impl.DefaultResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: ThreadNew
 * @Description: TODO 解析资源
 * @Date: 2020/10/24 14:44
 * @Version: 1.0
 */
public class ConfigParse {
    //配置类
    private final Config config = Config.newInstance();
    private final Loader loader;

    public ConfigParse(Loader loader) {
        if (loader == null) {
            this.loader = new DefaultResourceLoader();
        } else {
            this.loader = loader;
        }
    }

    public ConfigParse() {
        this(null);
    }

    public Config load(String path) throws IOException {
        if (StringUtils.isNotBlank(path)) {
            Resource load = loader.load(path);
            Properties pro = getPro(load.getInputStream());
            config.put(Constans.POWER_REGISTRY_PRC_SERVER, pro.get(Constans.POWER_REGISTRY_PRC_SERVER));
            config.put(Constans.POWER_PRC_SERVER_CONNECT_TIMEOUT, pro.get(Constans.POWER_PRC_SERVER_CONNECT_TIMEOUT));
            config.put(Constans.POWER_PRC_ROOT, pro.get(Constans.POWER_PRC_ROOT));
            config.put(Constans.POWER_PRC_SERVER_NAME, pro.get(Constans.POWER_PRC_SERVER_NAME));
        }
        return config;
    }

    private Properties getPro(InputStream in) {
        Properties pro = new Properties();
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pro;
    }

    public Config getConfig() {
        return config;
    }

    public Loader getLoader() {
        return loader;
    }
}
