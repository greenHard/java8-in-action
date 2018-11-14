package com.zhang.specific.java8.function;

import java.util.function.Predicate;

/**
 * Predicate 例子
 *
 * @author <p>yuyang.zhang<p>
 * @date 2018-11-05 17:18
 * @since 1.0
 */
public class PredicateExample {

    public static void main(String[] args) {
        // test()
        Predicate<Integer> predicate = x -> x >  100;
        System.out.println("test: " + predicate.test(99));
        System.out.println("========================");

        // negate()
        System.out.println("negate:" + predicate.negate().test(99));
        System.out.println("========================");

        // and()
        Predicate<Integer> andPredicate = predicate.and(x -> x % 2 == 0);
        System.out.println("and :" + andPredicate.test(102));
        System.out.println("and :" + andPredicate.test(101));
        System.out.println("========================");
        //
        // or()
        Predicate<Integer> orPredicate = predicate.or(x -> x % 2 == 0);
        System.out.println("or :" + orPredicate.test(8));
        System.out.println("or :" + orPredicate.test(101));
        System.out.println("========================");

        // IntPredicate, LongPredicate, DoublePredicate
    }
}
