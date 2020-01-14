package com.framework.handler;

import com.framework.util.ActionMapUtil;
import com.framework.util.ResponseUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

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
        FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;

        FullHttpResponse response = null;
        Map<String, Object> params = new HashMap<>();
        if (fullHttpRequest.method() == HttpMethod.GET) {
            params = ResponseUtils.getGetParamsFromChannel(fullHttpRequest);
            log.info("params --- " + params.toString());
        } else if (fullHttpRequest.method() == HttpMethod.POST) {
            params = ResponseUtils.getPostParamsFromChannel(fullHttpRequest);
            log.info("params --- " + params.toString());
        } else {
            response = ResponseUtils.responseOK(HttpResponseStatus.INTERNAL_SERVER_ERROR, null);
        }

        ActionMapUtil.invote(fullHttpRequest.uri(), ctx, params);

    }

    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) {
        log.info("=====================================  channelRead0  ==========================================");
    }

}
