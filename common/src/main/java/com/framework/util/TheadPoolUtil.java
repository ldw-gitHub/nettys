package com.framework.util;


import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description
 * @author: liudawei
 * @date: 2021/1/12 15:19
 */
public class TheadPoolUtil {
    /**
     * 线程池空闲要保留的线程数
     */
    static int corePoolSize = 5;

    /**
     * 线程池最大线程数
     */
    static int maximumPoolSize = 10;

    /**
     * 当线程数大于核心使用数，多余线程最长保留等待时间
     */
    static Long keepAliveTime = 0L;

    /**
     * 用于在任务完成之前，保存任务的队列
     */
    static LinkedBlockingDeque linkedBlockingDeque = new LinkedBlockingDeque<Runnable>(10);


    /**
     * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
     * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
     * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
     * ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
     */

    public static ThreadPoolExecutor getThreadPool() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
                linkedBlockingDeque, new ThreadFactoryBuilder().setNameFormat("XX-task-%d").build(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        return pool;
    }

    /**
     * pool.shutdown();  线程池拒接收新提交的任务，同时等待线程池里的任务执行完毕后关闭线程池
     * pool.shutdownNow();  线程池拒接收新提交的任务，同时立马关闭线程池，线程池里的任务不再执行
     * 使用shuwdown方法关闭线程池时，一定要确保任务里不会有永久阻塞等待的逻辑，否则线程池就关闭不了
     * 使用shutdownNow方法关闭线程池时，一定要对任务里进行异常捕获
     */
    public static boolean closeThreadPool(ThreadPoolExecutor pool) {
        boolean flag = false;
        long st = System.currentTimeMillis();
        if (!pool.isTerminated()) {
            pool.shutdown();
//            pool.shutdownNow();
            try {
                //pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                pool.awaitTermination(2L, TimeUnit.SECONDS);
                if (pool.isTerminated()) {
                    long et = System.currentTimeMillis();
                    System.out.println("耗时：" + (et - st) + "秒");
                }
                flag = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            flag = true;
        }
        return flag;
    }

    static volatile int number = 0;

    /**
     * 有没有一种一定能保证线程安全的代码写法？（偷偷告诉你，真的有！）
     * 自定义线程池有7个参数，他们分别是什么意思？
     * 为什么阿里规范中不允许使用JDK自带线程池？
     * 自旋锁、偏向锁、轻量级锁、重量级锁、读写锁、分段锁都是什么？
     * 如何正确的启动和停止一个线程？
     * 线程和纤程的区别的是什么？为什么纤程比较轻量级？
     * ThreadLocal有没有内存泄漏的问题？为什么？
     * 下列三种业务，应该如何使用线程池：
     * 高并发、任务执行时间短
     * 并发不高、任务执行时间长
     * 并发高、业务执行时间长
     *
     * @param args
     */
    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = getThreadPool();
        for (int i = 0; i < 100; i++) {
            if (!threadPool.isTerminated()) {
                System.out.println("i ===== " + i);
                threadPool.execute(() -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println("error : " + e.getMessage());
                    }
                    number++;
                    System.out.println(number);
                    if (number >= 10) {
                        boolean b = closeThreadPool(threadPool);
                        System.out.println("线程池关闭 --- " + b);
                    }
                });
            }
        }
    }

}
