package com.framework;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description
 * @author: liudawei
 * @date: 2020/6/22 14:40
 */
public class LockTest {

    private int a = 0;

    public static void main(String[] args) throws InterruptedException {

        //什么都不加
//        LockModel lockModel = new LockModel();

        //synchronized + volatile;单独 volatile只保证可见性，但不能保证同步问题
//        LockSynchronizedModel lockModel = new LockSynchronizedModel(0);

        //Lock
        LockLockModel lockModel = new LockLockModel(0);

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 20000; i++) {
           /* new Thread(() -> {
                lockModel.addA(1);
            }).start();*/
            executorService.execute(() -> {
                lockModel.addA(1);
            });
        }

        executorService.shutdown();

        while (true){
            if(executorService.isTerminated()){
                System.out.println(lockModel.getA());
                break;
            }
            Thread.sleep(500L);
        }

    }


}
