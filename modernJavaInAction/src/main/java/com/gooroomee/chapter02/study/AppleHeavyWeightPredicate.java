package com.gooroomee.chapter02.study;

import com.gooroomee.domain.Apple;
import com.gooroomee.domain.ApplePredicate;

public class AppleHeavyWeightPredicate implements ApplePredicate<Apple> {

    @Override
    public boolean test(Apple apple) {
        return apple.isWeightGreaterThan(150);
    }
}
