package com.zhang.specific.java8.stream;

/**
 * @author <p>yuyang.zhang<p>
 * @date 2018-11-13 19:42
 * @since 1.0
 */
public class Hello {

    int i = 0;

    int sum = 0;

    Runnable r1 = () -> {System.out.println(toString());};


    Runnable r2 = () -> {
        //int i = 1;
        int sum = 0;
        // 这里会出现编译错误，因为i已经在for循环外部声明过了sum += i;
        for (int i = 1; i < 10; i += 1) {
            sum += i;
        }
    };

    Runnable r3 = new Runnable() {
        int i = 1;
        int sum = 0;
        @Override
        public void run() {
            for (int i = 1; i < 10; i += 1) {
                sum += i;
            }
            System.out.println(this);
        }
    };

    public static void main(String... args) {
        new Hello().r1.run();
        new Hello().r2.run();
        new Hello().r3.run();
    }

    @Override
    public String toString() {
        return "Hello, world";
    }
}
