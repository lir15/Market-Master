package com.example.marketmaster.data;

public class ChartElement {
    private String time;
    private String value;
    private String name;

    public ChartElement(String name) {
        this.name = name;
    }

    public ChartElement(String time, String value) {
        this.time = time;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public String getValue() {
        return value;
    }
}
