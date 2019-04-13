package com.idhclub.concurrency.sington;

import com.idhclub.concurrency.annoations.ThreadSafe;

@ThreadSafe
public class LazySington {
    //在这里如果没有volatite修饰的话，会造成创建对象的时候，可能指令重排，
    /**
     * 创建对象的过程：
     * 1.分配内存
     * 2.初始化对象
     * 3.将初始化的对象和内存映射
     *
     * 若指令重排可能会132
     * 在3时被其他线层访问，而直接返回实例，则会线程不安全
     */
    static volatile LazySington sington;
    private LazySington(){

    }

    /**
     * 双重判断的原因：
     * 1.外面的判断是防止已经创建好的对象在多个线程访问的时候，线程不必要的进入锁，造成的资源浪费
     * 2.里面的判断则是真正是否要创建
     * @return
     */
    public static LazySington getIinstance(){
        if(sington == null){
            synchronized (sington){
                if(sington == null){
                    sington = new LazySington();
                }
            }
        }
        return sington;
    }

}
