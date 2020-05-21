package com.tools;


import org.springframework.context.ApplicationEvent;

public class Test extends Thread{

    private int sum = 10000;

    public synchronized void run() {
        try{
            int count = 3;
            while (sum >= 0) {
                sum --;
                System.out.println(Thread.currentThread().getName() + "-----剩余" + sum);
//                System.out.println("唤醒开始");
//                this.notify();
//                System.out.println("唤醒结束");
                System.out.println("休眠开始");
                this.wait(200);
                System.out.println("休眠开始");

            }

        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}

