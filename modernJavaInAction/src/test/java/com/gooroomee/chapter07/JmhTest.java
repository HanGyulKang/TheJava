package com.gooroomee.chapter07;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@State(Scope.Benchmark)
@Measurement(iterations = 10, // 테스트 반복 횟수
             time = 100,  // 반복 당 테스트 실행 시간
             timeUnit = TimeUnit.MILLISECONDS // 반복 당 테스트 실행 시간 단위
)
@Warmup(iterations = 10, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime) // 평균 시간 측정
@OutputTimeUnit(TimeUnit.MILLISECONDS) // 벤치마크 결과 확인 시간 값
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"}) // heap에 4G 메모리 공간 할당
public class JmhTest {

    private static final long N = 10_000_000L;

    // jmh plugins 설치하고 돌려라.
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

    /**
     *   55.294 ±(99.9%) 0.779 ms/op [Average]
     *   ## 테스트 평균 수행 시간, 99.9%확률로 오차 값 0.779 범위 내에서 테스트 코드가 실행 됨
     *
     *   (min, avg, max) = (54.267, 55.294, 58.260), stdev = 0.897
     *   ## 최소, 평균, 최대 값 / 표준편차 0.897
     *
     *   CI (99.9%): [54.516, 56.073] (assumes normal distribution)
     *   ## 신뢰 구간 99.9%, 평균 수행 시간 54.516 ~ 56.073
     */


    /**
     * 테스트 결과
     *
     * # JMH version: 1.37
     * # VM version: JDK 17.0.11, OpenJDK 64-Bit Server VM, 17.0.11+9-LTS
     * # VM invoker: /Users/gooroomee/Library/Java/JavaVirtualMachines/corretto-17.0.11/Contents/Home/bin/java
     * # VM options: -Xms4G -Xmx4G
     * # Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
     * # Warmup: 10 iterations, 100 ms each
     * # Measurement: 10 iterations, 100 ms each
     * # Timeout: 10 min per iteration
     * # Threads: 1 thread, will synchronize iterations
     * # Benchmark mode: Average time, time/op
     * # Benchmark: com.gooroomee.chapter07.JhmTest.sequentialSum
     *
     * # Run progress: 0.00% complete, ETA 00:00:04
     * # Fork: 1 of 2
     * # Warmup Iteration   1: 82.988 ms/op
     * # Warmup Iteration   2: 88.501 ms/op
     * # Warmup Iteration   3: 86.903 ms/op
     * # Warmup Iteration   4: 65.258 ms/op
     * # Warmup Iteration   5: 65.998 ms/op
     * # Warmup Iteration   6: 83.854 ms/op
     * # Warmup Iteration   7: 54.641 ms/op
     * # Warmup Iteration   8: 56.355 ms/op
     * # Warmup Iteration   9: 55.339 ms/op
     * # Warmup Iteration  10: 54.083 ms/op
     * Iteration   1: 53.996 ms/op
     * Iteration   2: 55.063 ms/op
     * Iteration   3: 54.255 ms/op
     * Iteration   4: 55.127 ms/op
     * Iteration   5: 53.979 ms/op
     * Iteration   6: 54.055 ms/op
     * Iteration   7: 54.664 ms/op
     * Iteration   8: 54.182 ms/op
     * Iteration   9: 54.085 ms/op
     * Iteration  10: 55.186 ms/op
     *
     * # Run progress: 50.00% complete, ETA 00:00:02
     * # Fork: 2 of 2
     * # Warmup Iteration   1: 81.949 ms/op
     * # Warmup Iteration   2: 85.969 ms/op
     * # Warmup Iteration   3: 85.928 ms/op
     * # Warmup Iteration   4: 63.482 ms/op
     * # Warmup Iteration   5: 61.696 ms/op
     * # Warmup Iteration   6: 60.677 ms/op
     * # Warmup Iteration   7: 53.371 ms/op
     * # Warmup Iteration   8: 53.361 ms/op
     * # Warmup Iteration   9: 54.671 ms/op
     * # Warmup Iteration  10: 53.203 ms/op
     * Iteration   1: 53.500 ms/op
     * Iteration   2: 54.959 ms/op
     * Iteration   3: 53.653 ms/op
     * Iteration   4: 54.663 ms/op
     * Iteration   5: 53.348 ms/op
     * Iteration   6: 53.810 ms/op
     * Iteration   7: 54.554 ms/op
     * Iteration   8: 55.921 ms/op
     * Iteration   9: 54.564 ms/op
     * Iteration  10: 55.559 ms/op
     *
     *
     * Result "com.gooroomee.chapter07.JhmTest.sequentialSum":
     *   54.456 ±(99.9%) 0.602 ms/op [Average]
     *   (min, avg, max) = (53.348, 54.456, 55.921), stdev = 0.693
     *   CI (99.9%): [53.854, 55.058] (assumes normal distribution)
     *
     *
     * # Run complete. Total time: 00:00:05
     *
     * REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
     * why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
     * experiments, perform baseline and negative tests that provide experimental control, make sure
     * the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
     * Do not assume the numbers tell you what you want them to tell.
     *
     * NOTE: Current JVM experimentally supports Compiler Blackholes, and they are in use. Please exercise
     * extra caution when trusting the results, look into the generated code to check the benchmark still
     * works, and factor in a small probability of new VM bugs. Additionally, while comparisons between
     * different JVMs are already problematic, the performance difference caused by different Blackhole
     * modes can be very significant. Please make sure you use the consistent Blackhole mode for comparisons.
     *
     * Benchmark              Mode  Cnt   Score   Error  Units
     * JhmTest.sequentialSum  avgt   20  54.456 ± 0.602  ms/op
     */
}
