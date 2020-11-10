package tn.threadnew.powerrpc.rpc;

import io.netty.channel.Channel;
import tn.threadnew.powerrpc.common.lang.Constans;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: ThreadNew
 * @Description: TODO 协调管理请求和响应
 * @Date: 2020/11/1 16:36
 * @Version: 1.0
 */
public class DefaultFuture implements ResponseFuture {
    private final long id;
    private Request request;
    private static final Map<Long, DefaultFuture> FUTURES = new ConcurrentHashMap<Long, DefaultFuture>();
    private static final Map<Long, Channel> CHANNELS = new ConcurrentHashMap<Long, Channel>();
    private ReentrantLock lock = new ReentrantLock();//锁
    private Condition done = lock.newCondition();//队列
    private Channel channel;
    private volatile Response response;
    private volatile ResponseCallBack responseCallBack;

    public DefaultFuture(Channel channel, Request request) {
        this.channel = channel;
        this.request = request;
        this.id = request.getId();
        // put into waiting map.
        FUTURES.put(id, this);
        CHANNELS.put(id, channel);
    }

    @Override
    public boolean isDone() {
        return response != null;
    }

    @Override
    public Object get() throws Exception {
        return get(0);
    }

    @Override
    public Object get(int timeout) throws Exception {
        if (timeout <= 0) {
            timeout = Constans.DEFAULT_GET_TIMEOUT;
        }
        if (!isDone()) {
            long start = System.currentTimeMillis();
            lock.lock();
            try {
                while (!isDone()) {
                    done.await(timeout, TimeUnit.MILLISECONDS);
                    if (isDone() || System.currentTimeMillis() - start > timeout) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }

        }
        return returnResponse();
    }

    private Object returnResponse() throws Exception {
        Response res = this.response;
        if (res == null) {
            throw new IllegalStateException("response cannot be null");
        }
        if (res.getStatus() == Constans.RESPONSE_OK) {
            return res.getData();
        }
        throw new Exception("remote error");
    }

    public static void received(Channel channel, Response response) {
        try {
            DefaultFuture future = FUTURES.remove(response.getId());
            if (future != null) {
                future.doReceived(response);
            }
        } finally {
            CHANNELS.remove(response.getId());
        }
    }

    private void doReceived(Response res) {
        lock.lock();
        try {
            response = res;
            if (done != null) {
                done.signal();
            }

        } finally {
            lock.unlock();
        }
        //异步
        if (responseCallBack != null) {
            invokeCallBack(this.responseCallBack);
        }
    }

    private void invokeCallBack(ResponseCallBack callBack) {
        callBack.done(this.response);
    }

    @Override
    public void setCallBack(ResponseCallBack callBack) {
        //异步处理
        this.responseCallBack = callBack;
    }

}
