package com.framework.handler;

import com.framework.model.FileUploadEntity;
import com.framework.model.ResultInfo;
import com.framework.util.ResponseUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

/**
 * @description
 * @author: liudawei
 * @date: 2020/3/27 10:22
 */
@Slf4j
public class NettyBreakpointUploadHandler extends ChannelInboundHandlerAdapter {
    private int byteRead;
    private volatile int start = 0;
    private String file_dir = "E:\\cesi";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("服务端：channelActive()");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        log.info("服务端：channelInactive()");
        ctx.flush();
        ctx.close();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FileUploadEntity) {
            FileUploadEntity ef = (FileUploadEntity) msg;
            byte[] bytes = ef.getBytes();
            byteRead = ef.getEndPos();
            String md5 = ef.getFile_md5();//文件名
            String path = file_dir + File.separator + md5;
            File file = new File(path);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(start);
            randomAccessFile.write(bytes);
            start = start + byteRead;
            System.out.println("path:" + path + "," + byteRead);
            if (byteRead > 0) {
                ctx.writeAndFlush(start);
                randomAccessFile.close();
                if (byteRead != 1024 * 10) {
                    Thread.sleep(1000);
                    channelInactive(ctx);
                }
            } else {
                ctx.close();
            }

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
        log.info("FileUploadServerHandler--exceptionCaught()");
    }
}
