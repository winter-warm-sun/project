package com.example.demo.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    public static ExecutorService executorService() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                8, 20, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5000),
                (Runnable task) -> {
                    Thread thread = new Thread(task);
                    thread.setName("批量插入线程");
                    return thread;
                },
                new ThreadPoolExecutor.AbortPolicy()
        );
        return executor;
    }
}
