package com.gooroomee.chapter01;

import com.gooroomee.domain.Apple;
import com.gooroomee.domain.AppleException;
import com.gooroomee.chapter02.study.AppleHeavyWeightPredicate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class Chapter01AppTest {

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
        Apple getGreenApple = Chapter01App.filterApple(apples, appleA::isGreenApple)
                                          .orElseThrow(AppleException.NoAppleException::thereAreNoApplesOfColor);
        Assert.assertTrue(getGreenApple.colorOf(Apple.Color.GREEN));

        Optional<Apple> apple = Chapter01App.filterApple(apples, (Apple a) -> Apple.Color.EMPTY == a.getColor());
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

    @Test
    public void applePredicateTest() {
        AppleHeavyWeightPredicate appleHeavyWeightPredicate = new AppleHeavyWeightPredicate();
        List<Apple> list = apples.stream()
                                 .filter(appleHeavyWeightPredicate::test)
                                 .toList();
        list.forEach(apple -> Assert.assertTrue(apple.isWeightGreaterThan(150)));
    }
}