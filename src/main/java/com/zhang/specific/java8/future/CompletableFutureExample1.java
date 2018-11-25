package com.zhang.specific.java8.future;

import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static java.util.stream.Collectors.toList;


/**
 * {@link java.util.concurrent.CompletableFuture}
 * <p>
 * Java8 异步编程
 * <p>
 */
public class CompletableFutureExample1 {
    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 1. complete(T t) 完成异步执行，并返回future的结果
        // completeExceptionally(Throwable ex) 异步执行不正常的结束
/*        CompletableFuture<Double> future = new CompletableFuture<>();
        new Thread(() -> {
            try {
                Thread.sleep(100L);
                // If not already completed, sets the value returned by {@link
                // #get()} and related methods to the given value.
                future.complete(1000d);
            } catch (InterruptedException e) {
                // If not already completed, causes invocations of {@link #get()}
                // and related methods to throw the given exception.
                future.completeExceptionally(e);
            }

        }).start();

        System.out.println("..............");
        future.whenComplete((v, t) -> {
            System.out.println(v);
            t.printStackTrace();
        });*/

        // 1.supplyAsync(Supplier<U> supplier) 使用ForkJoinPool.commonPool()作为它的线程池执行异步代码，异步操作有返回值
        // supplyAsync(Supplier<U> supplier,Executor executor) 使用指定的thread pool执行异步代码，异步操作有返回值
        // runAsync(Runnable runnable) 使用ForkJoinPool.commonPool()作为它的线程池执行异步代码。没有返回值
        // runAsync(Runnable runnable, Executor executor) 使用指定的thread pool执行异步代码。没有返回值
        CompletableFuture<Double> future = CompletableFuture.supplyAsync(CompletableFutureExample1::get);

        future.whenComplete((v, t) -> {
            System.out.println(v);
            t.printStackTrace();
        });

        TimeUnit.SECONDS.sleep(10);

        // 3. join()
       /* long start = System.currentTimeMillis();
        List<Double> doubles = Arrays.asList(random.nextDouble(), random.nextDouble(), random.nextDouble(), random.nextDouble(), random.nextDouble());
        System.out.println(doubles);
        List<CompletableFuture<Double>> futures = doubles
                .stream()
                .map(d -> CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(10000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return d * 10d;
                }))
                .collect(toList());
        // Returns the result value when complete, or throws an
        // (unchecked) exception if completed exceptionally
        List<Double> collect = futures.stream().parallel().map(CompletableFuture::join).collect(toList());
        System.out.println(collect);
        System.out.println(System.currentTimeMillis()-start);*/

        // 1.4 thenCompose() get()
        Double value = CompletableFuture.supplyAsync(CompletableFutureExample1::get)
                .whenComplete((v, t) -> System.out.println(">>>>" + v))
                .thenCompose(i -> CompletableFuture.supplyAsync(() -> i + 10))
                .get();
        System.out.println(value);
    }

    public static double get() {
        try {
            Thread.sleep(1000L);
            return random.nextDouble();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
