package com.gooroomee.chapter02.study;

import com.gooroomee.domain.Apple;
import com.gooroomee.domain.ApplePredicate;

public class AppleGreenColorPredicate implements ApplePredicate<Apple> {

    @Override
    public boolean test(Apple apple) {
        return Apple.Color.GREEN == apple.getColor();
    }
}
