package com.framework.handler;

import com.framework.model.BusinessException;
import com.framework.model.ResultInfo;
import com.framework.util.ResponseUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @description 做身份验证
 * @author: liudawei
 * @date: 2020/1/17 10:53
 */
@Slf4j
public class NettyVerificationHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest){
        log.info("==================================== NettyVerificationHandler.channelRead0 ==================================");
        verificationUser(ctx,fullHttpRequest);
    }

    protected  void verificationUser(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest){
        log.info("=====================================进入身份认证===================================");
        String authorization = fullHttpRequest.headers().get("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            log.error("缺失Authorization！！！！！");
            throw new BusinessException(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_TOKEN);
        }
        log.info("authorization --- " + authorization);

        // TODO 完善身份认证
        log.info("=====================================身份认证完成===================================");
        ctx.fireChannelRead(fullHttpRequest);
    }

    /**
     * @description:异常处理
     * @author: liudawei
     * @date: 2020/1/17 17:42
     * @param: ctx
     * @param: ex
     * @return: void
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable ex) {
        ex.printStackTrace();
        if (ex != null) {
            ResponseUtils.responseError(ctx, ex);
        }

    }
}
