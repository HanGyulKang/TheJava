package com.gooroomee.chapter04;

import com.gooroomee.AppTest;
import com.gooroomee.domain.Dish;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

public class Chapter04AppTest extends AppTest {

    @Test
    public void streamIntroductionTest() {
        // stream : 데이터 처리/연산 지원 API
        // stream 의 pipeline
        List<String> menuNames = menu.stream()
                                     .filter(d -> d.getCalories() > 1200)
                                     .sorted(Comparator.comparing(Dish::getCalories))
                                     .map(Dish::getDishName)
                                     .toList();
        List<String> names = List.of("쌀밥", "샐러드", "된장국", "연어스테이크", "햄버거");
        Assert.assertArrayEquals(menuNames.toArray(), names.toArray());

        // 병렬처리
        List<String> menuNameWithParallel = menu.stream()
                                                .filter(d -> d.getCalories() > 1200)
                                                .sorted(Comparator.comparing(Dish::getCalories))
                                                .map(Dish::getDishName)
                                                .toList();
        Assert.assertArrayEquals(menuNameWithParallel.toArray(), names.toArray());

        // 또 다른 예제
        List<String> threeMenuNames = menu.stream()
                                          .filter(d -> d.getCalories() > 1000)
                                          .map(Dish::getDishName)
                                          .limit(3)
                                          .toList();
        Assert.assertEquals(3, threeMenuNames.size());
    }

    @Test
    public void streamConsumptionTest() {
        Stream<Dish> stream = menu.stream();
        stream.forEach(System.out::println);
        // 이미 소비한 스트림은 재소비할 수 없다. 최고다 봉균님
        Assert.assertThrows(IllegalStateException.class,
                            () -> stream.forEach(System.out::println));
    }

    @Test
    public void streamIteration() {
        // Collection : 외부 반복 -> 개발자가 직접 for문을 만듦
        ArrayList<String> names = new ArrayList<>();
        Iterator<Dish> iterator = menu.iterator();
        while (iterator.hasNext()) {
            Dish dish = iterator.next();
            names.add(dish.getDishName());
        }

        // Stream : 내부 반복
        List<String> list = menu.stream()
                                .map(Dish::getDishName)
                                .toList();
        Assert.assertArrayEquals(list.toArray(), names.toArray());
    }
}