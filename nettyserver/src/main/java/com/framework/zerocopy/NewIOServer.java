package com.framework.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @description
 * @author: liudawei
 * @date: 2020/1/9 17:41
 */
public class NewIOServer {
    public static void main(String[] args) throws Exception{
        InetSocketAddress address = new InetSocketAddress(8899);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(address);
        serverSocket.setReuseAddress(true);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while(true){
            SocketChannel accept = serverSocketChannel.accept();
            accept.configureBlocking(true);

            int readCount = 0;

            while(-1 != readCount){
                readCount = accept.read(byteBuffer);
                byteBuffer.rewind();
            }

        }

    }
}
