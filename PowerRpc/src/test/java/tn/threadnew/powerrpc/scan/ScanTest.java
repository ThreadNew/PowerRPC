package tn.threadnew.powerrpc.scan;

import org.junit.Test;
import tn.threadnew.powerrpc.rpc.scan.Scan;
import tn.threadnew.powerrpc.rpc.scan.impl.ClassVisitor;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/11/2 19:57
 * @Version: 1.0
 */
public class ScanTest {
    @Test
    public void test(){
        ClassVisitor cv=new ClassVisitor();
        Scan.me.accept(cv).scanPacket(ScanTest.class);
    }
}
