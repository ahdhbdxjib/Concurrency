package com.idhclub.concurrency.example;

import com.idhclub.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
@NotThreadSafe
@Slf4j
public class StringBuilderDemo {
    //请求总数
    public static int clintTotal = 5000;
    //同时并发数
    public static int threadTotal = 200;
    public static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clintTotal);
        for (int i = 0; i < clintTotal; i++) {
            executorService.execute(
                    () -> {
                        try {
                            semaphore.acquire();
                            add();
                            semaphore.release();
                        } catch (InterruptedException e) {
                            log.error("error:{}",e);
                            ;
                        }
                        countDownLatch.countDown();
                    }
            );

        }
        countDownLatch.await();;
        executorService.shutdown();;
        log.info("StringBuiter: size:{}",sb.length());

    }

    private static void add() {
        sb.append("1");
    }

}
