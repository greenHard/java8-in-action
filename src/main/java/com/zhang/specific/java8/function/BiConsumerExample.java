package com.zhang.specific.java8.function;

import java.util.function.BiConsumer;

/**
 * BiConsumer 例子
 *
 * @author <p>yuyang.zhang<p>
 * @date 2018-11-05 17:20
 * @since 1.0
 */
public class BiConsumerExample {
    public static void main(String[] args) {
        // accept
        BiConsumer<Long,Long> biConsumer = (x, y) -> System.out.println(x * y);
        biConsumer.accept(10L, 20L);
        System.out.println("==================");

        // andThen
        BiConsumer<Long, Long> andThenConsumer = biConsumer.andThen((x, y) -> System.out.println(x + y));
        andThenConsumer.accept(10L, 20L);
    }
}
