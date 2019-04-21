package com.idhclub.concurrency.JUC_AQS;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class SemaphoreDemo3 {
    private final static int threadCount = 20;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        //表示一次可以允许三个线程同时访问
        final Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < threadCount; i++) {
            final int conutNum = i;
            executorService.execute(() -> {
                try {
                    if(semaphore.tryAcquire()){//尝试获取标志，否则直接丢弃
                        test(conutNum);
                        semaphore.release();
                    }
                } catch (InterruptedException e) {
                    log.error("error:", e);
                } finally {
                    countDownLatch.countDown();
                }

            });
        }

        countDownLatch.await();
        //确保前面的都执行结束后在执行下面的语句
        executorService.shutdown();
    }

    public static void test(int coutNum) throws InterruptedException {
        Thread.sleep(100);
        log.info("CountNum : {}", coutNum);
    }
}
