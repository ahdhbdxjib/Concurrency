package com.idhclub.concurrency.sington;

import com.idhclub.concurrency.annoations.Recommend;
import com.idhclub.concurrency.annoations.ThreadSafe;

/**
 * 使用枚举类的时候，JVM会在底层创建实例不会造成线程不安全
 *
 * 使用这方法兼顾了前面两种的有点，延迟加载，而且不会造成线程安全的问题
 */
@ThreadSafe
@Recommend
public class EnumSington {
    private EnumSington(){}
    private enum Sington{
        INSTANCE;
        private  EnumSington sington;
        Sington(){
            sington = new EnumSington();
        }
        public EnumSington getInstance(){
            return sington;
        }
    }

    public EnumSington getInstance(){
        return Sington.INSTANCE.getInstance();
    }
}
