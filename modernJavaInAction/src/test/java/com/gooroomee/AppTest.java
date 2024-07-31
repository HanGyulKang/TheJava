package com.gooroomee;

import com.gooroomee.domain.Apple;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Before;

import java.util.List;

public class AppTest {
    protected Apple appleA;
    protected Apple appleB;
    protected Apple appleC;
    protected Apple appleD;
    protected Apple appleE;

    protected List<Apple> apples;

    @Before
    public void before() {
        appleA = new Apple(180, Apple.Color.BLUE);
        appleB = new Apple(160, Apple.Color.GREEN);
        appleC = new Apple(140, Apple.Color.RED);
        appleD = new Apple(120, Apple.Color.GREEN);
        appleE = new Apple(100, Apple.Color.RED);
        apples = List.of(appleA, appleB, appleC, appleD, appleE);
    }
}
