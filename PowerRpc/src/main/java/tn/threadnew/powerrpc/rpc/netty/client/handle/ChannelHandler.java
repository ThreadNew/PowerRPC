package tn.threadnew.powerrpc.rpc.netty.client.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import tn.threadnew.powerrpc.rpc.DefaultFuture;
import tn.threadnew.powerrpc.rpc.Response;

/**
 * @Author: ThreadNew
 * @Description: TODO  消费端处理
 * @Date: 2020/11/3 21:02
 * @Version: 1.0
 */
public class ChannelHandler extends SimpleChannelInboundHandler<Response> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        DefaultFuture.received(channelHandlerContext.channel(), response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
