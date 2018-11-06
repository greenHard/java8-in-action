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
        Function<Integer, Integer> function = s -> s * s;
        Integer result = function.apply(2);
        System.out.println("apply : " + result);


        // compose()
        Function<Integer, Integer> compose = function.compose(s -> s * 3);
        System.out.println("compose: " + compose.apply(2));


        //andThen()
        Function<Integer, Integer> andThen = function.andThen(s -> s * 3);
        System.out.println("andThen: " + andThen.apply(2));

        // identity()
        
    }
}


