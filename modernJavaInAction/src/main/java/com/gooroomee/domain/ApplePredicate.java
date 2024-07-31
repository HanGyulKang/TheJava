package com.gooroomee.domain;

@FunctionalInterface
public interface ApplePredicate<Apple> {
    boolean test(Apple apple);
}
