package tn.threadnew.powerrpc.common.lang;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/11/2 22:06
 * @Version: 1.0
 */
public class ThreadPool {
    public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(Constans.DEFAULT_HANDLE_POOLSIZE);

}
