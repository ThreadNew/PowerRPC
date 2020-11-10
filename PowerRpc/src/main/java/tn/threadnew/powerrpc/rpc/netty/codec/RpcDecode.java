package tn.threadnew.powerrpc.rpc.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import tn.threadnew.powerrpc.common.lang.ClassUtils;
import tn.threadnew.powerrpc.rpc.Request;

import java.util.List;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/11/1 19:16
 * @Version: 1.0
 */
public class RpcDecode extends ByteToMessageDecoder {
    private Class clazz;

    public RpcDecode(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        byteBuf.markReaderIndex();
        int len = byteBuf.readInt();
        if (byteBuf.readableBytes() < len) {
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] data = new byte[len];
        byteBuf.readBytes(data);
        Object msg = ClassUtils.byteToObject(data, clazz);
        if (msg != null) {
            list.add(msg);
        }

    }
}
