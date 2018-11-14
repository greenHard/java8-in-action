package com.zhang.specific.java8.stream;


import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * 流操作
 *
 * @author yuyang.zhang
 */
public class StreamStartExample {
    public static void main(String[] args) {
        /**
         * 一. Why Stream?
         *    1. 很多业务逻辑都涉及类似于数据库的操作,比如对几道菜按类别进行分组，找出最贵的菜。使用集合操作起来就显的会没那么容易。
         *    如果使用数据库的类似的操作方式就很完美了。
         *    2. 处理大量的数据,需并行处理,如何利用多核的cpu呢? 这样的代码写起来好像会比较麻烦
         *    3. 函数式编程和多核cpu的盛行
         *
         * 二. What Stream?
         *     JDK 的新成员  java.util.stream.Stream
         *     它允许你用声明式的方式处理数据集合,透明地并行化处理,不需要写多线程的代码
         *     声明式: select * from dishes where calories < 400
         *
         *     简短的定义: 从支持数据处理操作的源生成的元素序列
         *
         *     元素序列: JDK提供了一个接口,可以访问特定元素类型的一组有序的值。集合是数据结构, 它的主要目的是以特定的时间/空间复杂度存储和访问元素
         *     。但流的目的在于表达计算。如filter、sorted、map.集合讲的是数据，流讲的是计算
         *
         *     源： 流会使用一个提供数据的源,如集合、数组等
         *
         *     数据处理操作：流的数据处理功能支持类似于数据库的操作。以及函数式编程中的常用操作。如filter、map、limit、reduce、sort等
         *
         *     流水线： 很多流操作本身会返回一个流,这样操作就可以连起来,形成一个大的流水线。
         *
         *     内部迭代: 流的迭代操作是在背后进行的。
         */
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
        compareCollectionAndStream(menu);


    }

    public static void compareCollectionAndStream(List<Dish> menu) {
        // 获取低热量的菜的名称,并按热量进行排序 Java 7
        List<Dish> lowCaloricDishes = new ArrayList<>();
        for (Dish dish : menu) {
            if (dish.getCalories() < 400) {
                lowCaloricDishes.add(dish);
            }
        }

        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return Integer.compare(o1.getCalories(), o2.getCalories());
            }
        });

        List<String> lowCaloricDishesName = new ArrayList<>();
        for (Dish dish : lowCaloricDishes) {
            lowCaloricDishesName.add(dish.getName());
        }

        // Java 8
        List<String> streamCollect = menu.stream()
                .filter(d -> d.getCalories() < 400)
                .sorted(comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(toList());

        // 并行流
        List<String> parallelStreamCollect = menu.parallelStream()
                .filter(d -> d.getCalories() < 400)
                .sorted(comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(toList());


        /**
         * 代码是声明性的方式写的: 直接写想完成什么,而不是说如何实现
         * 把几个基础的操作链接起来,来处理复杂的流水线
         *
         * 声明性—— 更简洁、更易读
         * 可复合—— 更灵活
         * 可并行—— 性能更好
         */

        System.out.println("=== Java7=====" + lowCaloricDishesName);
        System.out.println("=== Java8  Stream=====" + streamCollect);
        System.out.println("=== Java8  parallelStream=====" + parallelStreamCollect);
    }
}
