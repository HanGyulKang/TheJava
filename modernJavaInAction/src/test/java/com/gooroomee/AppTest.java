package com.gooroomee;

import com.gooroomee.domain.Apple;
import com.gooroomee.domain.Dish;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppTest {
    protected Apple appleA;
    protected Apple appleB;
    protected Apple appleC;
    protected Apple appleD;
    protected Apple appleE;
    protected Apple appleF;
    protected Apple appleG;
    protected Apple appleH;
    protected Apple appleI;

    protected List<Apple> apples;

    protected Dish dishA;
    protected Dish dishB;
    protected Dish dishC;
    protected Dish dishD;
    protected Dish dishE;
    protected Dish dishF;

    protected List<Dish> dishes;

    @Before
    public void before() {
        appleA = new Apple(180, Apple.Color.BLUE, new Date(), Apple.Country.KOREA);
        appleB = new Apple(160, Apple.Color.GREEN, new Date(), Apple.Country.JAPAN);
        appleC = new Apple(140, Apple.Color.RED, new Date(), Apple.Country.USA);
        appleD = new Apple(120, Apple.Color.GREEN, new Date(), Apple.Country.JAPAN);
        appleE = new Apple(100, Apple.Color.RED, new Date(), Apple.Country.USA);
        appleF = new Apple(100, Apple.Color.RED, new Date(), Apple.Country.JAPAN);
        appleG = new Apple(100, Apple.Color.GREEN, new Date(), Apple.Country.JAPAN);
        appleH = new Apple(100, Apple.Color.BLUE, new Date(), Apple.Country.USA);
        appleI = new Apple(100, Apple.Color.RED, new Date(), Apple.Country.JAPAN);

        apples = List.of(appleA, appleB, appleC, appleD, appleE, appleF, appleG, appleH, appleI);

        dishA = new Dish("쌀밥", 1280);
        dishB = new Dish("된장국", 1400);
        dishC = new Dish("샐러드", 1310);
        dishD = new Dish("햄버거", 2200);
        dishE = new Dish("요거트", 800);
        dishF = new Dish("콜라", 600);

        dishes = List.of(dishA, dishB, dishC, dishD, dishE, dishF);
    }
}
