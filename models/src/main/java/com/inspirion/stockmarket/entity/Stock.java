package com.inspirion.stockmarket.entity;

import java.time.LocalTime;
import java.util.Random;

public class Stock {
    private int id;
    private String name;
    private double basePrice;
    private double margin;
    private long interval;
    private long volume;

    public Stock() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public Stock(int id, String name, double basePrice, double margin, long interval, long volume) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
        this.margin = margin;
        this.interval = interval;
        this.volume = volume;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getMargin() {
        return margin;
    }

    public long getInterval() {
        return interval;
    }

    public long getVolume() {
        return volume;
    }

    public void varyPrice() {
        double[] margin = {0 - this.margin, 0, this.margin};
        Random r = new Random();
        int v = r.nextInt(margin.length);
        basePrice += margin[v];
        System.out.println(LocalTime.now() + " " + name + " " + basePrice);
    }
}
