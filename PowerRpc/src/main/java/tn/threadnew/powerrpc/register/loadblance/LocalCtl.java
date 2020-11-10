package tn.threadnew.powerrpc.register.loadblance;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * @Author: ThreadNew
 * @Description: TODO  服务注册缓存表
 * @Date: 2020/10/25 8:15
 * @Version: 1.0
 */
public final class LocalCtl {
    private static int round;//轮续
    private static HashMap<String, NodeValue> maps = new HashMap<>();

    //设置值
    public static void setRoud(int count) {
        round = count;
    }

    //添加节点内容
    public static void add(String key, NodeValue nv) {
        if (StringUtils.isNotBlank(key) && nv != null) {
            maps.put(key, nv);
        }
    }

    public static int getRound() {
        return round;
    }

    //根据key移除
    public static void remove(String key) {
        maps.remove(key);
    }

    //是否存在key
    public static boolean containsKey(String key) {
        return maps.containsKey(key);
    }

    //得到其大小
    public static int size() {
        return maps.keySet().size();
    }

    //随机得到
    public static NodeValue getRandom() {
        if (size() != 0) {
            Set<String> keys = maps.keySet();
            Object[] okeys = keys.toArray();
            Random rd = new Random();
            NodeValue nodeValue = maps.get(okeys[rd.nextInt(size())]);
            return nodeValue;
        }
        return null;
    }
}
