package com.highqi.demo;

import com.highqi.thread.ThreadMode;

public class ThreadDemo {

    private static final int THREADNUM = 5;//线程数量

    public static void main(String[] args) {

        //线程数量
        int threadmax = THREADNUM;
        for (int i = 0; i < threadmax; i++) {
            ThreadMode thread = new ThreadMode();
            thread.getThread().start();
        }
    }


}


