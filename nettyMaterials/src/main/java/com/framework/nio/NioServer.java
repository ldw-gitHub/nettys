package com.framework.nio;

import io.netty.buffer.ByteBuf;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class NioServer {

    private static Map<String,SocketChannel> clientMap = new HashMap<String,SocketChannel>();

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);//非阻塞
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//注册serverSocketChannel 关注客户端连接

        while (true) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                selectionKeys.forEach(selectionKey -> {
                    final SocketChannel client;

                    try {

                        if(selectionKey.isAcceptable()){//是否有新的连接
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector,SelectionKey.OP_READ);//将连接建立新的SocketChannel

                            String key = "【" + UUID.randomUUID().toString() + "】";
                            clientMap.put(key,client);
                        }else if(selectionKey.isReadable()){//是否有可读的数据
                            client = (SocketChannel) selectionKey.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);

                            int count = client.read(readBuffer);

                            if(count > 0){
                                readBuffer.flip();
                                Charset charset = Charset.forName("utf-8");
                                String receiveMessage = String.valueOf(charset.decode(readBuffer).array());
                                System.out.println("client : " + receiveMessage);

                                String sendKey = null;

                                for(Map.Entry<String,SocketChannel> entry:clientMap.entrySet()){
                                    if(client == entry.getValue()){
                                        sendKey = entry.getKey();
                                        break;
                                    }
                                }

                                for(Map.Entry<String,SocketChannel> entry:clientMap.entrySet()){
                                    SocketChannel value = entry.getValue();
                                    ByteBuffer writerBuffer = ByteBuffer.allocate(1024);

                                    writerBuffer.put((sendKey + ":" + receiveMessage).getBytes());
                                    writerBuffer.flip();

                                    value.write(writerBuffer);
                                }
                            }

                        }

                        selectionKeys.clear();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
