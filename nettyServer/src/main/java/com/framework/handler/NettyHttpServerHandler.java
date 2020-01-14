package com.framework.handler;

import com.framework.util.ActionMapUtil;
import com.framework.util.ResponseUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static io.netty.buffer.Unpooled.copiedBuffer;

/**
 * @description
 * @author: liudawei
 * @date: 2020/1/13 16:34
 */
@Slf4j
public class NettyHttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    /**
     * @description: 拦截器处理请求
     * @author: liudawei
     * @date: 2020/1/14 10:24
     * @param: ctx
     * @param: msg
     * @return: void
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("=====================================  channelRead  ==============================================");
        log.info("采用业务线程池来处理业务逻辑，否则netty workerGroup会阻塞在此，影响服务器效率");
        FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
        log.info("request url --- " + fullHttpRequest.uri());
        log.info("request method --- " + fullHttpRequest.method());

        FullHttpResponse response = null;
        String content = "";
        Map<String, Object> params = new HashMap<>();
        if (fullHttpRequest.method() == HttpMethod.GET) {
            params = ResponseUtils.getGetParamsFromChannel(fullHttpRequest);
            log.info("params --- " + params.toString());
        } else if (fullHttpRequest.method() == HttpMethod.POST) {
            params = ResponseUtils.getPostParamsFromChannel(fullHttpRequest);
            log.info("params --- " + params.toString());
        } else {
            content = "请求类型不支持！";
            response = ResponseUtils.responseOK(HttpResponseStatus.INTERNAL_SERVER_ERROR, copiedBuffer(content, CharsetUtil.UTF_8));
        }

        if (ActionMapUtil.invote(fullHttpRequest.uri(), fullHttpRequest.method() + "", ctx, params) == null){
            content = "请求路径不存在！";
            response = ResponseUtils.responseOK(HttpResponseStatus.BAD_GATEWAY, copiedBuffer(content, CharsetUtil.UTF_8));
        }

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

    }

    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) {
        log.info("=====================================  channelRead0  ==========================================");
    }

}
