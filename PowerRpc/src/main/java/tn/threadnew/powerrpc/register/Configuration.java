package tn.threadnew.powerrpc.register;

/**
 * @Author: ThreadNew
 * @Description: TODO 配置中心
 * @Date: 2020/11/4 21:43
 * @Version: 1.0
 */

public interface Configuration {
    public <T> T get(String key, Class<T> type);
}