package com.zhang.specific;

import com.google.common.base.Strings;
import com.zhang.specific.singleton.DoubleCheckSingleton;
import com.zhang.specific.singleton.EnumSerSingleton;
import com.zhang.specific.singleton.EnumSingleton;
import com.zhang.specific.singleton.SerSingleton;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static com.zhang.specific.singleton.DoubleCheckSingleton.getSingletonInstance;
import static com.zhang.specific.singleton.SerSingleton.getSerInstance;
import static java.lang.System.out;

/**
 * Java 5 新特性
 *
 * @author yuyang.zhang
 */
public class Java5NewSpecific {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {
        // 1. 自动拆箱和装箱 autoboxing & unboxing
        // 装箱就是自动将基本数据类型转换为包装器类型；拆箱就是自动将包装器类型转换为基本数据类型。
        // 使用javap -c *.class 可以看到其中过程
        Integer autoBoxing = 100;
        out.println(autoBoxing.getClass());
        int unboxing = autoBoxing;
        out.println(unboxing);

        //
        //Integer i1 = Integer.valueOf(126);
        //Integer i2 = Integer.valueOf(126);
        //System.out.println(i1 == i2);
        //
        //Integer i3 = Integer.valueOf(128);
        //Integer i4 = Integer.valueOf(128);
        //System.out.println(i3 ==i4);

        // 2. 枚举
        // (1) 常量
        // (2) switch语句
        // (3) 枚举中添加新方法
        // 每一个实例就相当于一个不可改变的实力对象
        Season spring = Season.SPRING;
        out.println(spring.getName());

        // 通过枚举可以实现一个安全的单例模式
        // Joshua Bloch -- 单元素的枚举类型已经成为实现Singleton的最佳方法
        // double check
        out.println(getSingletonInstance() == getSingletonInstance());
        // 1. 反射问题
        out.println("========================doubleCheckSerSingletonProblem");
        doubleCheckReflectAttack();
        // 2. 序列化问题
        out.println("========================doubleCheckSerSingletonProblem");
        doubleCheckSerSingletonProblem();
        // 枚举的方式
        out.println("==========================enumSingletonReflectAttack");
        //enumSingletonReflectAttack();
        out.println("==================enumSingletonSerSingletonProblem");                enumSingletonSerSingletonProblem();

        // 静态导入
        out.println("not static import...");
        out.println("static import...");


        // 可变参数
        // 1. 只能出现在参数列表的最后；
        // 2. 位于变量类型和变量名之间，前后有无空格都可以；
        // 3. 调用可变参数的方法时，编译器为该可变参数隐含创建一个数组，在方法体中以数组的形式访问可变参数。
        out.println("==================checkArgs");
        out.println(checkArgs("must", "1111", ""));

        // 泛型
        // jdk 5 之前
        List arrayList = new ArrayList();
        arrayList.add("aaaa");
        arrayList.add(100);
        for(int i = 0; i< arrayList.size();i++){
            String item = (String)arrayList.get(i);
            System.out.println(item);
        }

        // jdk5之后
        List<String> arrayList2 = new ArrayList<String>();
        arrayList2.add("aaaa");
        //arrayList2.add(100);
        for(int i = 0; i< arrayList2.size();i++){
            String item = (String)arrayList.get(i);
            System.out.println(item);
        }
        // 类型擦除 泛型信息只存在于代码编译阶段，在进入 JVM 之前，与泛型相关的信息会被擦除掉，专业术语叫做类型擦除。

        // For-Each
        for (String s : arrayList2) {
            System.out.println("s: " + s);
        }

        // juc java.util.concurrent

    }


    /**
     * 测试 double check 的问题
     */
    private static void doubleCheckReflectAttack() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        DoubleCheckSingleton s1 = getSingletonInstance();
        DoubleCheckSingleton s2 = getSingletonInstance();
        Constructor<DoubleCheckSingleton> constructor = DoubleCheckSingleton.class.getDeclaredConstructor();
        // 设置之后可以拿到私有方法
        constructor.setAccessible(true);
        DoubleCheckSingleton s3 = constructor.newInstance();
        out.println("正常情况下，实例化两个实例是否相同：" + (s1 == s2));
        out.println("通过反射攻击单例模式情况下，实例化两个实例是否相同：" + (s1 == s3));
    }

    /**
     * 序列化问题测试
     */
    private static void doubleCheckSerSingletonProblem() throws IOException, ClassNotFoundException {
        SerSingleton s = getSerInstance();
        s.setContent("单例序列化");
        out.println("序列化前读取其中的内容：" + s.getContent());
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("SerSingleton.obj"));
        oos.writeObject(s);
        oos.flush();
        oos.close();

        FileInputStream fis = new FileInputStream("SerSingleton.obj");
        ObjectInputStream ois = new ObjectInputStream(fis);
        SerSingleton s1 = (SerSingleton) ois.readObject();
        ois.close();
        out.println(s + "\n" + s1);
        out.println("序列化后读取其中的内容：" + s1.getContent());
        out.println("序列化前后两个是否同一个：" + (s == s1));
    }

    /**
     * 测试 double check 的问题
     * if ((clazz.getModifiers() & Modifier.ENUM) != 0)
     * throw new IllegalArgumentException("Cannot reflectively create enum objects");
     */
    private static void enumSingletonReflectAttack() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        EnumSingleton e1 = EnumSingleton.INSTANCE;
        EnumSingleton e2 = EnumSingleton.INSTANCE;
        out.println("正常情况下,没有通过放射攻击的情况: " + (e1 == e2));
        Constructor<EnumSingleton> constructor = EnumSingleton.class.getDeclaredConstructor();
        // 设置之后可以拿到私有方法
        constructor.setAccessible(true);
        EnumSingleton e3 = constructor.newInstance();
        out.println("通过反射攻击单例模式情况下，实例化两个实例是否相同：" + (e1 == e3));
    }


    /**
     * 枚举单例序列化测试
     */
    private static void enumSingletonSerSingletonProblem() throws IOException, ClassNotFoundException {
        EnumSerSingleton es1 = EnumSerSingleton.INSTANCE;
        es1.setContent("单例序列化");
        out.println("序列化前读取其中的内容：" + es1.getContent());
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("EnumSerSingleton.obj"));
        oos.writeObject(es1);
        oos.flush();
        oos.close();

        FileInputStream fis = new FileInputStream("EnumSerSingleton.obj");
        ObjectInputStream ois = new ObjectInputStream(fis);
        EnumSerSingleton es2 = (EnumSerSingleton) ois.readObject();
        ois.close();
        out.println("序列化后读取其中的内容：" + es2.getContent());
        out.println("序列化前后两个是否同一个：" + (es1 == es2));
    }


    /**
     * 校验参数必须都不为空 返回true,否则返回false
     * return result
     */
    private static boolean checkArgs(String must,String... args) {
        if (Strings.isNullOrEmpty(must)) {
            return false;
        }

        for (String arg : args) {
            if (Strings.isNullOrEmpty(arg)) {
                return false;
            }
        }
        return true;
    }

}

