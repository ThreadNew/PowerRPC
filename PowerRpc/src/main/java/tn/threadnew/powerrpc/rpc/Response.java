package tn.threadnew.powerrpc.rpc;

/**
 * @Author: ThreadNew
 * @Description: TODO  响应
 * @Date: 2020/11/1 16:21
 * @Version: 1.0
 */
public class Response {
    //与请求的ID相同
    private long id;
    //数据
    private Object data;
    //状态标志：0是成功 1 失败
    private int status;

    public Response() {
    }

    public Response(long id, Object data, int status) {
        this.id = id;
        this.data = data;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
