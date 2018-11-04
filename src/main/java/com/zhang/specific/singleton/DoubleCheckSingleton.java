package com.zhang.specific.singleton;

/**
 * 双重检查锁单例模式
 * @author yuyang.zhang
 */
public class DoubleCheckSingleton {
    private volatile static DoubleCheckSingleton uniqueInstance;

    private DoubleCheckSingleton() {
    }

    public static DoubleCheckSingleton getSingletonInstance() {
        if (uniqueInstance == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (uniqueInstance == null) {//进入区域后，再检查一次，如果仍是null,才创建实例
                    uniqueInstance = new DoubleCheckSingleton();
                }
            }
        }
        return uniqueInstance;
    }
}
