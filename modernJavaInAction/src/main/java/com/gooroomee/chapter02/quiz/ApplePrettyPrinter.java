package com.gooroomee.chapter02.quiz;

import com.gooroomee.chapter01.study.Apple;

public class ApplePrettyPrinter implements ApplePrettyPrint<Apple> {

    @Override
    public String accept(Apple apple) {
        int weight = apple.getWeight();
        Apple.Color color = apple.getColor();
        return "무게는? " + weight + ", 색깔은? " + color.name();
    }
}
