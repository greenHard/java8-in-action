package com.zhang.specific.java8.future;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class CompletableFutureExample3 {
    public static void main(String[] args) throws InterruptedException {
        // applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn)
        // 当任意一个CompletableFuture完成的时候，fn会被执行，它的返回值会当作新的CompletableFuture<U>的计算结果。
        // applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn)
        // 当任意一个CompletableFuture完成的时候，fn会被执行，它的返回值会当作新的CompletableFuture<U>的计算结果。使用ForkJoinPool
        // applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn,Executor executor)
        // 当任意一个CompletableFuture完成的时候，fn会被执行，它的返回值会当作新的CompletableFuture<U>的计算结果。使用指定的线程池
        CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 1");
            return CompletableFutureExample1.get();
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 2");
            return CompletableFutureExample1.get();
        }), v -> v * 10)
                .thenAccept(System.out::println);

        // acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action)
        //  当任意一个CompletableFuture完成的时候，action这个消费者就会被执行
        // acceptEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn)
        //  当任意一个CompletableFuture完成的时候，action这个消费者就会被执行。使用ForkJoinPool
        // acceptEitherAsync( CompletionStage<? extends T> other, Function<? super T, U> fn,Executor executor)
        //  当任意一个CompletableFuture完成的时候，action这个消费者就会被执行。使用指定的线程池
        CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 1");
            return CompletableFutureExample1.get();
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 2");
            return CompletableFutureExample1.get();
        }), System.out::println);


        // runAfterBoth()
        /*CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " is running.");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " is running.");
            return 2;
        }), () -> System.out.println("done"));*/


        // runAfterEither() 任意一个完成
       /* CompletableFuture.supplyAsync(() -> {
            System.out.println("I am future 1");
            return CompletableFutureExample1.get();
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("I am future 2");
            return CompletableFutureExample1.get();
        }), () -> System.out.println("done."));*/


        // anyOf(CompletableFuture<?>... cfs) 在任何一个Future对象结束后结束，并返回一个future。
        // allOf(CompletableFuture<?>... cfs) 在所有Future对象完成后结束，并返回一个future。
        // anyOf 和 acceptEither、applyToEither的区别在于，后两者只能使用在两个future中，而anyOf可以使用在多个future中。
        List<CompletableFuture<Double>> collect = Stream.of(1, 2, 3, 4)
                .map(i -> CompletableFuture.supplyAsync(CompletableFutureExample1::get))
                .collect(toList());

        CompletableFuture.anyOf(collect.toArray(new CompletableFuture[collect.size()]))
                .thenRun(() -> System.out.println("done"));

        Thread.currentThread().join();
    }
}
