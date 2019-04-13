package com.idhclub.concurrency.sington;

import com.idhclub.concurrency.annoations.NotRecommend;
import com.idhclub.concurrency.annoations.ThreadSafe;

/**
 * 饿汉模式是在初始化的时候就将对象创建出来，从而达到线程安全的结果
 */
@ThreadSafe
@NotRecommend
public class HungrySington {
    private static   HungrySington sington = null;
    static {
         sington = new HungrySington();
   }

    private HungrySington(){

    }

    public HungrySington getIntstance(){
        return sington;
    }


}
