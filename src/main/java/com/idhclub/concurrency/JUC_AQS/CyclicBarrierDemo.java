package com.idhclub.concurrency.JUC_AQS;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CyclicBarrierDemo {
    private static CyclicBarrier cyclicBarrier= new CyclicBarrier(5);
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            executor.execute(()->{
                try {
                    race(threadNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    executor.shutdown();
                }
            });

        }
    }

    private static void race(int threadNum) throws InterruptedException, BrokenBarrierException {
        Thread.sleep(1000);
        log.info("{}already",threadNum);
        //表示当前的线程已经准备好了
        cyclicBarrier.await();
        log.info("{}continue",threadNum);
    }
}
