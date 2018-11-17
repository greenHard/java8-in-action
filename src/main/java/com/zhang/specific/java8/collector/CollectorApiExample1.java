package com.zhang.specific.java8.collector;

import com.zhang.specific.java8.stream.Dish;

import java.util.*;

import static java.util.stream.Collectors.*;

/**
 * Collector Api 归约和汇总操作
 */
public class CollectorApiExample1 {
    public static void main(String[] args) {
        // 1. 工厂方法 Collectors 提供的功能
        // 将流元素归约和汇总成一个值
        // 元素分组
        // 元素分区
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

        // 归约
        // 1.1 counting
        Long total = menu.stream().collect(counting());
        //long total = menu.stream().count();
        System.out.println("total numbers: " + total);


        // 1.2 maxBy/ minBy
        Optional<Dish> maxCaloriesDish = menu.stream().collect(maxBy(Comparator.comparingInt(Dish::getCalories)));
        // Optional<Dish> maxCaloriesDish = menu.stream().max(Comparator.comparingInt(Dish::getCalories));
        maxCaloriesDish.ifPresent(System.out::println);

        // 汇总
        // 1.3 summingInt/summingLong/summingDouble
        Integer totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
        // int totalCalories = menu.stream().mapToInt(Dish::getCalories).sum();
        System.out.println("sum calories: " + totalCalories);

        // 1.4 averagingInt/averagingLong/averagingDouble
        Double averageCalories = menu.stream().collect(averagingInt(Dish::getCalories));
        // OptionalDouble averageCalories = menu.stream().mapToInt(Dish::getCalories).average();
        System.out.println("average calories: " + averageCalories);

        // 1.5 summarizingInt/summarizingLong/summarizingDouble
        IntSummaryStatistics summaryStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println("Statistic result: " + summaryStatistics);

        // 连接字符串
        // 1.6 joining
        String shortMenu = menu.stream().map(Dish::getName).collect(joining(", "));
        System.out.println("shortMenu result: " + shortMenu);

        // 通用的归约汇总
        //reducing(T identity, BinaryOperator<T> op)
        //reducing(BinaryOperator<T> op)
        //reducing(U identity, Function<? super T, ? extends U> mapper, BinaryOperator<U> op)
        // identity 初始值  mapper 转化函数  BinaryOperator 累积函数
        totalCalories  = menu.stream().collect(reducing(0, Dish::getCalories, (i, j) -> i + j));
        System.out.println("sum calories: " + totalCalories);
        totalCalories = menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
        System.out.println("sum calories: " + totalCalories);
        /**
         *   public static <T> Collector<T, ?, Long> counting() {
         *       return reducing(0L, e -> 1L, Long::sum);
         *   }
         */

        /**
         * summary
         *
         * 函数式编程通常都会提供多种方式去执行同一个操作, 在不同的方案中, 建议选择最专门化的一个。
         * 无论是从可读性还是性能上看都是最好的决定。
         *
         * 例如: 统计总热量
         * menu.stream().mapToInt(Dish::getCalories).sum();  这是最佳的方案
         * IntStream能让我们避免了拆装箱的操作
         */

    }
}
