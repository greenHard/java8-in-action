package com.zhang.specific.java8.future;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

public class Shop {

    private final List<Shop> shops = Arrays.asList(
            new Shop("Best Price"),
            new Shop("BuyItAll")
    );
    private String name;

    private static final Random random = new Random(System.currentTimeMillis());

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    public List<String> findPrices(String product){
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f",shop.getName(),shop.getPrice(product)))
                .collect(toList());
    }

    public List<String> findPricesAsync(String product){
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f",shop.getName(),shop.getPrice(product)))
                .collect(toList());
    }

    /**
     * 将同步改成异步方法
     */
    public Future<Double> getPriceAsync(String product) {
        // 正常写法
        //CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        //new Thread(() -> {
        //    double price = calculatePrice(product);
        //    futurePrice.complete(price);
        //}).start();
        //return futurePrice;

        // 使用工厂方法
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    private double calculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    private static void delay() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Shop(String name) {
        this.name = name;
    }
}
