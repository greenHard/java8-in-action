package com.zhang.specific.java8.collector;

import com.zhang.specific.java8.stream.Dish;

import java.util.*;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

/**
 * Collector Api 分组操作
 */
public class CollectorApiExample2 {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));

        // 1. 分组
        // 1.1 单级分组 groupingBy
        Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
        System.out.println(dishesByType);

        // 1.2 多级分组 groupingBy
        // 按类型和热量分组
        Map<Dish.Type, Map<String, List<Dish>>> result = menu.stream().collect(groupingBy(Dish::getType, groupingBy(
                dish -> {
                    if (dish.getCalories() <= 400)
                        return "diet";
                    else if (dish.getCalories() <= 700)
                        return "normal";
                    else
                        return "fat";
                })
        ));
        System.out.println(result);

        // 1.3 与其他收集器一起使用
        Map<Dish.Type, Optional<Dish>> mostCaloricByType = menu.stream().collect(groupingBy(Dish::getType, maxBy(comparingInt(Dish::getCalories))));
        System.out.println("mostCaloricByType:" + mostCaloricByType);

        // collectingAndThen(收集器,转换函数)
        Map<Dish.Type, Dish> typeDishMap = menu.stream().collect(groupingBy(Dish::getType, collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println("typeDishMap:" + typeDishMap);

    }
}
