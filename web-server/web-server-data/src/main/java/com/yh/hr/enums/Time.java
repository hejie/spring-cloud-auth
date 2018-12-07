package com.yh.hr.enums;

public enum Time {
    All(100, "所有"),
    TODAY(101, "今天"),
    OTHER(102, "其他");

    Time(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int code;
    public String name;
}