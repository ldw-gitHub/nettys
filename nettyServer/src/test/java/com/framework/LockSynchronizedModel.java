package com.framework;

/**
 * @description
 * @author: liudawei
 * @date: 2020/6/22 14:43
 */
public class LockSynchronizedModel {
    public /*volatile*/ int a;

    public LockSynchronizedModel(int a) {
        this.a = a;
    }

    public synchronized void addA(int i) {
            a += i;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}
