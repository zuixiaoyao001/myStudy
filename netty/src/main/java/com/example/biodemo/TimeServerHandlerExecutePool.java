package com.example.biodemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//自定义的线程池
public class TimeServerHandlerExecutePool {
    private ExecutorService executor;
//  线程池构造函数，创建线程池
    public TimeServerHandlerExecutePool(int maxPoolSize,int queueSize){
//      new ThreadPoolExecutor(线程池基本大小，线程池最大数量，存活时间，时间单位，消息队列）
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),maxPoolSize,120L,
                TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(queueSize));

    }
//  该方法会不断从消息队列中获取任务执行
    public void execute(java.lang.Runnable task){
        executor.execute(task);
    }

}
