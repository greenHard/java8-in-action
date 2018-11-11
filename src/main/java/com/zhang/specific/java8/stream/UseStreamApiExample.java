package com.zhang.specific.java8.stream;


import com.google.common.base.Charsets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 使用流
 *
 * @author yuyang.zhang
 */
public class UseStreamApiExample {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));

        //1.筛选和切片
        //filterAndSlice(menu);

        //2. 映射 mapping
        //mapping(menu);

        //3. 查找和匹配
        //findAndMatch(menu);

        // 4. 归约
        //reduce(menu);

        // 5. 数值流
        //numberStream(menu);

        // 6. 构建流
        buildStream(menu);
    }

    /**
     * 1.筛选和切片
     */
    private static void filterAndSlice(List<Dish> menu) {

        // 1.1 filter
        List<Dish> vegetationMenu = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(toList());
        System.out.println("===========filter==========");
        System.out.println(vegetationMenu);

        // 1.2 distinct
        System.out.println("===========distinct==========");
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 4, 5, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);

        // 1.3 limit
        System.out.println("===========limit==========");
        List<Dish> limitDishes = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .limit(3)
                .collect(toList());
        System.out.println(limitDishes);

        // 1.3 skip 扔掉前两个元素
        System.out.println("===========skip==========");
        List<Dish> skipDishes = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .collect(toList());
        System.out.println(skipDishes);
    }

    /**
     * 2. 映射 mapping
     */
    private static void mapping(List<Dish> menu) {
        // 2.1 map
        System.out.println("===========map==========");
        List<String> dishNames = menu.stream()
                .map(Dish::getName)
                .collect(toList());
        System.out.println("dishNames: " + dishNames);

        List<Integer> dishNamesLength = menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(toList());
        System.out.println("dishNamesLength: " + dishNamesLength);

        // 2.2 flatMap 扁平化
        // flatMap 把一个流中的每个值都转化成另外一个流,然后把所有流连接起来成为一个流
        // 将多个Stream连接成一个Stream，这时候不是用新值取代Stream的值，与map有所区别，这是重新生成一个Stream对象取而代之
        System.out.println("===========flatMap==========");
        List<String> words = Arrays.asList("Hello", "World");

        // 使用map结果不是我们要的
        List<String[]> collect = words.stream()
                .map(w -> w.split(""))
                .distinct()
                .collect(toList());
        System.out.println(collect);

        List<Stream<String>> collect2 = words.stream()
                .map(w -> w.split(""))
                .map(Arrays::stream)
                .distinct()
                .collect(toList());
        System.out.println(collect2);

        List<String> flatMapCollect = words.stream()
                .map(w -> w.split(""))
                .flatMap(Arrays::stream) // 将各个生成的流扁化成单个流
                .distinct()
                .collect(toList());
        System.out.println(flatMapCollect);

        // https://www.jianshu.com/p/8d80dcb4e7e0 这个例子也比较形象


    }


    /**
     * 3. 查找和匹配
     */
    private static void findAndMatch(List<Dish> menu) {
        // 3.1 anyMatch
        System.out.println("===========anyMatch==========");
        if (menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("这个菜单里面包含了素食...");
        }

        // 3.2 allMatch
        System.out.println("===========allMatch==========");
        boolean isHealthy = menu.stream().allMatch(d -> d.getCalories() < 1000);
        System.out.println("是否所有的菜品热量都小于1000,结果是: " + isHealthy);

        // 3.3 noneMatch
        System.out.println("===========noneMatch==========");
        isHealthy = menu.stream().noneMatch(d -> d.getCalories() >= 1000);
        System.out.println("是否所有的菜品没有热量都大于1000,结果是: " + isHealthy);

        // 3.4 findAny
        System.out.println("===========findAny==========");
        menu.stream().findAny()
                .ifPresent(System.out::println);

        // 3.4 findFirst
        System.out.println("===========findFirst==========");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        numbers.stream()
                .map(x -> x * x)
                .filter(x -> x % 3 == 0)
                .findFirst()
                .ifPresent(System.out::println);

        // anyMatch、allMatch、noneMatch、findAny、findFirst这三个操作都是终端操作,而且都用到了短路的策略

    }


    /**
     * 4. 归约
     */
    private static void reduce(List<Dish> menu) {
        // 4.1 reduce
        System.out.println("===========reduce==========");
        Integer sum = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, (a, b) -> a + b);

        Integer sum2 = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println(sum);
        System.out.println(sum2);


        // 4.2 max
        System.out.println("===========max==========");
        menu.stream()
                .map(Dish::getCalories)
                .reduce(Integer::max)
                .ifPresent(System.out::println);

        // 4.3 min
        System.out.println("===========min==========");
        menu.stream()
                .map(Dish::getCalories)
                .reduce(Integer::min)
                .ifPresent(System.out::println);

    }


    /**
     * 5. 数值流
     */
    private static void numberStream(List<Dish> menu) {
        // 5.1. 有一个自动装箱的成本
        // 优化,原始类型流  IntStream、DoubleStream、LongStream
        System.out.println("===========IntStream==========");
        int sum = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum(); // 这里还支持max,min,average等方法
        System.out.println(sum);

        // 5.2. 转化成对象流  boxed
        System.out.println("===========boxed==========");
        IntStream intStream = menu.stream()
                .mapToInt(Dish::getCalories);
        Stream<Integer> integerStream = intStream.boxed();

        // 5.3. rangeClosed
        // 1-100 之间偶数统计
        System.out.println("===========count==========");
        long count = IntStream.rangeClosed(1, 100)
                .filter(i -> i % 2 == 0)
                .count();
        System.out.println(count);

    }


    /**
     * 6. 构建流
     */
    private static void buildStream(List<Dish> menu) {
        // 6.1 由值创建流
        System.out.println("===========number==========");
        Stream<String> stream = Stream.of("Java", "In", "Action");
        stream.map(String::toLowerCase)
                .forEach(System.out::println);

        // 6.2 数组创建流
        System.out.println("===========array==========");
        int[] numbers = {1, 2, 3, 4, 5, 6};
        int sum = Arrays.stream(numbers).sum();
        System.out.println(sum);

        // 6.3 文件生成流
        // java.nio.file.Files
        System.out.println("===========file==========");
        long uniqueWords;
        try (Stream<String> lines = Files.lines(Paths.get("data.txt"), Charsets.UTF_8)) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split("")))
                    .distinct()
                    .count();
            System.out.println(uniqueWords);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 6.4 无限流  -- 由函数生成流
        System.out.println("===========iterate==========");
        Stream.iterate(0, n -> n + 2)
                .limit(5)
                .forEach(System.out::println);

        // 斐波纳契数列
        // 0,1,1,2,3,5,8,13...
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(20)
                .map(t -> t[0])
                .forEach(System.out::println);


        // generate
        System.out.println("===========generate==========");
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);






    }
}
