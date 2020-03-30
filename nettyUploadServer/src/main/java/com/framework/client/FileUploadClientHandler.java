package com.framework.client;

import com.framework.model.FileUploadEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @description
 * @author: liudawei
 * @date: 2020/3/30 9:49
 */
@Slf4j
public class FileUploadClientHandler extends ChannelInboundHandlerAdapter {
    private int byteRead;
    private volatile int start = 0;
    private volatile int lastLength = 0;
    public RandomAccessFile randomAccessFile;
    private FileUploadEntity fileUploadFile;

    public FileUploadClientHandler(FileUploadEntity ef) {
        if (ef.getFile().exists()) {
            if (!ef.getFile().isFile()) {
                System.out.println("Not a file :" + ef.getFile());
                return;
            }
        }
        this.fileUploadFile = ef;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        super.channelInactive(ctx);
        log.info("客户端结束传递文件channelInactive()");
    }

    public void channelActive(ChannelHandlerContext ctx) {
        //System.out.println("客户端：channelActive()");
        try {
            randomAccessFile = new RandomAccessFile(fileUploadFile.getFile(),
                    "r");
            randomAccessFile.seek(fileUploadFile.getStarPos());
            // lastLength = (int) randomAccessFile.length() / 10;
            lastLength = 1024 * 10;
            byte[] bytes = new byte[lastLength];
            if ((byteRead = randomAccessFile.read(bytes)) != -1) {
                fileUploadFile.setEndPos(byteRead);
                fileUploadFile.setBytes(bytes);
                ctx.writeAndFlush(fileUploadFile);
            } else {
            }
            log.info("channelActive()文件已经读完 "+byteRead);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if (msg instanceof Integer) {
            start = (Integer) msg;
            if (start != -1) {
                randomAccessFile = new RandomAccessFile(
                        fileUploadFile.getFile(), "r");
                randomAccessFile.seek(start);
                log.info("长度：" + (randomAccessFile.length() - start));
                int a = (int) (randomAccessFile.length() - start);
                int b = (int) (randomAccessFile.length() / 1024*2);
                if (a < lastLength) {
                    lastLength = a;
                }
                log.info("文件长度：" + (randomAccessFile.length())+",start:"+start+",a:"+a+",b:"+b+",lastLength:"+lastLength);
                byte[] bytes = new byte[lastLength];
                // log.info("-----------------------------" + bytes.length);
                if ((byteRead = randomAccessFile.read(bytes)) != -1
                        && (randomAccessFile.length() - start) > 0) {
                    // log.info("byte 长度：" + bytes.length);
                    fileUploadFile.setEndPos(byteRead);
                    fileUploadFile.setBytes(bytes);
                    try {
                        ctx.writeAndFlush(fileUploadFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    randomAccessFile.close();
                    ctx.close();
                    log.info("文件已经读完channelRead()--------" + byteRead);
                }
            }
        }
    }


    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
