package tn.threadnew.powerrpc.register;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/11/4 19:48
 * @Version: 1.0
 */
public interface Register extends Choice {
    public void start();
    public void stop();
    public void register(Object obj);
}