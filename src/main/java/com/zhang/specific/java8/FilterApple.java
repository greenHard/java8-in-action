package com.zhang.specific.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 过滤苹果
 */
public class FilterApple {


    public static void main(String[] args) {
        List<Apple> list = Arrays.asList(new Apple("yellow", 150), new Apple("green", 180), new Apple("red", 170));

        // 查找绿色的苹果
        List<Apple> greenApple = findGreenApple(list);
        System.out.println("greenApple: " + greenApple);

        // 将颜色作为参数
        List<Apple> greenApples = findAppleByColor(list, "green");
        System.out.println("greenApple: " + greenApples);
        List<Apple> redApples = findAppleByColor(list, "red");
        System.out.println("redApple: " + redApples);


        // 如果有需要筛选其他属性
        List<Apple> greenAnd160WeightApples = findGreenAnd160WeightApple(list, new GreenAnd160WeightFilter());
        System.out.println("绿色并且大于160的苹果: " + greenAnd160WeightApples);

        List<Apple> redAnd110WeightApples = findRedAnd110WeightApple(list, new RedAnd110WeightFilter());
        System.out.println("红色并且大于110的苹果: " + redAnd110WeightApples);


        // 面向接口编程
        List<Apple> apples = findApples(list, new GreenAnd160WeightFilter());
        System.out.println("绿色并且大于160的苹果: " + apples);

        List<Apple> apples1 = findApples(list, new RedAnd110WeightFilter());
        System.out.println("红色并且大于110的苹果: " + apples1);


        // 匿名内部类的方式,行为参数化
        List<Apple> yellowList = findApples(list, new AppleFilter() {
            @Override
            public boolean filter(Apple apple) {
                return "yellow".equals(apple.getColor());
            }
        });
        System.out.println("黄色的苹果: " + yellowList);


        // lambda的方式
        List<Apple> lambdaResult = findApples(list, apple -> "green".equals(apple.getColor()));
        System.out.println("绿色的苹果:" + lambdaResult);

        // Runnable 接口


        // 将list类型抽象化
        List<Apple> appFilters = filter(list, apple -> "green".equals(apple.getColor()));
        System.out.println("使用通用过滤方法过滤的结果" + appFilters);

        List<Integer> numbers = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        List<Integer> resultNumbers = filter(numbers, i -> i % 10 == 0);
        System.out.println("过滤之后的集合为: " +resultNumbers);


        /**
         *  为什么会有lambda?
         *
         *  Lambda的写法?
         *
         *  Lambda探索?
         *
         *
         *
         */





    }


    /**
     * 通用过滤的方法
     *
     * @param list   过滤的集合
     * @param filter 过滤条件实现
     * @param <T>    泛型 T
     * @return 过滤之后的结果
     */
    private static <T> List<T> filter(List<T> list, Predicate<T> filter) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (filter.test(t)) {
                result.add(t);
            }
        }
        return result;
    }


    /**
     * 查找绿色大于160g的苹果
     */
    private static List<Apple> findGreenAnd160WeightApple(List<Apple> apples, GreenAnd160WeightFilter greenAnd160WeightFilter) {
        List<Apple> list = new ArrayList<>();

        for (Apple apple : apples) {
            if (greenAnd160WeightFilter.filter(apple)) {
                list.add(apple);
            }
        }
        return list;
    }


    /**
     * 查找红色大于110g的苹果
     */
    private static List<Apple> findRedAnd110WeightApple(List<Apple> apples, RedAnd110WeightFilter redAnd110WeightFilter) {
        List<Apple> list = new ArrayList<>();

        for (Apple apple : apples) {
            if (redAnd110WeightFilter.filter(apple)) {
                list.add(apple);
            }
        }
        return list;
    }


    /**
     * 需要什么由传进来的参数做定义
     */
    private static List<Apple> findApples(List<Apple> apples, AppleFilter appleFilter) {
        List<Apple> list = new ArrayList<>();

        for (Apple apple : apples) {
            if (appleFilter.filter(apple)) {
                list.add(apple);
            }
        }
        return list;
    }


    /**
     * 查询绿色苹果
     */
    private static List<Apple> findGreenApple(List<Apple> apples) {
        List<Apple> list = new ArrayList<>();
        for (Apple apple : apples) {
            if ("green".equals(apple.getColor())) {
                list.add(apple);
            }
        }
        return list;
    }

    /**
     * 通过颜色筛选苹果
     */
    private static List<Apple> findAppleByColor(List<Apple> apples, String color) {
        List<Apple> list = new ArrayList<>();
        for (Apple apple : apples) {
            if (color.equals(apple.getColor())) {
                list.add(apple);
            }
        }
        return list;
    }


}
