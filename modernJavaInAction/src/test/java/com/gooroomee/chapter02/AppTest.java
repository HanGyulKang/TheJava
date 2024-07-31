package com.gooroomee.chapter02;

import com.gooroomee.chapter01.study.Apple;
import com.gooroomee.chapter02.study.AppleGreenColorPredicate;
import com.gooroomee.chapter02.study.AppleHeavyWeightPredicate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AppTest {

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
    public void applePredicateTest() {
        List<Apple> greenApples = App.filterApples(apples, new AppleGreenColorPredicate());
        List<Apple> heavyApples = App.filterApples(apples, new AppleHeavyWeightPredicate());
        Assert.assertEquals(2, greenApples.size());
        Assert.assertEquals(2, heavyApples.size());
    }

    @Test
    public void appleLambdaTest() {
        List<Apple> redApples = App.filterApples(apples, a -> Apple.Color.RED == a.getColor());
        redApples.forEach(a -> Assert.assertEquals(Apple.Color.RED, a.getColor()));
    }

    @Test
    public void appleAbstractFilterTest() {
        List<Apple> redApples = App.abstractFilter(apples, a -> Apple.Color.RED == a.getColor());
        redApples.forEach(a -> Assert.assertEquals(Apple.Color.RED, a.getColor()));
    }

    @Test
    public void appleStreamFilterTest() {
        List<Apple> redApples = App.abstractStreamFilter(apples, a -> Apple.Color.RED == a.getColor());
        redApples.forEach(a -> Assert.assertEquals(Apple.Color.RED, a.getColor()));
    }
}