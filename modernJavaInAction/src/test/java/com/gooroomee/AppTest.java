package com.gooroomee;

import com.gooroomee.domain.Apple;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Before;

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
    }
}
