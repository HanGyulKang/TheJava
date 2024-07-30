package com.gooroomee.chapter02.quiz;

import com.gooroomee.chapter01.study.Apple;

public class App {

    public static String appleInfo(Apple apple, ApplePrettyPrint<Apple> a) {
        return a.accept(apple);
    }

    public static void main(String[] args) {
        Apple apple = new Apple(140, Apple.Color.GREEN);
        String s = appleInfo(apple, new ApplePrettyPrinter());
        System.out.println(s);
    }
}
