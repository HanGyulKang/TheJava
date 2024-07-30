package com.gooroomee.chapter02.study;

import com.gooroomee.chapter01.study.Apple;
import com.gooroomee.chapter01.study.ApplePredicate;

public class AppleGreenColorPredicate implements ApplePredicate<Apple> {

    @Override
    public boolean test(Apple apple) {
        return Apple.Color.GREEN == apple.getColor();
    }
}
