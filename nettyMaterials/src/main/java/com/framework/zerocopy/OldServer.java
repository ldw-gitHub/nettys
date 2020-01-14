package com.framework.zerocopy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description
 * @author: liudawei
 * @date: 2020/1/9 16:45
 */
public class OldServer {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8899);
        while(true){
            Socket accept = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(accept.getInputStream());

            byte[] byteArray = new byte[4096];

            while(true){
                int read = dataInputStream.read(byteArray, 0, byteArray.length);


            }


        }
    }
}
