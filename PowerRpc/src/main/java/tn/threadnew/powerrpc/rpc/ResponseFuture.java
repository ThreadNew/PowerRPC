package tn.threadnew.powerrpc.rpc;

/**
 * @Author: ThreadNew
 * @Description: TODO  响应信息回调
 * @Date: 2020/11/1 8:47
 * @Version: 1.0
 */
public interface ResponseFuture {
    public boolean isDone();

    public Object get() throws Exception;

    public Object get(int timeout) throws Exception;

    public void setCallBack(ResponseCallBack callBack);
}