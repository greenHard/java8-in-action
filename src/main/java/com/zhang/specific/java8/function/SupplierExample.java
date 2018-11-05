package com.zhang.specific.java8.function;

import java.util.function.Supplier;

/**
 * Supplier 例子
 *
 * @author <p>yuyang.zhang<p>
 * @date 2018-11-05 17:18
 * @since 1.0
 */
public class SupplierExample {
    public static void main(String[] args) {
        // get()
        Supplier<StringBuilder> supplier = StringBuilder::new;
        System.out.println(supplier.get().getClass());

        // BooleanSupplier, IntSupplier, LongSupplier, DoubleSupplier
    }
}
