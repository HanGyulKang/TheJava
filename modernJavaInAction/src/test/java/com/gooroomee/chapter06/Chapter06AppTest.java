package com.gooroomee.chapter06;

import com.gooroomee.AppTest;
import com.gooroomee.chapter06.study.PrimeNumberCollector;
import com.gooroomee.chapter06.study.ToListCollector;
import com.gooroomee.domain.Dish;
import com.gooroomee.domain.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Chapter06AppTest extends AppTest {

    Map<String, List<String>> dishTags;

    @Before
    public void setUp() {
        dishTags = new HashMap<>();
        dishTags.put("햄버거", List.of("greasy", "salty"));
        dishTags.put("샐러드", List.of("salty", "roasted"));
        dishTags.put("요거트", List.of("fried", "crisp"));
        dishTags.put("된장국", List.of("greasy", "fried"));
        dishTags.put("쌀밥", List.of("light", "natural"));
        dishTags.put("연어스테이크", List.of("natural", "salty"));
        dishTags.put("콜라", List.of("delicious", "unhealthy"));
    }

    /**
     * [STREAM의 구조]
     * <p>
     * <b>중간연산자</b>({@code filter}, {@code map} ...), <b>최종연산자</b>({@code count}, {@code collect}, {@code findFirst},
     * {@code forEach}, {@code reduce}...)
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

    @Test
    public void groupingTest() {
        Map<Dish.Type, List<Dish>> groupByType = menu.stream().collect(Collectors.groupingBy(Dish::getType));
        System.out.println(groupByType);

        Map<Dish.CaloricLevel, List<Dish>> groupByCaloricLevel = menu.stream().collect(Collectors.groupingBy(d -> {
            if (d.getCalories() <= 400) return Dish.CaloricLevel.DIET;
            else if (d.getCalories() <= 700) return Dish.CaloricLevel.NORMAL;
            else return Dish.CaloricLevel.FAT;
        }));
        System.out.println(groupByCaloricLevel);

        // group by 안ㄴ에 filter 조건을 넣어줌으로서 필터에 걸리지 않은 key값(아래 예제에서는 type)이 사라지는 경우를 방지한다.
        Predicate<Dish> dishPredicate = dish -> dish.getCalories() > 1000;
        Map<Dish.Type, List<Dish>> collectA = menu.stream()
                                                  .collect(Collectors.groupingBy(
                                                          Dish::getType,
                                                          Collectors.filtering(dishPredicate, Collectors.toList()))
                                                  );
        assertEquals(5, collectA.keySet().size());

        // mapping을 해서 필요한 값만 특정 Collection으로 집합시킬 수 있다.
        Map<Dish.Type, Set<String>> collectB = menu.stream()
                                                   .collect(Collectors.groupingBy(
                                                           Dish::getType,
                                                           Collectors.mapping(Dish::getDishName, Collectors.toSet()))
                                                   );
        System.out.println(collectB);

        Map<Dish.Type, Set<String>> collectC = menu.stream()
                                                   .collect(Collectors.groupingBy(Dish::getType,
                                                                                  Collectors.flatMapping(
                                                                                          dish -> dishTags.get(dish.getDishName()).stream(),
                                                                                          Collectors.toSet()))
                                                   );
        System.out.println(collectC);
    }

    @Test
    public void streamGroupingTest2() {
        Map<Dish.Type, Long> counting = menu.stream()
                                            .collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));
        assertEquals(counting.get(Dish.Type.FISH).intValue(), 1);
        assertEquals(counting.get(Dish.Type.OTHER).intValue(), 3);
        assertEquals(counting.get(Dish.Type.DRINK).intValue(), 1);
        assertEquals(counting.get(Dish.Type.MEAT).intValue(), 1);
        assertEquals(counting.get(Dish.Type.VEGETABLE).intValue(), 1);

        // 오 이건 좋다 : 타입별 가장 많은 칼로리를 가진 Dish
        Map<Dish.Type, Optional<Dish>> maxBy = menu.stream()
                                                   .collect(Collectors.groupingBy(Dish::getType,
                                                                                  Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))));
        System.out.println(maxBy);

        // Optional 걷어내기
        Map<Dish.Type, Dish> noOptional = menu.stream()
                                              .collect(Collectors.groupingBy(Dish::getType,
                                                                             Collectors.collectingAndThen(
                                                                                     Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)),
                                                                                     Optional::get
                                                                             )));
        System.out.println(noOptional);

        // 타입별 총 칼로리 합 그룹핑
        Map<Dish.Type, Integer> sumCaloriesByType = menu.stream()
                                                        .collect(Collectors.groupingBy(Dish::getType,
                                                                                       Collectors.summingInt(Dish::getCalories)));
        System.out.println(sumCaloriesByType);
    }

    @Test
    public void partitioningTest() {
        // 채식과 채식이 아닌 것 구분
        // 반환 키값은 Boolean
        Map<Boolean, Set<String>> partitioningByVegetable = menu
                .stream()
                .collect(Collectors.partitioningBy(Dish::isVegetable,
                                                   Collectors.mapping(Dish::getDishName, Collectors.toSet())));
        Set<String> noVegetableResult = Set.of("요거트", "연어스테이크", "된장국", "햄버거", "콜라");
        Set<String> noVegetable = partitioningByVegetable.get(false);
        assertTrue(noVegetableResult.containsAll(noVegetable));

        Set<String> vegetableResult = Set.of("샐러드", "쌀밥");
        Set<String> vegetable = partitioningByVegetable.get(true);
        assertTrue(vegetableResult.containsAll(vegetable));

        Map<Boolean, Map<Dish.Type, Set<String>>> collect = menu.stream()
                                                                .collect(Collectors.partitioningBy(Dish::isVegetable,
                                                                           Collectors.groupingBy(Dish::getType,
                                                                             Collectors.mapping(Dish::getDishName, Collectors.toSet()
                                                                         ))));
        System.out.println(collect);

        Map<Boolean, Long> counsellingByIsVegetable = menu.stream()
                                          .collect(Collectors.partitioningBy(Dish::isVegetable, Collectors.counting()));
        long noVegetableCount = counsellingByIsVegetable.get(false);
        long vegetableCount = counsellingByIsVegetable.get(true);
        assertEquals(5, noVegetableCount);
        assertEquals(2, vegetableCount);
    }

    @Test
    public void customCollectorTest() {
        List<Dish> collect = menu.stream().collect(new ToListCollector<>());
        List<Dish> listA = menu.stream().collect(Collectors.toList());
        List<Dish> listB = menu.stream().toList();
        assertEquals(collect, listA);
        assertEquals(collect, listB);
        assertEquals(listA, listB);

        int max = 200;
        Map<Boolean, List<Integer>> booleanListMap = this.partitionPrimes(max);
        System.out.println(booleanListMap.get(true));
    }

    private Map<Boolean, List<Integer>> partitionPrimes(int n) {
//        return IntStream.rangeClosed(2, n).boxed()
//                .collect(Collectors.partitioningBy(this::isPrime)); // 소수와 비소수로 분할
        return IntStream.rangeClosed(2, n).boxed()
                .collect(new PrimeNumberCollector()); // 소수와 비소수로 분할
    }

    private boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot)
                .takeWhile(i -> i <= candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }
}