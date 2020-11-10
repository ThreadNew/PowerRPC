package tn.threadnew.powerrpc.rpc.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import tn.threadnew.powerrpc.common.lang.ClassUtils;
import tn.threadnew.powerrpc.rpc.Request;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/11/1 19:15
 * @Version: 1.0
 */
public class RpcEncode extends MessageToByteEncoder {
    private Class clazz;

    public RpcEncode(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (clazz.isInstance(o)) {//是request或者response实例就进行编码
            byte[] bytes = ClassUtils.convertToByte(o);
            int len = bytes.length;
            byteBuf.writeInt(len);
            byteBuf.writeBytes(bytes);

        }
    }
}
