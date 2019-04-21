package com.idhclub.concurrency.Lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class LockDemo {
    private final static int clientThrread = 2000;
    private final static int threadCount = 200;
    private final static Lock lock = new ReentrantLock();
    private static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        //表示一次可以允许三个线程同时访问
        final Semaphore semaphore = new Semaphore(threadCount);
        final CountDownLatch countDownLatch = new CountDownLatch(clientThrread);
        for (int i = 0; i < clientThrread; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    test();
                    semaphore.release();
                } catch (InterruptedException e) {
                    log.error("error:", e);
                }finally {
                    countDownLatch.countDown();
                }

            });
        }

        //确保前面的都执行结束后在执行下面的语句
        countDownLatch.await();
        executorService.shutdown();

        log.info("i = {}",i);
    }

    public static void test() throws InterruptedException {
        lock.lock();
        try {
            i++;
        }finally {
            lock.unlock();
        }

    }
}
