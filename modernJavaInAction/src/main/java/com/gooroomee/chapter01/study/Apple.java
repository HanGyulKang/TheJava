package com.gooroomee.chapter01.study;

public class Apple {
    public enum Color {
        RED, GREEN, BLUE, EMPTY;
    }

    private final int weight;
    private final Color color;

    public int getWeight() {
        return weight;
    }

    public Color getColor() {
        return color;
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

    @Override
    public String toString() {
        return "Apple{" +
               "weight=" + weight +
               ", color=" + color +
               '}';
    }

    public Apple(int weight, Color color) {
        this.weight = weight;
        this.color = color;
    }
}
