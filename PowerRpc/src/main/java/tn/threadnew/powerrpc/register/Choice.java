package tn.threadnew.powerrpc.register;

/**
 * @Author: ThreadNew
 * @Description: TODO  服务选择的通用接口
 * @Date: 2020/10/24 15:32
 * @Version: 1.0
 */
public interface Choice {
    // 返回Object 方便做类型转换，转换成我们定义的类型
    public Object choice();

}