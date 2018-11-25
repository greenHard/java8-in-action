package com.zhang.specific.java8.parallelStream;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 并行流
 */
public class ParallelStreamExample1 {

    public static void main(String[] args) {
        /**
         * 并行流默认使用的是ForkJoinPool,默认线程数量\是处理器的数量
         */
        System.out.println(Runtime.getRuntime().availableProcessors());

        // long result = sequentialSum(100000);
        // System.out.println(result);

        // 测试并行流的性能
        //System.out.println("The best process time(normalAdd)=>" + measureSumPerformance(ParallelStreamExample1::normalAdd, 100_000_000) + " MS");
        //System.out.println("The best process time(iterateStream)=>" + measureSumPerformance(ParallelStreamExample1::iterateStream, 10_000_000) + " MS");
        //System.out.println("The best process time(parallelStream)=>" + measureSumPerformance(ParallelStreamExample1::parallelStream, 10_000_000) + " MS");
        //System.out.println("The best process time(parallelStream2)=>" + measureSumPerformance(ParallelStreamExample1::parallelStream2, 10_000_000) + " MS");
        //System.out.println("The best process time(parallelStream3)=>" + measureSumPerformance(ParallelStreamExample1::parallelStream3, 100_000_000) + " MS");

        // 错用并行流
        System.out.println("The best process time(sideEffectSum)=>" + measureSumPerformance(ParallelStreamExample1::sideEffectSum, 100_000_000) + " MS");
        System.out.println("The best process time(sideEffectSum2)=>" + measureSumPerformance(ParallelStreamExample1::sideEffectSum2, 100_000_000) + " MS");

        /**
         * 高效使用并行流:
         * 1. 测试性能
         * 2. 留意装箱
         * 3. 某些操作本身在并行流上的效率比顺序流差,特别是limit和findFirst的顺序操作
         * 4. 对于较小的数据量,并行流不是一个好的方式
         */
    }


    /**
     * 求和,当n很大的时候,并行流是个很好的方式
     * .parallel() 转换成并行流
     * .sequential() 转化成顺序流
     */
    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                //.sequential()
                .reduce(0L, Long::sum);
    }

    private static long measureSumPerformance(Function<Long, Long> adder, long limit) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long startTimestamp = System.currentTimeMillis();
            long result = adder.apply(limit);
            long duration = System.currentTimeMillis() - startTimestamp;
            System.out.println("The result of sum=>" + result);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }


    private static long iterateStream(long limit) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(limit).reduce(0L, Long::sum);
    }

    private static long parallelStream(long limit) {
        return Stream.iterate(1L, i -> i + 1).parallel()
                .limit(limit).reduce(0L, Long::sum);
    }

    private static long parallelStream2(long limit) {
        return Stream.iterate(1L, i -> i + 1).mapToLong(Long::longValue).parallel()
                .limit(limit).reduce(0L, Long::sum);
    }

    private static long parallelStream3(long limit) {
        // 没有拆装箱的开销
        return LongStream.rangeClosed(1, limit).parallel().reduce(0L, Long::sum);
    }

    private static long normalAdd(long limit) {
        long result = 0L;
        for (long i = 1L; i < limit; i++) {
            result += i;
        }
        return result;
    }

    /**
     * forEach 副作用,会改变多个线程的共享变量的可变状态
     */
    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }


    private static class Accumulator {
        public long total = 0;

        public void add(long value) {
            total += value;
        }
    }

    public static long sideEffectSum2(long n) {
        Accumulator2 accumulator = new Accumulator2();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total.get();
    }

    private static class Accumulator2 {
        public AtomicLong total = new AtomicLong(0L);

        public void add(long value) {
            total.addAndGet(value);
        }
    }
}
