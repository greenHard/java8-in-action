package com.zhang.specific.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 过滤苹果
 */
public class FilterApple {


    public static void main(String[] args) {
        List<Apple> list = Arrays.asList(new Apple("yellow", 150), new Apple("green", 120), new Apple("red", 170));
        // 查找绿色的苹果
        List<Apple> greenApple = findGreenApple(list);
        System.out.println("greenApple: " + greenApple);

        // 将颜色作为参数
        List<Apple> greenApples = findAppleByColor(list, "green");
        System.out.println("greenApple: " + greenApples);
        List<Apple> redApples = findAppleByColor(list, "red");
        System.out.println("redApple: " + redApples);


        // 如果有需要筛选其他属性 -- 行为参数化

/*
        List<Apple> result = findApple(list, new GreenAnd160WeightFilter());
        System.out.println(result);

        List<Apple> yellowList = findApple(list, new AppleFilter() {
            @Override
            public boolean filter(Apple apple) {
                return "yellow".equals(apple.getColor());
            }
        });

        System.out.println(yellowList);*/
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
