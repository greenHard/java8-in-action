package com.zhang.specific.java8.optional;

import java.util.Optional;

/**
 * 最初是例子的演示
 */
public class OptionalExample2 {
    public static void main(String[] args) {
        Optional.ofNullable(getInsuranceNameByOptional(null)).ifPresent(System.out::println);
    }

    private static String getInsuranceNameByOptional(Person person) {
        return Optional.ofNullable(person)
                .flatMap(Person::getCar).flatMap(Car::getInsurance)
                .map(Insurance::getName).orElse("Unknown");
    }
}
