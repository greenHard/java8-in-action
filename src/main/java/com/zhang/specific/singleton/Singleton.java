package com.zhang.specific.singleton;

/**
 * 饿汉式
 * @author <p>yuyang.zhang<p>
 * @date 2018-11-06 19:15
 * @since 1.0
 */
public class Singleton {

    private  static final Singleton SINGLETON = new Singleton();


    private Singleton(){}


    public static Singleton getInstance(){
        return SINGLETON;
    }


    public static void main(String[] args) {
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        System.out.println(s1 == s2);
    }

}
