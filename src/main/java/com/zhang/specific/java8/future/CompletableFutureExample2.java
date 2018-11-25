package com.zhang.specific.java8.future;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.stream.Collectors.toList;

/**
 * {@link java.util.concurrent.CompletableFuture}
 * <p>
 * Java8 异步编程
 * <p>
 */
public class CompletableFutureExample2 {

    public static void main(String[] args) throws InterruptedException {

        // thenApply((Function<? super T,? extends U> fn) 接受一个Function<? super T,? extends U>参数用来转换CompletableFuture
        // thenApplyAsync(Function<? super T,? extends U> fn)	接受一个Function<? super T,? extends U>参数用来转换CompletableFuture，使用ForkJoinPool
        // thenApplyAsync(Function<? super T,? extends U> fn, Executor executor) 接受一个Function<? super T,? extends U>参数用来转换CompletableFuture，使用指定的线程池
        CompletableFuture.supplyAsync(() -> 1)
                .thenApply(i -> Integer.sum(i, 10))
                .whenComplete((v, t) -> System.out.println(v));

        // thenCompose(Function<? super T, ? extends CompletionStage<U>> fn)	在异步操作完成的时候对异步操作的结果进行一些操作，并且仍然返回CompletableFuture类型。
        // thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn) 在异步操作完成的时候对异步操作的结果进行一些操作，并且仍然返回CompletableFuture类型。使用ForkJoinPool。
        //thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn,Executor executor) 在异步操作完成的时候对异步操作的结果进行一些操作，并且仍然返回CompletableFuture类型。使用指定的线程池。
        CompletableFuture.supplyAsync(() -> 1)
                .thenCompose(i -> CompletableFuture.supplyAsync(() -> 10 * i))
                .thenAccept(System.out::println);

        // thenCombine(CompletionStage<? extends U> other, BiFunction<? super T,? super U,? extends V> fn) 当两个CompletableFuture都正常完成后，执行提供的fn，用它来组合另外一个CompletableFuture的结果。
        // thenCombineAsync(...)
        // thenCombineAsync(...)
        CompletableFuture.supplyAsync(() -> 1)
                .thenCombine(CompletableFuture.supplyAsync(() -> 2.0d), (r1, r2) -> r1 + r2)
                .thenAccept(System.out::println);


        // thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super T,? super U> action) 当两个CompletableFuture都正常完成后，执行提供的action，用它来组合另外一个CompletableFuture的结果。
        // thenAcceptBothAsync(...)
        // thenAcceptBothAsync(...)
        CompletableFuture.supplyAsync(() -> 1)
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> 2.0d), (r1, r2) -> {
                    System.out.println(r1);
                    System.out.println(r2);
                    System.out.println(r1 + r2);
                });

        // whenComplete(BiConsumer<? super T,? super Throwable> action) 当CompletableFuture完成计算结果时对结果进行处理，或者当CompletableFuture产生异常的时候对异常进行处理。
        // whenCompleteAsync(...)
        // handle(BiFunction<? super T, Throwable, ? extends U> fn)	  当CompletableFuture完成计算结果或者抛出异常的时候，执行提供的fn
        // handleAsync(...)
        // handle()的参数是BiFunction，apply()方法返回R，相当于转换的操作。 类似于转化函数  所以，handle()相当于whenComplete()+转换。
        // 而whenComplete()的参数是BiConsumer，accept()方法返回void。 消费者

        // cc()
        CompletableFuture.supplyAsync(() -> 1)
                .handle((v, t) -> Integer.sum(v, 10))
                .whenComplete((v, t) -> System.out.println(v))
                .thenRun(() -> System.out.println("then run.."));


        // thenAccept(Consumer<? super T> action)	当CompletableFuture完成计算结果，只对结果执行Action，而不返回新的计算值
        // thenAcceptAsync（...）
        CompletableFuture.supplyAsync(() -> 1)
                .thenApply(i -> Integer.sum(i, 10))
                .thenAccept(System.out::println);


        /**
         * thenRun() vs thenAccept() vs thenApply()
         * thenAccept takes a Consumer and returns a T=Void
         * thenApply on the other hand takes a Function and returns a CF carrying the return value of the function.
         * thenRun takes a Runnable and returns a T=Void
         */

        Thread.sleep(1000L);
    }
}