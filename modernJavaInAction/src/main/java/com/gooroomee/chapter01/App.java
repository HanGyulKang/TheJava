package com.gooroomee.chapter01;

import com.gooroomee.chapter01.study.Apple;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * chapter 01 : 입문
 */
public class App {

    static Optional<Apple> filterApple(List<Apple> apples, Predicate<Apple> p) {
        return apples.stream().filter(p).findFirst();
    }
}
