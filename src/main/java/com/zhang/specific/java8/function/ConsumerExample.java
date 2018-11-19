package com.zhang.specific.java8.function;

import java.util.function.Consumer;

/**
 * Consumer 例子
 *
 * @author <p>yuyang.zhang<p>
 * @date 2018-11-05 17:19
 * @since 1.0
 */
public class ConsumerExample {
    public static void main(String[] args) {
        // accept()
        //Runnable runnable = () -> System.out.println("1111");
        Consumer<Long> consumer = x -> System.out.println(x + 2);
        consumer.accept(7L);
        System.out.println("==============");

        // andThen()
        Consumer<Long> andThenConsumer = consumer.andThen(x -> System.out.println(x * 2));
        andThenConsumer.accept(7L);

        // DoubleConsumer IntConsumer, LongConsumer
    }
}
