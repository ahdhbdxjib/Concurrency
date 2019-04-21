package com.idhclub.concurrency.Collections_JUC;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;

/**
 * add():
 *  public void add(int index, E element) {
 *         final ReentrantLock lock = this.lock;
 *         lock.lock();
 *         try {
 *             Object[] elements = getArray();
 *             int len = elements.length;
 *             if (index > len || index < 0)
 *                 throw new IndexOutOfBoundsException("Index: "+index+
 *                                                     ", Size: "+len);
 *             Object[] newElements;
 *             int numMoved = len - index;
 *             if (numMoved == 0)
 *                 newElements = Arrays.copyOf(elements, len + 1);
 *             else {
 *                 newElements = new Object[len + 1];
 *                 System.arraycopy(elements, 0, newElements, 0, index);
 *                 System.arraycopy(elements, index, newElements, index + 1,
 *                                  numMoved);
 *             }
 *             newElements[index] = element;
 *             setArray(newElements);
 *         } finally {
 *             lock.unlock();
 *         }
 *     }
 *
 *     get():不加锁
 *     public E get(int index) {
 *         return get(getArray(), index);
 *     }
 */
@Slf4j
public class CopyOnWriteArrayListDemo {
    //请求总数
    public static int clintTotal = 5000;
    //同时并发数
    public static int threadTotal = 200;
    public static CopyOnWriteArrayList<Integer> coalist = new CopyOnWriteArrayList();

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
        log.info("StringBuiter: size:{}",coalist.size());

    }

    private static void add() {
        coalist.add(1);
    }

}
