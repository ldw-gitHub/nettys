package com.framework.util;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description
 * @author: liudawei
 * @date: 2021/1/12 17:19
 */
public class MyRejectPolicy implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        //自定义策略 ；当线程池队列满了后新增队列处理
    }

}
