package com.framework.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class NioTest10Charset {

    /**
     * Nio编解码
     * ASCII  （American standard code for information interchange 美国信息交换标准代码)
     *          7bit来表示一个字符，共计可以表示128种字符
     *
     * ISO-8859-1 8bit表示一个字符，即用一个字节（byte）（8bit）来表示一个字符，共计可以表示256个字符
     *
     * gb2312 两个字节表示一个汉字，国标 ，少数生僻字没有
     *
     * gbk  对gb2312的超集
     *
     * gb18030  汉字最完整编码
     *
     * big5  台湾繁体中文
     *
     * unicode 国际标,统一采用两个字节表示一个字符， 对于英文存储存在浪费
     *
     * UTF - unicode translation format
     *     - unicode 是一种编码方式，utf是一种存储方式，utf-8是unicode的实现方式之一
     *
     * UTF-16
     *     - UTF-16BE big endian
     *     - UTF-16LE little endian
     *     Zero width no-break space ,0xFEFE(BE) 0xFFFE(LE)
     * UTF-32
     *     - UTF-32BE
     *     - UTF-32LE
     * UTF-8
     *     - 变长字节表示形式
     *     英文数字采用ascii,中文通过3个字节表示
     *
     * BOM（byte order mark）,
     *
     */

    public static void main(String[] args) throws Exception{
        String inputFile = "NioTest9.txt";
        String outputFile = "NioTest9_out.txt";

        RandomAccessFile inputRandomAccessFile = new RandomAccessFile(inputFile,"r");

        RandomAccessFile outputRandomAccessFile = new RandomAccessFile(outputFile,"rw");

        long inputLength = new File(inputFile).length();

        FileChannel inputFileChannel = inputRandomAccessFile.getChannel();
        FileChannel outputFileChannel = outputRandomAccessFile.getChannel();

        //内存映射
        MappedByteBuffer inputData = inputFileChannel.map(FileChannel.MapMode.READ_ONLY,0,inputLength);

        System.out.println("=====================================");
        Charset.availableCharsets().forEach((k,v) -> {
            System.out.println(k + " , " + v);
        });
        System.out.println("=====================================");
//        Charset charset = Charset.forName("utf-8");
        Charset charset = Charset.forName("iso-8859-1");

        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();

        CharBuffer charBuffer = decoder.decode(inputData);
        ByteBuffer outputData = encoder.encode(charBuffer);

        outputFileChannel.write(outputData);

        inputRandomAccessFile.close();
        outputRandomAccessFile.close();

    }
}
