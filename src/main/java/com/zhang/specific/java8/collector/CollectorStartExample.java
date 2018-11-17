package com.zhang.specific.java8.collector;

import com.zhang.specific.java8.Apple;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * collector操作
 *
 * @author yuyang.zhang
 */
public class CollectorStartExample {
    public static void main(String[] args) {
        List<Apple> list = Arrays.asList(new Apple("green", 150)
                , new Apple("yellow", 120)
                , new Apple("green", 170)
                , new Apple("green", 150)
                , new Apple("yellow", 120)
                , new Apple("green", 170));

        List<Apple> greenList = list.stream().filter(a -> a.getColor().equals("green")).collect(Collectors.toList());
        Optional.ofNullable(greenList).ifPresent(System.out::println);
        Optional.ofNullable(groupByNormal(list)).ifPresent(System.out::println);
        System.out.println("===================================================");
        Optional.ofNullable(groupByCollector(list)).ifPresent(System.out::println);
    }


    /**
     * 通过颜色进行分组
     */
    private static Map<String, List<Apple>> groupByNormal(List<Apple> apples) {
        Map<String, List<Apple>> map = new HashMap<>();
        for (Apple a : apples) {
            List<Apple> list = map.get(a.getColor());
            if (null == list) {
                list = new ArrayList<>();
                map.put(a.getColor(), list);
            }
            list.add(a);
        }
        return map;
    }

    /**
     * 通过collect的方式分组
     */
    private static Map<String, List<Apple>> groupByCollector(List<Apple> apples) {
        return apples.stream().collect(groupingBy(Apple::getColor));
    }
}
