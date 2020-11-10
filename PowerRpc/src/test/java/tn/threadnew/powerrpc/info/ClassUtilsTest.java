package tn.threadnew.powerrpc.info;

import org.junit.Test;
import tn.threadnew.powerrpc.common.lang.ClassUtils;
import tn.threadnew.powerrpc.rpc.Request;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/11/5 0:20
 * @Version: 1.0
 */
public class ClassUtilsTest {
    @Test
    public void test()throws  Exception{
        Request request=new Request();
        request.setClassName("mehth");
        request.setVersion("1.2");
        request.setParameterTypes(new Class[]{UserInfo.class});
        byte[] bytes = ClassUtils.convertToByte(request);
        System.out.println(bytes.length);
    }
}
