package com.gooroomee.chapter07;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime) // 평균 시간 측정
@OutputTimeUnit(TimeUnit.MILLISECONDS) // 벤치마크 결과 확인 시간 값
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"}) // heap에 4G 메모리 공간 할당
public class JhmTest {

    private static final long N = 10_000_000L;

    @Benchmark
    public long sequentialSum() {
        return Stream.iterate(1L, i -> i + 1)
                     .limit(N)
                     .reduce(0L, Long::sum);
    }

    @TearDown
    public void tearDown() {
        System.gc();
    }

    @Test
    public void benchmarking() {
        sequentialSum();
    }
}
