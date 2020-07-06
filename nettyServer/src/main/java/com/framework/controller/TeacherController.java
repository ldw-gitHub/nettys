package com.framework.controller;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Enumeration;

/**
 * @description
 * @author: liudawei
 * @date: 2020/6/2 10:45
 */
@RestController
public class TeacherController {

    @PostMapping("/getName")
    public String getName(HttpServletRequest request, HttpServletResponse response
            /*@RequestBody JSONObject text*/
            /*    String name,Integer age*/
            /*@RequestParam("test") MultipartFile file*/) throws IOException {
        Enumeration<String> attributeNames = request.getHeaderNames();
        while (attributeNames.hasMoreElements()) {
            System.out.println(request.getHeader(attributeNames.nextElement()));
        }

        ServletInputStream inputStream = request.getInputStream();

        FileOutputStream file = new FileOutputStream(new File("C:\\Users\\20190322\\Desktop\\test\\1.docx"));
        byte[] arr = new byte[1024];

        int n = -1;
        while ((n = inputStream.read(arr, 0, arr.length)) != -1) {
            file.write(arr, 0, n);
        }

        file.flush();
        file.close();
        inputStream.close();

//        System.out.println("param ====== " + file);
        return "ldw";
    }
}
