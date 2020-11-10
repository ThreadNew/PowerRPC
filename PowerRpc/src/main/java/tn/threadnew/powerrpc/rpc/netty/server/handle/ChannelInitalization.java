package tn.threadnew.powerrpc.rpc.netty.server.handle;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import tn.threadnew.powerrpc.common.lang.ThreadPool;
import tn.threadnew.powerrpc.rpc.Request;
import tn.threadnew.powerrpc.rpc.Response;
import tn.threadnew.powerrpc.rpc.netty.codec.RpcDecode;
import tn.threadnew.powerrpc.rpc.netty.codec.RpcEncode;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/11/2 20:50
 * @Version: 1.0
 */
public class ChannelInitalization extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(64 * 1024, 0, 4));
        pipeline.addLast(new RpcDecode(Request.class));
        pipeline.addLast(new RpcEncode(Response.class));
        pipeline.addLast(new ChannelHandler(ThreadPool.EXECUTOR_SERVICE));

    }
}
