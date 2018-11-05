package com.zhang.specific.java8;

/**
 * 通用的过滤器
 *
 * @author <p>yuyang.zhang<p>
 * @date 2018-11-05 13:15
 * @since 1.0
 */
public interface Predicate<T> {

    /**
     * 抽象的过滤方法
     */
    boolean test(T t);
}
