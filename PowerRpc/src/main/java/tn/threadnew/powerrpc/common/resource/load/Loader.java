package tn.threadnew.powerrpc.common.resource.load;


import tn.threadnew.powerrpc.common.resource.Resource;

import java.io.IOException;

/**
 * @Author:  ThreadNew
 * @Description: TODO  资源加载器屏蔽掉细节
 * @Date: 2020/10/23 20:45
 * @Version: 1.0
 */
public interface Loader {
    public Resource load(String path) throws IOException;
}
