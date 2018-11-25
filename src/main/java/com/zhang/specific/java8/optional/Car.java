package com.zhang.specific.java8.optional;

import java.util.Optional;

public class Car {

    /**
     * 车可能有保险,也可能没有保险
     */
    private Optional<Insurance> insurance;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }
}
