package com.zhang.specific.java8.method;

import com.google.common.base.Supplier;
import com.zhang.specific.java8.Apple;
import com.zhang.specific.java8.function.MyFunction;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 方法引用
 *
 * @author <p>yuyang.zhang<p>
 * @date 2018-11-06 13:39
 * @since 1.0
 */
public class MethodReference {
    public static void main(String[] args) {
        /**
         *
         * 方法引用:
         *   静态方法引用：ClassName::methodName
         *   实例上的实例方法引用：instanceReference::methodName
         *   类型上的实例方法引用：ClassName::methodName
         *   超类上的实例方法引用：super::methodName
         *   构造方法引用：Class::new
         *   数组构造方法引用：TypeName[]::new
         */

        // 静态方法引用：ClassName::methodName
        int value = Integer.parseInt("100");
        System.out.println(value);
        Function<String, Integer> f = Integer::parseInt;
        Integer result = f.apply("100");
        System.out.println(result);
        System.out.println("=====================");


        // 类型上的实例方法引用 ClassName::methodName
        List<Integer> list = Arrays.asList(2,5,4,6,7);
        list.stream().forEach(a -> System.out.println(a));
        System.out.println("==========================");
        list.stream().forEach(System.out::println);
        System.out.println("========================");


        // 实例上的实例方法引用：instanceReference::methodName
        String str = new String("java 8");
        Supplier<Integer> suppiler = str::length;
        System.out.println("instanceReference: "+ suppiler.get());
        System.out.println("========================");


        // 构造方法引用：Class::new
        BiFunction<String, Long, Apple> appleBiFunction = Apple::new;
        Apple apple = appleBiFunction.apply("red", 100L);
        System.out.println(apple);
        System.out.println("========================");

        // 5个参数, 6个参数, 7个参数?
        MyFunction<String,Long,String,Apple> myFunction = Apple::new;
        Apple myApple = myFunction.apply("green", 100L, "apple");
        System.out.println(myApple);
    }
}
