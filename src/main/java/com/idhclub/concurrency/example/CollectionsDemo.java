package com.idhclub.concurrency.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 在使用的时候，只能使用for循环，而不能使用foreach 和 迭代器
 */
public class CollectionsDemo {
    public static List<Integer> list = new ArrayList<>();

    public static void test1(){
        for (Integer i : list
                ) {
            if (i.equals(5)) {
                list.remove(i);
            }
        }
    }

    public static void test2() {

        Iterator<Integer> iterator = list.iterator();
        int i = 0;
        while (iterator.hasNext()) {

            if (i == 5)
                list.remove(i);
            i++;
            iterator.next();
        }
    }

    public static void test3() {
        for (int i = 0; i < list.size(); i++) {
            if (i == 5){
                list.remove(i);
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        //ConcurrentModificationException
//        test1();
//        ConcurrentModificationException
//        test2();
        test3();
    }
}
