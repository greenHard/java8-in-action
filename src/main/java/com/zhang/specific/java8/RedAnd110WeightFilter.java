package com.zhang.specific.java8;

/**
 * 红色并且大于110g的苹果
 *
 * @author yuyang.zhang
 */
public class RedAnd110WeightFilter implements AppleFilter {


    @Override
    public boolean filter(Apple apple) {
        return "red".equals(apple.getColor()) && apple.getWeight() > 110;
    }
}
