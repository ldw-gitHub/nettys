package com.framework.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description
 * @author: liudawei
 * @date: 2020/6/2 10:45
 */
@RestController
public class TeacherController {

    @PostMapping("/getName")
    public String getName(HttpServletRequest request, HttpServletResponse response){
        return "ldw";
    }
}
