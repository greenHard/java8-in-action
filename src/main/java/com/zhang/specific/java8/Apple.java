package com.zhang.specific.java8;

/**
 * 苹果
 */
public class Apple {

    private String color;

    private long weight;

    private String name;


    public Apple() {
    }

    public Apple(String color, long weight) {
        this.color = color;
        this.weight = weight;
    }

    public Apple(String color, long weight, String name) {
        this.color = color;
        this.weight = weight;
        this.name = name;
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

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "color='" + color + '\'' +
                ", weight=" + weight +
                ", name='" + name + '\'' +
                '}';
    }
}
