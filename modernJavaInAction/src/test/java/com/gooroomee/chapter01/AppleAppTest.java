package com.gooroomee.chapter01;

import com.gooroomee.chapter01.study.Apple;
import com.gooroomee.chapter01.study.AppleException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class AppleAppTest {

    Apple appleA;
    Apple appleB;
    Apple appleC;
    Apple appleD;
    Apple appleE;

    List<Apple> apples;

    @Before
    public void before() {
        appleA = new Apple(180, Apple.Color.BLUE);
        appleB = new Apple(160, Apple.Color.GREEN);
        appleC = new Apple(140, Apple.Color.RED);
        appleD = new Apple(120, Apple.Color.GREEN);
        appleE = new Apple(100, Apple.Color.RED);
        apples = List.of(appleA, appleB, appleC, appleD, appleE);
    }

    @Test
    public void testPredicate() {
        Apple getGreenApple = AppleApp.filterApple(apples, appleA::isGreenApple)
                                      .orElseThrow(AppleException.NoAppleException::thereAreNoApplesOfColor);
        Assert.assertTrue(getGreenApple.colorOf(Apple.Color.GREEN));

        Optional<Apple> apple = AppleApp.filterApple(apples, (Apple a) -> Apple.Color.RED == a.getColor());
        Assert.assertTrue(apple.isEmpty());
    }

    @Test
    public void testStream() {
        int targetWeight = 150;
        List<Apple> list = apples.stream()
                                 .filter((Apple a) -> a.getWeight() > targetWeight)
                                 .toList();
        list.forEach(apple -> Assert.assertTrue(apple.isWeightGreaterThan(targetWeight)));

        List<Apple> parallelList = apples.parallelStream()
                                  .filter(a -> a.getWeight() > targetWeight)
//                                  .sorted(Comparator.comparing(Apple::getWeight))
                                  .toList();
        parallelList.forEach(apple -> Assert.assertTrue(apple.isWeightGreaterThan(targetWeight)));
    }
}