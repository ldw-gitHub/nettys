package com.framework.nio;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
/**
 * Scattering 与 Gathering
 *
 *
 */
public class NioTest8 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);

        serverSocketChannel.socket().bind(address);

        int messageLength = 2 + 3 + 4;

        ByteBuffer[] buffer = new ByteBuffer[3];
        buffer[0] = ByteBuffer.allocate(2);
        buffer[1] = ByteBuffer.allocate(3);
        buffer[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true){
            int byteRead= 0;

            while(byteRead< messageLength){
                long r = socketChannel.read(buffer);
                byteRead += r;

                System.out.println("byteRead: " + byteRead);
                Arrays.asList(buffer).stream().map(b -> "positions:" + b.position()).forEach(System.out::println);
            }

            Arrays.asList(buffer).forEach(b -> { b.flip();});

            long byteWritten = 0;
            while(byteWritten < messageLength){
                long write = socketChannel.write(buffer);
                byteWritten += write;
            }

            Arrays.asList(buffer).forEach(b -> { b.clear() ;});

            System.out.println("byteRead: " + byteRead + " byteWritten : " + byteWritten);
        }


    }
}
