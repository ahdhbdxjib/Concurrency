package com.idhclub.concurrency.example;

import com.idhclub.concurrency.annoations.NotThreadSafe;
import com.idhclub.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@ThreadSafe
@Slf4j
public class DateTimeFormatDemo2 {
    //请求总数
    public static int clintTotal = 5000;
    //同时并发数
    public static int threadTotal = 200;
   public static DateTimeFormatter simpleDateFormat = DateTimeFormat.forPattern("YYYYMMDD");

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
                            log.error("error:{}", e);
                            ;
                        }
                        countDownLatch.countDown();
                    }
            );

        }
        countDownLatch.await();
        ;
        executorService.shutdown();
        ;

    }

    private static void add() {
            simpleDateFormat.parseDateTime("20101205");
    }

}
