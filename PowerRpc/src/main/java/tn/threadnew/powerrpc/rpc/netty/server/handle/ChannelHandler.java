package tn.threadnew.powerrpc.rpc.netty.server.handle;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang.StringUtils;
import tn.threadnew.powerrpc.common.lang.ClassUtils;
import tn.threadnew.powerrpc.common.lang.Constans;
import tn.threadnew.powerrpc.rpc.Request;
import tn.threadnew.powerrpc.rpc.Response;
import tn.threadnew.powerrpc.rpc.RpcContext;
import tn.threadnew.powerrpc.rpc.netty.annotation.impl.RpcInfos;

import java.util.concurrent.ExecutorService;

/**
 * @Author: ThreadNew
 * @Description: TODO  消息执行
 * @Date: 2020/11/2 21:58
 * @Version: 1.0
 */
public class ChannelHandler extends SimpleChannelInboundHandler<Request> {
    private ExecutorService executor;

    public ChannelHandler(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    handle(channelHandlerContext.channel(), request);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    //处理
    private void handle(Channel channel, Request request) throws Exception {
        long id = request.getId();
        String key = request.getGroup() + "||" + request.getVersion() + "||" + request.getClassName();
        Response response = new Response();
        response.setId(id);
        RpcInfos rpcInfos = RpcContext.get(key);
        if (rpcInfos != null) {
            String methodName = request.getMethodName();
            if (StringUtils.isNotBlank(methodName)) {
                Object invoke = ClassUtils.invoke(rpcInfos.getClazz(), methodName, request.getParameterTypes(), request.getParameters());
                response.setData(invoke);
                response.setStatus(Constans.RESPONSE_OK);
            } else {
                response.setStatus(Constans.RESPONSE_ERROR);
            }
        }
        channel.writeAndFlush(response).sync();
    }
}
