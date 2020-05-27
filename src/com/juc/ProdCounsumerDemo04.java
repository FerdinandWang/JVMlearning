package com.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目:现在两个线程,可以操作初始值为0的一个变量,
 * 实现一个线程对该变量加1,一个县城对该变量减1.
 * 实现交替,10轮,变量初始值为0
 *
 * 1.高内聚低耦合前提下,线程操作资源类
 * 2.判断+干活+通知
 * 3.防止线程的虚拟唤醒,只要有wait需要用while判断
 *
 * wait()判断的时候用while 不要用 if(官方文档)
 *
 *
 *
 */
public class ProdCounsumerDemo04 {

    public static void main(String[] args) {

        Aircondition aircondition = new Aircondition();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    aircondition.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

/*        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    aircondition.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();*/

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    aircondition.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

/*        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    aircondition.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();*/
    }
}

class Aircondition {

    private int number = 0;

/*    public synchronized void increment() throws Exception {
        //1.判断
        while (number != 0) {
            this.wait();
        }

        //2.干活
        number++;
        System.out.println(Thread.currentThread().getName() + "\t" +  number);

        //3.通知
        this.notifyAll();
    }

    public synchronized void decrement() throws Exception {
        //1.判断
        while (number == 0) {
            this.wait();
        }

        //2.干活
        number--;
        System.out.println(Thread.currentThread().getName() + "\t" +  number);

        this.notifyAll();
    }*/

    //可重入非公平的递归锁
    private final Lock lock = new ReentrantLock();

    public void increment() throws Exception {
        lock.lock();
        try{
            //1.判断
            while (number != 0) {
                this.wait();
            }

            //2.干活
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" +  number);
            //3.通知

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }




    }



}