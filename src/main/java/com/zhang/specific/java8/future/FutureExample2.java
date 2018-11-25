package com.zhang.specific.java8.future;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自定义Future
 */
public class FutureExample2 {
    public static void main(String[] args) {
        CustomFuture<String> future = invoke(() -> {
            try {
                Thread.sleep(10000L);
                return "I am Finished.";
            } catch (InterruptedException e) {
                return "I am Error";
            }
        });

        future.setCompletable(new Completable<String>() {
            @Override
            public void complete(String s) {
                System.out.println(s);
            }

            @Override
            public void exception(Throwable cause) {
                System.out.println("error");
                cause.printStackTrace();
            }
        });

        System.out.println(".........");
        System.out.println(future.get());
        System.out.println(future.get());
    }

    private static <T> CustomFuture<T> invoke(Callable<T> callable) {
        AtomicReference<T> result = new AtomicReference<>();

        AtomicBoolean finished = new AtomicBoolean(false);

        CustomFuture<T> future = new CustomFuture<T>() {
            private Completable<T> completable;

            @Override
            public T get() {
                return result.get();
            }

            @Override
            public boolean isDone() {
                return finished.get();
            }

            @Override
            public void setCompletable(Completable<T> completable) {
                this.completable = completable;
            }

            @Override
            public Completable<T> getCompletable() {
                return completable;
            }
        };

        Thread t = new Thread(() -> {
            try {
                T value = callable.action();
                result.set(value);
                finished.set(true);
                if (future.getCompletable() != null)
                    future.getCompletable().complete(value);
            } catch (Throwable cause) {
                if (future.getCompletable() != null)
                    future.getCompletable().exception(cause);
            }
        });

        t.start();

        return future;
    }


    private interface CustomFuture<T> {

        T get();

        boolean isDone();

        void setCompletable(Completable<T> completable);

        Completable<T> getCompletable();
    }

    private interface Callable<T> {
        T action();
    }

    private interface Completable<T> {

        void complete(T t);

        void exception(Throwable cause);
    }
}
