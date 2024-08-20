package com.gooroomee.chapter08;

import com.gooroomee.AppTest;
import com.gooroomee.domain.Trader;
import com.gooroomee.domain.Transaction;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class Chapter08AppTest extends AppTest {

    @Test
    public void collectionFactoryMethodTest() {
        /**
         * LIST
         */
        List<String> friendsA = new ArrayList<>();
        friendsA.add("A");
        friendsA.add("B");
        friendsA.add("C");

        // =>
        List<String> friendsB = Arrays.asList("A", "B", "C");

        // =>
        List<String> friendsC = List.of("A", "B", "C");
        // static <E> List<E> of(E e1, E e2, E e3) {
        //        return ImmutableCollections.listFromTrustedArray(e1, e2, e3);
        // }
        // of factory method가 list를 불변으로 만들기 때문에 add가 되질 않고 UnsupportedOperationException이 발생한다.
        assertThrows(UnsupportedOperationException.class, () -> friendsC.add("D"));


        assertArrayEquals(friendsA.toArray(), friendsB.toArray());
        assertArrayEquals(friendsB.toArray(), friendsC.toArray());

        /**
         * Set
         */
        Set<String> friendsSetA = new HashSet<>(friendsA);

        // =>
        Set<String> friendsSetB = Stream.of("A", "B", "C").collect(Collectors.toSet());
        assertEquals(friendsSetA, friendsSetB);

        // =>
        Set<String> friendsSetC = Set.of("A", "B", "C");
        assertEquals(friendsSetA, friendsSetC);


        /**
         * Map
         */
        Map<String, Integer> mapA = Map.of("A", 10, "B", 24, "C", 61);

        // =>
        Map<String, Integer> mapB = Map.ofEntries(Map.entry("A", 10),
                                                  Map.entry("B", 24),
                                                  Map.entry("C", 61));
        assertEquals(mapA, mapB);
    }

    @Test
    public void removeIfTest() {
        List<Trader> traders = new ArrayList<>(transactions.stream()
                                                           .map(Transaction::getTrader)
                                                           .toList());
        traders.removeIf(trader -> trader.getName().equals("Mario"));
        traders.forEach(trader -> assertNotEquals("Mario", trader.getName()));
    }

    @Test
    public void replaceAllTest() {
        List<String> test = new ArrayList<>(List.of("A", "B", "C"));
        test.replaceAll(t -> "111" + t);
        test.forEach(t -> assertTrue(t.startsWith("111")));
    }

    @Test
    public void concurrentHashMapTest() {
        // ConcurrentHashMap은 동시성 친화적이며 최신 기술을 반영한 HashMap이다.
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();

        map.put("A", 10L);
        map.put("B", 20L);
        map.put("C", 30L);
        map.put("D", 40L);

        // 병렬성 기준 값 : 맵의 크기가 주어진 기준값보다 작으면 순차적으로 연산을 실행한다.
        // 고로 1을 주면 1개보다 작은 경우가 되기 때문에 병렬성을 극대화할 수 있다.
        // 하지만 Long::max 값으로 설정하면 한 개의 스레드로 연산한다.
        long parallelismThreshold = 1;
        Optional<Long> maxValue = Optional.ofNullable(map.reduceValues(parallelismThreshold, Long::max));
        assertEquals(40L, (long) maxValue.get());

        // 계수
        map.mappingCount(); // == map.size(), int값 반환
    }
}