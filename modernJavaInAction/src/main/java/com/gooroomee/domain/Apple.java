package com.gooroomee.domain;

import java.util.Date;

public class Apple {
    public enum Color {
        RED, GREEN, BLUE, EMPTY
    }

    public enum Country {
        KOREA, JAPAN, USA
    }

    private final int weight;
    private final Color color;
    private final Country country;
    private final Date birth;

    public int getWeight() {
        return weight;
    }

    public Color getColor() {
        return color;
    }

    public Country getCountry() {
        return country;
    }

    public boolean isHeavyApple(Apple apple) {
        return apple.getWeight() > 150;
    }

    public boolean isGreenApple(Apple apple) {
        return apple.getColor() == Color.GREEN;
    }

    public boolean colorOf(Color color) {
        return this.color == color;
    }

    public boolean isWeightGreaterThan(int weight) {
        return this.weight > weight;
    }

    public Apple(int weight, Color color, Date date, Country country) {
        this.weight = weight;
        this.color = color;
        this.birth = date;
        this.country = country;
    }

    public Apple(int weight, Color color, Date date) {
        this.weight = weight;
        this.color = color;
        this.birth = date;
        this.country = Country.KOREA;
    }

    public Apple(int weight, Color color) {
        this.weight = weight;
        this.color = color;
        this.birth = new Date();
        this.country = Country.KOREA;
    }

    public Apple() {
        this.weight = 10;
        this.color = Color.RED;
        this.birth = new Date();
        this.country = Country.KOREA;
    }

    @Override
    public String toString() {
        return "Apple{" +
               "weight=" + weight +
               ", color=" + color +
               ", country=" + country +
               ", birth=" + birth +
               '}';
    }
}
