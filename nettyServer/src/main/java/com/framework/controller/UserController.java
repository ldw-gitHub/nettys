package com.framework.controller;

import com.framework.auth.ActionMap;
import com.framework.auth.NettyController;
import com.framework.model.BusinessException;
import com.framework.model.ResultInfo;
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

    @ActionMap(key = "/login", requestMethod = "Post")
    public void login(ChannelHandlerContext ctx, Map<String, Object> params) {

        throw new BusinessException(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
//        ResultInfo resultInfo = new ResultInfo(ResultInfo.SUCCESS,ResultInfo.MSG_SUCCESS,params);
//        ResponseUtils.responseOK(ctx,resultInfo);
    }

    @ActionMap(key = "/loginout", requestMethod = "get")
    public void loginout(ChannelHandlerContext ctx, Map<String, Object> params) {
        ResponseUtils.responseOK(ctx, new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, params));
    }
}
