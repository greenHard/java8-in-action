package com.zhang.specific.java8.defaultMethod;

/**
 *  默认方法
 *
 *  @author yuyang.zhang
 */
public class DefaultMethodInExample {
    public static void main(String[] args) {
        // 1. 为什么需要默认方法
        // (1) API的向前兼容 (2) 更好的支持lambda

        // 2. 默认方法的写法
        // Collection、Collections

        // 3. 多实现与菱形继承
        C c = new C();
        c.hello();
    }

    private interface A {
        default void hello() {
            System.out.println("==A.hello==");
        }
    }

    private interface B {
        default void hello() {
            System.out.println("==B.hello==");
        }
    }

    private static class C implements B, A {
        @Override
        public void hello() {
            B.super.hello();
        }
    }
}
