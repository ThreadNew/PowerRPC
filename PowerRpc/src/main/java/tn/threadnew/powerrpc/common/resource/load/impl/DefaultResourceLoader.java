package tn.threadnew.powerrpc.common.resource.load.impl;


import org.apache.commons.lang.StringUtils;
import tn.threadnew.powerrpc.common.resource.Resource;
import tn.threadnew.powerrpc.common.resource.impl.ProResource;
import tn.threadnew.powerrpc.common.resource.load.Loader;

import java.io.IOException;

/**
 * @Author: hase
 * @Description: TODO
 * @Date: 2020/10/23 20:46
 * @Version: 1.0
 */
public class DefaultResourceLoader implements Loader {

    @Override
    public Resource load(String path) throws IOException {
        Resource rs = null;
        if (StringUtils.isNotBlank(path)) {
            rs = new ProResource(path);
        }
        return rs;
    }
}
