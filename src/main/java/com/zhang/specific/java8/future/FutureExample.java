package com.zhang.specific.java8.future;

import com.sun.xml.internal.ws.util.CompletedFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * {@link Future}
 *  异步编程
 */
public class FutureExample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // 把衣服扔到洗衣店
        Future<String> future = executorService.submit(() -> {
            try {
                Thread.sleep(10000L);
                return "I am finished.";
            } catch (InterruptedException e) {
                return "I am Error.";
            }
        });

        //...
        //...
        //...
        //...
        //String value = future.get(10, TimeUnit.MICROSECONDS);
        while (!future.isDone()) {
            Thread.sleep(10);
        }
        System.out.println(future.get());

        /**
         * Future 的问题?
         * 1. 当任务完成后能不能回调接口告诉我,不是简单地阻塞等到操作的结果
         * 2. 将两个异步计算合并成一个 -- 计算相互独立,但第二个计算依赖第一个的结果
         * 3. 等待Future集合中的所有任务都完成
         * 4. 仅仅等到Future集合中最快的任务完成(有可能通过不同的方式获取一个值,只想要最快的方式.)
         */
    }
}
