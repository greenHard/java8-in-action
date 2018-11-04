package com.zhang.specific;

/**
 * 季节
 * @author yuyang.zhang
 */
public enum  Season {
    // 那么必须在enum实例序列的最后添加一个分号。而且 Java 要求必须先定义 enum 实例
    SPRING("春天","green"),SUMMER("夏天","red"),AUTUMN("秋天","yellow"),WINTER("冬天","white");

    private String name;

    private String color;

    Season(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
