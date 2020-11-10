package tn.threadnew;

import org.junit.Assert;
import org.junit.Test;
import tn.threadnew.powerrpc.common.lang.Constans;

/**
 * @Author: hase
 * @Description: TODO
 * @Date: 2020/10/24 11:45
 * @Version: 1.0
 */
public class PowerRpcTest {
    @Test
    public void version() {

        System.out.println(System.getProperty(Constans.DEFAULT_BLANCE));
        Assert.assertEquals(PowerRpc.getName(),"PowerRpc");
        Assert.assertEquals(PowerRpc.getVersion(),"1.0");
    }
}
