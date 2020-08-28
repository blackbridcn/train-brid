package com.train.jdk.server.io;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeServiceHandlerExecutePools {

    ThreadPoolExecutor poolExecutor;

    public TimeServiceHandlerExecutePools(int maxPoolsSize, int queueSize) {
        poolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                maxPoolsSize, 120l, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
    }


    public void executor(Runnable task) {
        System.out.println("--------------> "+task.toString());
        poolExecutor.execute(task);
    }

}
