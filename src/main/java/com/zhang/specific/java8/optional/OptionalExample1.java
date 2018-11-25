package com.zhang.specific.java8.optional;

import java.util.Optional;

/**
 * {@link java.util.Optional}
 */
public class OptionalExample1 {
    public static void main(String[] args) {
        // person.getCar().getInsurance().getName() 问题?
        /**
         * 引入Optional的意图,并非要消除每一个null引用。
         * 与此相反,它的目标是帮助我们设计出普适的API,让程序员看到方法签名,就能了解它是否能够接收一个Optional的值。
         * 这种强制会让你更积极地将变量从Optional中解包出来,直面缺失的变量值。
         */

        // 1. 创建
        // 1.1 返回一个空的Optional对象
        Optional<Insurance> insuranceOptional = Optional.<Insurance>empty();
        insuranceOptional.get();
        // 1.2 根据非空值创建Optional
        Optional<Insurance> insuranceOptional1 = Optional.of(new Insurance());
        insuranceOptional1.get();
        // 1.3 可接收null的Optional
        Optional<Insurance> objectOptional = Optional.ofNullable(null);
        // 允许在Optional对象不包含值的时候提供一个默认值
        objectOptional.orElse(new Insurance());
        objectOptional.orElseGet(Insurance::new);
        // 自定义抛出的异常
        objectOptional.orElseThrow(RuntimeException::new);
        objectOptional.orElseThrow(() -> new RuntimeException("Not have reference"));

        Insurance insurance = insuranceOptional1.filter(i -> i.getName() != null).get();
        System.out.println(insurance);

        Optional<String> nameOptional = insuranceOptional1.map(i -> i.getName());
        System.out.println(nameOptional.orElse("empty Value"));
        System.out.println(nameOptional.isPresent());
        nameOptional.ifPresent(System.out::println);


        System.out.println(getInsuranceName(null));
        System.out.println(getInsuranceNameByOptional(null));
    }

    private static String getInsuranceName(Insurance insurance) {
        if (null == insurance)
            return "unknown";
        return insurance.getName();
    }

    private static String getInsuranceNameByOptional(Insurance insurance) {
        return Optional.ofNullable(insurance).map(Insurance::getName).orElse("unknown");
    }
}
