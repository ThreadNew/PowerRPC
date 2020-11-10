package tn.threadnew.powerrpc.common.resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: ThreadNew
 * @Description: TODO 资源接口
 * @Date: 2020/10/24 11:52
 * @Version: 1.0
 */
public interface Resource {
    public InputStream getInputStream() throws IOException;
}
