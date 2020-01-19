package com.framework.handler;

import com.framework.model.BusinessException;
import com.framework.model.ResultInfo;
import com.framework.util.ActionMapUtil;
import com.framework.util.ResponseUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.netty.buffer.Unpooled.copiedBuffer;

/**
 * @description 解析参数，请求分发
 * @author: liudawei
 * @date: 2020/1/13 16:34
 */
@Slf4j
public class NettyInboundHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

//    private static int THREAD_NUMBER = 300;
//    private static ExecutorService FIXED_THREAD_POOL = Executors.newFixedThreadPool(THREAD_NUMBER);

    /**
     * @description: 参数校验，请求分发
     * @author: liudawei
     * @date: 2020/1/14 10:24
     * @param: ctx
     * @param: msg
     * @return: void
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        //采用业务线程池来处理业务逻辑，否则netty workerGroup会阻塞在此，影响服务器效率
        log.info("request url --- " + fullHttpRequest.uri());
        log.info("request method --- " + fullHttpRequest.method());

        FullHttpResponse response = null;
        String content = "";
        Map<String, Object> params = new HashMap<>();
        boolean isRightRequest = false; //参数是否获取

        if (fullHttpRequest.method() == HttpMethod.GET) {
            params = ResponseUtils.getGetParamsFromChannel(fullHttpRequest);
            isRightRequest = true;
        }

        if (fullHttpRequest.method() == HttpMethod.POST) {
            params = ResponseUtils.getPostParamsFromChannel(fullHttpRequest);
            isRightRequest = true;
        }

        if(params == null){
            throw new BusinessException(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }

        log.info("params --- " + params.toString());

        if (!isRightRequest) {
            throw new BusinessException(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }

        //io.netty.util.IllegalReferenceCountException: refCnt: 0, decrement: 1
        fullHttpRequest.retain();

        //路由
        boolean invote = ActionMapUtil.invote(fullHttpRequest.uri(), fullHttpRequest.method() + "", ctx, params);
        if (invote) {
            throw new BusinessException(ResultInfo.FAILURE, "请求路径不存在！");
        }

    }


    /**
     * @description:处理抛出的异常
     * @author: liudawei
     * @date: 2020/1/17 17:36
     * @param: ctx
     * @param: ex
     * @return: void
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable ex) throws Exception {
        if (ex != null) {
            ex.printStackTrace();
            ResponseUtils.responseError(ctx, ex);
        }

    }


}

