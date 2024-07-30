package com.gooroomee.chapter01;

import com.gooroomee.chapter01.study.Apple;
import com.gooroomee.chapter01.study.AppleException;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AppleAppTest extends TestCase {

    @Test
    public void testFilterApple() {
        Apple blueApple = new Apple(160, Apple.Color.BLUE);
        Apple greenApple = new Apple(160, Apple.Color.GREEN);

        List<Apple> apples = List.of(blueApple, greenApple);
        Apple getGreenApple = AppleApp.filterApple(apples, blueApple::isGreenApple)
                .orElseThrow(AppleException.NoAppleException::thereAreNoApplesOfColor);
        Assert.assertTrue(getGreenApple.colorOf(Apple.Color.GREEN));
    }
}