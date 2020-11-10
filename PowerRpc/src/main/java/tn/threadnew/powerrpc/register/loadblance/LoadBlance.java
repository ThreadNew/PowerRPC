package tn.threadnew.powerrpc.register.loadblance;

/**
 * @Author: ThreadNew
 * @Description: TODO 通用的负载均衡的算法
 * @Date: 2020/10/25 8:46
 * @Version: 1.0
 */
public interface LoadBlance<T> {
    public T loadBlance(Object obj) throws Exception;
}