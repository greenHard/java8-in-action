package com.zhang.specific.java8.function;


import java.util.function.Function;

/**
 * Function 例子
 *
 * @author <p>yuyang.zhang<p>
 * @date 2018-11-05 17:19
 * @since 1.0
 */
public class FunctionExample {
    public static void main(String[] args) {
        // apply()
        Function<String, String> function = s -> s.toLowerCase();
        String apply = function.apply("Java 8");
        System.out.println("apply : " + apply);

        //andThen()
        Function<String, String> andThenFunction = function.andThen(s -> s.toUpperCase());
        String name = andThenFunction.apply("zhang");
        System.out.println("andThen: "+ name);


    }
}
