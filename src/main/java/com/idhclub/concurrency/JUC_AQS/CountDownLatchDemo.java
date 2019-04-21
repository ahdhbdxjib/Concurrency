package com.idhclub.concurrency.JUC_AQS;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CountDownLatchDemo {
    private final static int threadCount = 200;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int conutNum = i;
            executorService.execute(() -> {
                try {
                    test(conutNum);
                } catch (InterruptedException e) {
                    log.error("error:", e);
                } finally {
                    countDownLatch.countDown();
                }

            });
        }

        countDownLatch.await();
        //确保前面的都执行结束后在执行下面的语句
        log.info("finish");
        executorService.shutdown();
    }

    public static void test(int coutNum) throws InterruptedException {
        Thread.sleep(1000);
        log.info("CountNum : {}", coutNum);
        Thread.sleep(1000);
    }
}
