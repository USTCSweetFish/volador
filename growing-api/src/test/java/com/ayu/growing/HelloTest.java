package com.ayu.growing;

import com.ayu.growing.entity.JdEntity;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class HelloTest {


    private static List<String> simpleList = new ArrayList<>();
    private static List<JdEntity> normalList = new ArrayList<>();

    static {
        simpleList.add("apple");
        simpleList.add("apple");
        simpleList.add("banana");
        simpleList.add("orange");

        normalList.add(new JdEntity("1", "Apple", "23"));
        normalList.add(new JdEntity("2", "Banana", "34"));
        normalList.add(new JdEntity("3", "Orange", "34"));
        normalList.add(new JdEntity("4", "Pig", "43"));
    }

    //list转set
    @Test
    public void test() {
        System.out.println("----------------简单List---------------");
        simpleList.forEach(e -> System.out.println(e));
        System.out.println("----------------简单List转Set---------------");
        Set<String> simpleSet = new HashSet<>(simpleList);
        simpleSet.forEach(e -> System.out.println(e));

        System.out.println("----------------普通List---------------");
        normalList.forEach(e -> System.out.println(e));
        System.out.println("----------------普通List转Set---------------");
        Set<String> normalSet = normalList.stream().map(JdEntity::getBookName).collect(Collectors.toSet());
        normalSet.forEach(e -> System.out.println(e));
        System.out.println("----------------普通List转List---------------");
        List<String> list = normalList.stream().map(JdEntity::getBookID).collect(Collectors.toList());
        list.forEach(e -> System.out.println(e));
        System.out.println("----------------普通List转Map---------------");
        Map<String, JdEntity> normalMap = normalList.stream().collect(Collectors.toMap(JdEntity::getBookID, (b) -> b));
        normalMap.forEach((id, book) -> {
            System.out.println(id + "::" + book);
        });
    }

    //set集合并集，交集，差集
    @Test
    public void test1() {
        String[] str1 = {"a", "b", "c", "d", "e"};
        String[] str2 = {"d", "e", "f", "g", "h"};

        Set<String> set1 = new HashSet<String>(Arrays.asList(str1));
        Set<String> set2 = new HashSet<String>(Arrays.asList(str2));
        Set<String> set = new HashSet<String>();

        // 并集
        set.addAll(set1);
        set.addAll(set2);
        System.out.println("并集" + set);

        // 交集
        set.clear();
        set.addAll(set1);
        set.retainAll(set2);
        System.out.println("交集" + set);
        // 差集
        set.clear();
        set.addAll(set1);
        set.removeAll(set2);
        System.out.println("差集" + set);
    }

    //固定数量线程池创建
    @Test
    public void test2() {
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(9);
        for (int i = 0; i <= 100; i++) {
            newFixedThreadPool.execute(() -> {
                        try {
                            Thread.sleep(1000);
                            System.out.println("---------输出" + Thread.currentThread().getName());
                        } catch (Exception e) {

                        }
                    }
            );
        }
    }
}
