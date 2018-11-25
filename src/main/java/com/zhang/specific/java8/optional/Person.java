package com.zhang.specific.java8.optional;

import java.util.Optional;


public class Person {

    /**
     * 人可能有车,也可能没有车
     */
    private Optional<Car> car;

    public Optional<Car> getCar() {
        return this.car;
    }
}
