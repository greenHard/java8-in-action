package com.zhang.specific.java8.collector;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

public class ToListCollector<T> implements Collector<T,List<T>,List<T>> {
    @Override
    public Supplier<List<T>> supplier() {
        // 调用的过程中会产生一个新的累加器实例
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        // 将元素添加到结果容器中
        return List::add;
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        // 提供一个归约操作使用的函数
        return (list1,list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    @Override
    public Function<List<T>, List<T>> finisher() {
        // 对结果容器最终转化
        // 累加器正好符合结果预期不需要转化
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH,CONCURRENT));
    }
}
