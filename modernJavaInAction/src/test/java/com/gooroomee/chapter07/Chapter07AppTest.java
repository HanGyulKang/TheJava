package com.gooroomee.chapter07;

import com.gooroomee.AppTest;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class Chapter07AppTest extends AppTest {

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
}