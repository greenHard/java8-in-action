package com.zhang.specific.singleton;

import java.io.Serializable;

/**
 * 序列化单例
 */
public class SerSingleton implements Serializable {
    private volatile static SerSingleton uniqueInstance;

    private  String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    private SerSingleton() {
    }

    public static SerSingleton getSerInstance() {
        if (uniqueInstance == null) {
            synchronized (SerSingleton.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new SerSingleton();
                }
            }
        }
        return uniqueInstance;
    }



}
