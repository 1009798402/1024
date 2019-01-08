package com.highqi.thread;

public class ThreadMode {
    public Thread getThread()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i < 10000 ; i++)
                {
                    System.out.print("\n"+Thread.currentThread().getName()+"-----"+i);
                }
            }
        });

        return thread;
    }
}