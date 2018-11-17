package com.zhang.specific.java8.collector;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;

/**
 * Collector 源码分析
 */
public class CollectorApiExample4 {
    public static void main(String[] args) {

        /*
        public interface Collector<T, A, R> {
            Supplier<A> supplier();
            BiConsumer<A, T> accumulator();
            BinaryOperator<A> combiner();
            Function<A, R> finisher();
            // 告诉collect 在执行归约操作的时候可以应用哪些优化,比如并行化
            Set<Characteristics> characteristics();
        }
        // T 是流中药收集项目的泛型
        // A 是累加器的类型
        // R 是收集操作得到的对象的类型

        // 前面四个方法都会返回一个函数
        enum Characteristics {
            // accumulator 函数可以多个线程同时调用,且该收集器可以并行归约流
            CONCURRENT,
            // 归约结果不受流中项目的遍历和累积顺序的影响
            UNORDERED,
            // 完成器方法返回的函数是一个恒等函数,可以跳过。这种情况下,累加器对象将会直接用作归约过程的最终结果。
            累加器A 不加检查转成结果R是安全的
            IDENTITY_FINISH
        }
      */

        Collector<String, List<String>, List<String>> collector = new ToListCollector<>();
        List<String> result = Arrays.asList("Hello", "Lambda", "Collector", "Java 8", "Stream", "Collectors")
                .parallelStream()
                .filter(s -> s.length() >= 7)
                .collect(collector);
        System.out.println(result);

    }
}
