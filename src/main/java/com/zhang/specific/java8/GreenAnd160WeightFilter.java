package com.zhang.specific.java8;

/**
 * 绿色并且大于160g的苹果
 *
 * @author yuyang.zhang
 */
public class GreenAnd160WeightFilter implements AppleFilter {


    @Override
    public boolean filter(Apple apple) {
        return "green".equals(apple.getColor()) && apple.getWeight() > 160;
    }
}
