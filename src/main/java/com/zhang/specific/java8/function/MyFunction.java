package com.zhang.specific.java8.function;

/**
 * @author <p>yuyang.zhang<p>
 * @date 2018-11-06 13:58
 * @since 1.0
 */
@FunctionalInterface
public interface MyFunction<T, U, K, R> {

    R apply(T t, U u, K k);
}
