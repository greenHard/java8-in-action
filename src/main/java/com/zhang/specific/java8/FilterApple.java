package com.zhang.specific.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 过滤苹果
 */
public class FilterApple {


    public static void main(String[] args) {
        List<Apple> list = Arrays.asList(new Apple("yellow", 150), new Apple("green", 180), new Apple("red", 170));

        // 查找绿色的苹果
        List<Apple> greenApple = findGreenApple(list);
        System.out.println("greenApple: " + greenApple);

        // 将颜色作为参数
        List<Apple> greenApples = findAppleByColor(list, "green");
        System.out.println("greenApple: " + greenApples);
        List<Apple> redApples = findAppleByColor(list, "red");
        System.out.println("redApple: " + redApples);


        // 如果有需要筛选其他属性
        List<Apple> greenAnd160WeightApples = findGreenAnd160WeightApple(list, new GreenAnd160WeightFilter());
        System.out.println("绿色并且大于160的苹果: " + greenAnd160WeightApples);

        List<Apple> redAnd110WeightApples = findRedAnd110WeightApple(list, new RedAnd110WeightFilter());
        System.out.println("红色并且大于110的苹果: " + redAnd110WeightApples);


        // 面向接口编程
        List<Apple> apples = findApples(list, new GreenAnd160WeightFilter());
        System.out.println("绿色并且大于160的苹果: " + apples);

        List<Apple> apples1 = findApples(list, new RedAnd110WeightFilter());
        System.out.println("红色并且大于110的苹果: " + apples1);


        // 匿名内部类的方式,行为参数化
        List<Apple> yellowList = findApples(list, new AppleFilter() {
            @Override
            public boolean filter(Apple apple) {
                return "yellow".equals(apple.getColor());
            }
        });
        System.out.println("黄色的苹果: " + yellowList);


        // lambda的方式
        List<Apple> lambdaResult = findApples(list, apple -> "green".equals(apple.getColor()));
        System.out.println("绿色的苹果:" + lambdaResult);

        // Runnable 接口


        // 将list类型抽象化
        List<Apple> appFilters = filter(list, apple -> "green".equals(apple.getColor()));
        System.out.println("使用通用过滤方法过滤的结果" + appFilters);

        List<Integer> numbers = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        List<Integer> resultNumbers = filter(numbers, i -> i % 10 == 0);
        System.out.println("过滤之后的集合为: " +resultNumbers);


        /**
         *
         *  为什么会有lambda?
         *    匿名内部类的问题:
         *      1. 语法过于冗余
         *      2. 匿名类中的 this 和变量名容易使人产生误解
         *      3. 类型载入和实例创建语义不够灵活
         *      4. 无法捕获非 final 的局部变量
         *      5. 无法对控制流进行抽象
         *
         *      通过提供更简洁的语法和局部作用域规则，Java SE 8 彻底解决了问题 1 和问题 2
         *      通过提供更加灵活而且便于优化的表达式语义，Java SE 8 绕开了问题 3
         *      通过过允许编译器推断变量的“常量性”（finality），Java SE 8 减轻了问题 4 带来的困扰
         *
         *  Lambda的写法?
         *      1. 不需要参数,返回值为 5
         *          () -> 5
         *
         *      2. 接收一个参数(数字类型),返回其2倍的值
         *           x -> 2 * x
         *
         *      3. 接受2个参数(数字),并返回他们的差值
         *          (x, y) -> x – y
         *
         *      4. 接收2个int型整数,返回他们的和
         *          (int x, int y) -> x + y
         *
         *      5. 接受一个 string 对象,并在控制台打印,不返回任何值(看起来像是返回void)
         *          (String s) -> System.out.print(s)
         *
         *  Lambda探索?
         *  SAM（Single Abstract Method）
         *  1. 我们把这些只拥有一个方法的接口称为 函数式接口 @FunctionalInterface
         *
         *
         *  结构类型与指名类型?
         *  (String, Object) -> int  结构类型
         *  1. 它会为Java类型系统引入额外的复杂度，并带来结构类型（Structural Type）和指名类型（Nominal Type）的混用。（Java几乎全部使用指名类型）
         *  2. 它会导致类库风格的分歧——一些类库会继续使用回调接口，而另一些类库会使用结构化函数类型
         *  3. 它的语法会变得十分笨拙，尤其在包含受检异常（checked exception）之后
         *  4. 每个函数类型很难拥有其运行时表示，这意味着开发者会受到类型擦除（erasure）的困扰和局限。比如说，无法对方法m(T->U)和m(X->Y)进行重载（Overload）
         *
         *
         *  目标类型（Target typing）?
         *  (e)-> e.length;
         *  编译器负责推导 lambda 表达式类型。它利用 lambda 表达式所在上下文 所期待的类型 进行推导，这个 被期待的类型 被称为 目标类型
         *
         *  推导的根据:
         *      1. T 是一个函数式接口
         *      2. lambda 表达式的参数和 T 的方法参数在数量和类型上一一对应
         *      3. lambda 表达式的返回值和 T 的方法返回值相兼容（Compatible）
         *      4. lambda 表达式内所抛出的异常和 T 的方法 throws 类型相兼容
         *
         *
         * 目标类型的山下文?
         * 显示类型、隐式类型
         * 如果 lambda 表达式具有 显式类型（参数类型被显式指定），编译器就可以直接 使用lambda 表达式的返回类型；
         * 如果lambda表达式具有 隐式类型（参数类型被推导而知），重载解析则会忽略 lambda 表达式函数体而只依赖 lambda 表达式参数的数量
         *
         *
         * 语法作用域:
         * lambda 表达式不可以掩盖任何其所在上下文中的局部变量
         *  public class Hello {
         *      Runnable r1 = () -> { System.out.println(this); }
         *      Runnable r2 = () -> { System.out.println(toString()); }
         *      public String toString() {  return "Hello, world"; }
         *      public static void main(String... args) {
         *          new Hello().r1.run();
         *          new Hello().r2.run();
         *      }
         * }
         *
         * int i = 0;
         * int sum = 0;
         * for (int i = 1; i < 10; i += 1) { //这里会出现编译错误，因为i已经在for循环外部声明过了
         *   sum += i;
         * }
         *
         *
         * 变量捕获:
         *   允许在其中捕获那些符合 有效只读（Effectively final）的局部变量
         *   有效只读: 一个局部变量在初始化后从未被修改过，那么它就符合有效只读的要求.
         *   lambda表达式对值封闭，对变量开放
         *
         *
         *
         *
         *
         *
         *
         */





    }


    /**
     * 通用过滤的方法
     *
     * @param list   过滤的集合
     * @param filter 过滤条件实现
     * @param <T>    泛型 T
     * @return 过滤之后的结果
     */
    private static <T> List<T> filter(List<T> list, Predicate<T> filter) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (filter.test(t)) {
                result.add(t);
            }
        }
        return result;
    }


    /**
     * 查找绿色大于160g的苹果
     */
    private static List<Apple> findGreenAnd160WeightApple(List<Apple> apples, GreenAnd160WeightFilter greenAnd160WeightFilter) {
        List<Apple> list = new ArrayList<>();

        for (Apple apple : apples) {
            if (greenAnd160WeightFilter.filter(apple)) {
                list.add(apple);
            }
        }
        return list;
    }


    /**
     * 查找红色大于110g的苹果
     */
    private static List<Apple> findRedAnd110WeightApple(List<Apple> apples, RedAnd110WeightFilter redAnd110WeightFilter) {
        List<Apple> list = new ArrayList<>();

        for (Apple apple : apples) {
            if (redAnd110WeightFilter.filter(apple)) {
                list.add(apple);
            }
        }
        return list;
    }


    /**
     * 需要什么由传进来的参数做定义
     */
    private static List<Apple> findApples(List<Apple> apples, AppleFilter appleFilter) {
        List<Apple> list = new ArrayList<>();

        for (Apple apple : apples) {
            if (appleFilter.filter(apple)) {
                list.add(apple);
            }
        }
        return list;
    }


    /**
     * 查询绿色苹果
     */
    private static List<Apple> findGreenApple(List<Apple> apples) {
        List<Apple> list = new ArrayList<>();
        for (Apple apple : apples) {
            if ("green".equals(apple.getColor())) {
                list.add(apple);
            }
        }
        return list;
    }

    /**
     * 通过颜色筛选苹果
     */
    private static List<Apple> findAppleByColor(List<Apple> apples, String color) {
        List<Apple> list = new ArrayList<>();
        for (Apple apple : apples) {
            if (color.equals(apple.getColor())) {
                list.add(apple);
            }
        }
        return list;
    }


}
