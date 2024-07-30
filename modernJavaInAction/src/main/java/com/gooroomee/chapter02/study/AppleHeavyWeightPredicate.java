package com.gooroomee.chapter02.study;

import com.gooroomee.chapter01.study.Apple;
import com.gooroomee.chapter01.study.ApplePredicate;

public class AppleHeavyWeightPredicate implements ApplePredicate<Apple> {

    @Override
    public boolean test(Apple apple) {
        return apple.isWeightGreaterThan(150);
    }
}
