package com.framework.controller;

import com.framework.auth.ActionMap;
import com.framework.auth.NettyController;
import com.framework.util.ResponseUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

import java.util.Map;

import static io.netty.buffer.Unpooled.copiedBuffer;

/**
 * @description
 * @author: liudawei
 * @date: 2020/1/13 15:47
 */
@NettyController
public class UserController {

    @ActionMap(key = "/login")
    public void login(ChannelHandlerContext ctx, Map<String, Object> params){
        ByteBuf content = copiedBuffer(params.toString(), CharsetUtil.UTF_8);
        FullHttpResponse response = ResponseUtils.responseOK(HttpResponseStatus.OK, content);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
