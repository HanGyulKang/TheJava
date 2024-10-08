package com.gooroomee.chapter05;

import com.gooroomee.AppTest;
import com.gooroomee.domain.Dish;
import com.gooroomee.domain.Trader;
import com.gooroomee.domain.Transaction;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class Chapter05AppTest extends AppTest {

    @Test
    public void streamFilteringTest() {
        List<Dish> list = menu.stream()
                              .filter(Dish::isVegetable)
                              .distinct()
                              .toList();
        list.forEach(m -> assertTrue(m.isVegetable()));
    }

    @Test
    public void streamSlicingTest() {
        List<Dish> takeWile = menu.stream()
                                  .sorted(Comparator.comparing(Dish::getCalories))
                                  // Predicate가 false를 반환하면 탐색 중단
                                  .takeWhile(m -> m.getCalories() <= 800)
                                  .toList();

        List<Dish> dropWile = menu.stream()
                                  .sorted(Comparator.comparing(Dish::getCalories))
                                  // takeWile과 정반대로 작동, Predicate가 false를 반환한 이후부터 수집
                                  .dropWhile(m -> m.getCalories() <= 800)
                                  .toList();
        takeWile.forEach(m -> assertTrue(m.getCalories() <= 800));
        dropWile.forEach(m -> assertTrue(m.getCalories() > 800));
    }

    @Test
    public void streamSmallingTest() {
        // limit
        List<Dish> limitList = menu.stream()
                                   .filter(d -> d.getCalories() > 800)
                                   .limit(3)
                                   .toList();
        limitList.forEach(m -> assertTrue(m.getCalories() > 800));

        // skip
        List<Dish> skipList = menu.stream()
                                  .filter(d -> Dish.Type.OTHER == d.getType())
                                  .skip(1)
                                  .toList();
    }

    @Test
    public void streamFlatTest() {
        String[] strArray = {"GoodByd", "World"};
        Stream<String> words = Arrays.stream(strArray);
        List<String> list = words.map(word -> word.split(""))
                                 .flatMap(Arrays::stream)
                                 .distinct()
                                 .toList();
        System.out.println(list);
    }

    @Test
    public void streamReducingTest() {
        final int answer = 55;

        // reduce가 되기 까지...
        final List<Integer> integerList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int sum = 0;
        for (int x : integerList) {
            sum += x;
        }

        assertEquals(answer, sum);

        sum = 0;
        // 이렇게 : reduce의 인수는 두 개
        int reduce1 = integerList.stream().reduce(sum, (a, b) -> a + b);
        assertEquals(answer, reduce1);

        // Method reference
        int reduce2 = integerList.stream().reduce(sum, Integer::sum);
        assertEquals(answer, reduce2);

        // 초기값(sum)은 변하지 않음

        int max = integerList.stream().reduce(Integer::max).get();
        int min = integerList.stream().reduce(Integer::min).get();
        assertEquals(1, min);
        assertEquals(10, max);

        // map reduce pattern
        int menuCount1 = menu.stream()
                             .map(d -> 1)
                             .reduce(0, Integer::sum);
        long menuCount2 = menu.stream().count();
        assertEquals(menuCount1, menuCount2);
    }

    @Test
    public void quiz() {
        // 1
        System.out.println("1 =======");
        List<Transaction> answer01 = transactions.stream()
                                                 .filter(t -> t.getYear() == 2011)
                                                 .sorted(Comparator.comparing(Transaction::getValue))
                                                 .toList();
        answer01.forEach(System.out::println);

        // 2
        System.out.println("2 =======");
        Set<String> answer02 = transactions.stream()
                                           .map(t -> t.getTrader().getCity())
                                           .collect(Collectors.toSet());
        answer02.forEach(System.out::println);

        // 3
        System.out.println("3 =======");
        List<Trader> answer03 = transactions.stream()
                                            .map(Transaction::getTrader)
                                            .filter(trader -> "Cambridge".equals(trader.getCity()))
                                            .distinct()
                                            .sorted(Comparator.comparing(Trader::getName))
                                            .toList();
        answer03.forEach(System.out::println);

        // 4
        System.out.println("4 =======");
        String strAnswer04 = transactions.stream()
                                         .map(t -> t.getTrader().getName())
                                         .distinct()
                                         .sorted()
                                         .reduce("", (n1, n2) -> n1 + n2); // 이건 좀 억지같다 해답이
        System.out.println(strAnswer04);

        List<String> answer04 = transactions.stream()
                                            .map(t -> t.getTrader().getName())
                                            .distinct()
                                            .sorted()
                                            .toList();
        answer04.forEach(System.out::println);

        // 5
        System.out.println("5 =======");
        boolean answer05 = transactions.stream()
                                       .map(Transaction::getTrader)
                                       .anyMatch(t -> t.getCity().equals("Milan"));
        assertTrue(answer05);
        System.out.println(answer05);

        // 6
        System.out.println("6 =======");
        List<Transaction> answer06 = transactions.stream()
                                                 .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
                                                 .toList();
        answer06.forEach(System.out::println);

        // 7
        System.out.println("7 =======");
        int max = transactions.stream()
                              .mapToInt(Transaction::getValue)
                              .max()
                              .orElse(0);
        assertEquals(1000, max);
        System.out.println(max);


        // 8
        System.out.println("8 =======");
        int min = transactions.stream()
                              .mapToInt(Transaction::getValue)
                              .min()
                              .getAsInt();
        assertEquals(300, min);
        System.out.println(min);
    }
}