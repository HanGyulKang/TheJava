package com.gooroomee.chapter06;

import com.gooroomee.AppTest;
import com.gooroomee.domain.Dish;
import com.gooroomee.domain.Trader;
import com.gooroomee.domain.Transaction;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Chapter06AppTest extends AppTest {
    /**
     * [STREAM의 구조]
     * <p>
     * <b>중간연산자</b>({@code filter}, {@code map} ...), <b>최종연산자</b>({@code count}, {@code collect}, {@code findFirst}, {@code forEach}, {@code reduce}...)
     * 중간연산자는 여러번 붙을 수 있음. 그렇게 해서 만들어지는게 스트림 파이프라인
     * 모든 중간연산을 지난 이후에 최종연산은 한 번밖에 안 됨
     * <p>
     * - 중간연산자 : 연산<br>
     * - 최종연산자 : 연산 결과를 소비<br>
     * <p>
     * ** 스트림은 기본적으로 최종연산 이후에는 <b>"재소모"</b>가 <u>되지 않음</u>. 두 번 사용할 수 없음<br>
     * {@link IllegalStateException} 발생
     * </p>
     */
    @Test
    public void test() {
        List<Integer> integers = List.of(1, 2, 3, 4, 5);
        Stream<Integer> integerStream = integers.stream().filter(n -> n % 2 == 0);

        List<Integer> list = integerStream.map(n -> n * 2).toList();
        try {
            long count = integerStream.count(); // already consumed
        } catch (IllegalStateException e) {
            System.out.println(
                    """
                    이미 소비한 stream을 최종연산자로 재소비하려고 하기 때문에
                    java.lang.IllegalStateException: stream has already been operated upon or closed 발생
                    """
            );
        }
        list.forEach(n -> assertEquals(0, n % 2));
    }

    @Test
    public void streamGrouping() {
        Map<Integer, List<Transaction>> collect = transactions.stream()
                                                              .collect(Collectors.groupingBy(Transaction::getYear));
        System.out.println(collect);
    }

    @Test
    public void minAndMaxTest() {
        Comparator<Dish> comparing = Comparator.comparing(Dish::getCalories);
        Optional<Dish> maxCalorie = menu.stream()
                                        .collect(Collectors.maxBy(comparing)); // 지금은 그냥 max()을 사용
        maxCalorie.ifPresent(c -> assertEquals(2200, c.getCalories()));

        Optional<Dish> minCalorie = menu.stream()
                                        .collect(Collectors.minBy(comparing)); // 지금은 그냥 min()을 사용
        minCalorie.ifPresent(c -> assertEquals(600, c.getCalories()));
    }

    @Test
    public void summaryOperationTest() {
        int sumA = menu.stream()
                       .collect(Collectors.summingInt(Dish::getCalories));
        int sumB = menu.stream()
                       .mapToInt(Dish::getCalories)
                       .sum();
        assertEquals(9290, sumA);
        assertEquals(9290, sumB);

        Double avgA = menu.stream()
                          .collect(Collectors.averagingInt(Dish::getCalories));
        double avgB = menu.stream()
                          .mapToDouble(Dish::getCalories)
                          .average()
                          .orElse(0.0);
        assertEquals(1327.142857142857, avgA, 0.0);
        assertEquals(1327.142857142857, avgB, 0.0);

        // summarizing
        IntSummaryStatistics summary = menu.stream()
                                           .collect(Collectors.summarizingInt(Dish::getCalories));
        // IntSummaryStatistics{count=7, sum=9290, min=600, average=1327.142857, max=2200}
        assertEquals(7, summary.getCount());
        assertEquals(9290, summary.getSum());
        assertEquals(1327.142857142857, summary.getAverage(), 0.0);
        assertEquals(2200, summary.getMax());
        assertEquals(600, summary.getMin());
    }

    @Test
    public void concatStringTest() {
        String concatMenuName = menu.stream()
                                    .map(Dish::getDishName)
                                    .collect(Collectors.joining());
        StringBuilder sb = new StringBuilder();
        menu.stream()
            .map(Dish::getDishName)
            .toList()
            .forEach(sb::append);
        assertEquals(concatMenuName, sb.toString());

        // 이렇게 콤마 추가도 가능 함
        String concatMenuNameWithDelimiter = menu.stream().map(Dish::getDishName).collect(Collectors.joining(", "));
        assertEquals("쌀밥, 된장국, 샐러드, 햄버거, 요거트, 콜라, 연어스테이크", concatMenuNameWithDelimiter);
    }
}