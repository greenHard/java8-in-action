package com.zhang.specific.singleton;

/**
 * 枚举序列化单实例
 */
public enum EnumSerSingleton {
    INSTANCE;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
