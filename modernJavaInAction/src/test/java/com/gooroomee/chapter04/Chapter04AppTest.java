package com.gooroomee.chapter04;

import com.gooroomee.AppTest;
import com.gooroomee.domain.Dish;
import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

public class Chapter04AppTest extends AppTest {

    @Test
    public void streamIntroductionTest() {
        // stream 의 pipeline

        List<String> list = dishes.stream()
                                  .filter(d -> d.getCalories() > 1200)
                                  .sorted(Comparator.comparing(Dish::getCalories))
                                  .map(Dish::getDishName)
                                  .toList();
        List<String> names = List.of("쌀밥", "샐러드", "된장국", "햄버거");
        Assert.assertArrayEquals(list.toArray(), names.toArray());

        // 병렬처리
        List<String> listWithParallel = dishes.stream()
                                              .filter(d -> d.getCalories() > 1200)
                                              .sorted(Comparator.comparing(Dish::getCalories))
                                              .map(Dish::getDishName)
                                              .toList();
        Assert.assertArrayEquals(listWithParallel.toArray(), names.toArray());
    }
}