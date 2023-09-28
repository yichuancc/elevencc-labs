package com.example.lab01bito.demo;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2023-06-24 15:47
 */
public class BitoTest01 {
    public static void main(String[] args) {

        /**
         * List<String> myList = new ArrayList<>();
         * 实现ArrayList集合多线程安全的方式？
         * 解决方案：
         * 1、List<String> list = new Vector<>();
         * 2、List<String> list = Collections.synchronizedList(new ArrayList<>());
         * 3、List<String> list = new CopyOnWriteArrayList<>();
         */
        List<String> myList = new CopyOnWriteArrayList<>();
         // Create 10 threads
        for (int i = 1; i <= 10; i++) {
            // Each thread adds a random string to the list and prints the updated list
            new Thread(() -> {
                String randomString = UUID.randomUUID().toString().substring(0, 5);
                myList.add(randomString);
                System.out.println(myList);
            }, String.valueOf(i)).start();
        }
    }
}
