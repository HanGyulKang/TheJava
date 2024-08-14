package com.gooroomee.chapter08;

import com.gooroomee.AppTest;
import org.junit.Test;

import java.util.*;
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
}