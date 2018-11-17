package com.zhang.specific.java8.collector;

import com.zhang.specific.java8.stream.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;

/**
 * Collector Api 分区操作
 */
public class CollectorApiExample3 {
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

        // 分区
        // partitioningBy
        // 分区是分组的特殊情况,使用一个谓词作为分类函数，称为分区函数
        Map<Boolean, List<Dish>> partitionMenu = menu.stream().collect(partitioningBy(Dish::isVegetarian));
        System.out.println("partitionMenu: " + partitionMenu);

        // partitioningBy + groupingBy
        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = menu.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
        System.out.println("vegetarianDishesByType: " + vegetarianDishesByType);
    }
}
