package com.gooroomee.chapter01;

import com.gooroomee.chapter01.study.Apple;
import com.gooroomee.chapter01.study.ApplePredicate;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class App {

    // 입문 강좌
    static Optional<Apple> filterApple(List<Apple> apples, Predicate<Apple> p) {
        return apples.stream().filter(p).findFirst();
    }
}
