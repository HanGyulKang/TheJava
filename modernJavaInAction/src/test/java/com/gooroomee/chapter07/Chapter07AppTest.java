package com.gooroomee.chapter07;

import com.gooroomee.AppTest;
import com.gooroomee.chapter07.study.ForkJoinSumCalculator;
import com.gooroomee.chapter07.study.WordCounter;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class Chapter07AppTest extends AppTest {

    private final static long LOOP = 21_402_104;

    @Test
    public void parallelStreamTest() {
        int limit = 100;
        long result = 5050;
        long sum = 0L;
        // stream
        sum = Stream.iterate(1L, i -> i + 1)
                    .limit(limit)
                    .reduce(sum, Long::sum);
        assertEquals(sum, result);

        // legacy
        sum = 0L;
        for (long i = 1; i <= limit; i++) {
            sum += i;
        }
        assertEquals(sum, result);

        // parallel
        sum = 0L;
        sum = Stream.iterate(1L, i -> i + 1)
                    .limit(limit)
                    .parallel()
                    .reduce(sum, Long::sum);
        assertEquals(sum, result);
    }

    @Test
    public void forkJoinSumTest() {
        long[] numbers = LongStream.rangeClosed(1, LOOP).toArray();
        ForkJoinSumCalculator task = new ForkJoinSumCalculator(numbers);
        long result = new ForkJoinPool().invoke(task);

        assertEquals(229025038514460L, result);
    }

    private final static String SENTENCE = "Nel       mezzo del cammin di nostra vita " +
                                           "mi ritrovai in una   selva oscura" +
                                           " ch  la dritta  via  era smarrita";

    @Test
    public void spliteratorTest() {
        int counter = 0;
        boolean lastSpace = true;

        for (Character c : SENTENCE.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) {
                    counter++;
                }
                lastSpace = false;
            }
        }

        assertEquals(19, counter);

        WordCounter wordCount = IntStream.rangeClosed(0, SENTENCE.length() - 1)
                                         .mapToObj(SENTENCE::charAt)
                                         .reduce(new WordCounter(0, true),
                                                 WordCounter::accumulate,
                                                 WordCounter::combine);
        assertEquals(19, wordCount.getCounter());
    }
}