package com.gooroomee.chapter02;

import com.gooroomee.domain.Apple;
import com.gooroomee.domain.ApplePredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * chapter 02 : 동작 파라미터화 코드 전달하기
 * <br>
 * {@link com.gooroomee.chapter02.Chapter02AppTest}
 */
public class Chapter02App {

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

    public static <T> List<T> abstractFilter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for(T e : list) {
            if (p.test(e)) {
                result.add(e);
            }
        }

        return result;
    }

    public static <T> List<T> abstractStreamFilter(List<T> list, Predicate<T> p) {
        return list.stream().filter(p).toList();
    }
}
