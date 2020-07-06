package com.framework;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description
 * @author: liudawei
 * @date: 2020/6/22 14:43
 */
public class LockLockModel {
    public int a;

    public LockLockModel(int a) {
        this.a = a;
    }

    private Lock lock = new ReentrantLock();

    public void addA(int i) {
//        lock.lock();
        try {
            a += i;
        }finally {
            lock.unlock();
        }
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}
