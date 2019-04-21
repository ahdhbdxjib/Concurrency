package com.idhclub.concurrency.JUC_AQS;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CountDownLatchDemo2 {
    private final static int threadCount = 200;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
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

//        设置等待时间，如果超出时间就直接执行后面的的方法
        //当时执行的方法的计时是从执行方法中计时的
        countDownLatch.await(20,TimeUnit.MILLISECONDS);
        //确保前面的都执行结束后在执行下面的语句
        log.info("finish");
        executorService.shutdown();
    }

    public static void test(int coutNum) throws InterruptedException {
        Thread.sleep(10);
        log.info("CountNum : {}", coutNum);
    }
}
