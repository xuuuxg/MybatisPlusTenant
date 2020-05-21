package com.tools;

public class ThreadA extends Thread {

    private Boolean isRun = false;
    private Object lock;

    public ThreadA(Object b) {
        this.lock = b;
    }

    public void run() {
        System.out.println("ThreadA Run");
        synchronized (lock) {

        }
    }

}
