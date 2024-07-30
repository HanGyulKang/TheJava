package com.gooroomee.chapter02;

import com.gooroomee.chapter01.study.Apple;
import com.gooroomee.chapter01.study.ApplePredicate;

import java.util.ArrayList;
import java.util.List;

/**
 * chapter 02 : 동작 파라미터화 코드 전달하기
 */
public class App {

    public static List<Apple> filterApples(List<Apple> inventory,
                                           ApplePredicate<Apple> p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }

        return result;
    }
}
