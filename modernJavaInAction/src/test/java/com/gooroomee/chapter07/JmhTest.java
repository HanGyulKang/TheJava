package com.gooroomee.chapter07;

import com.gooroomee.domain.Accumulator;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@State(Scope.Benchmark)
@Measurement(iterations = 10, // 테스트 반복 횟수
             time = 10,  // 반복 당 테스트 실행 시간
             timeUnit = TimeUnit.MILLISECONDS // 반복 당 테스트 실행 시간 단위
             )
@Warmup(iterations = 10, time = 10, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime) // 평균 시간 측정
@OutputTimeUnit(TimeUnit.MILLISECONDS) // 벤치마크 결과 확인 시간 값
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"}) // heap에 4G 메모리 공간 할당
public class JmhTest {

    @TearDown
    public void tearDown() {
        System.gc();
    }

    /**
     * 55.294 ±(99.9%) 0.779 ms/op [Average]
     * ## 테스트 평균 수행 시간, 99.9%확률로 오차 값 0.779 범위 내에서 테스트 코드가 실행 됨
     * <p>
     * (min, avg, max) = (54.267, 55.294, 58.260), stdev = 0.897
     * ## 최소, 평균, 최대 값 / 표준편차 0.897
     * <p>
     * CI (99.9%): [54.516, 56.073] (assumes normal distribution)
     * ## 신뢰 구간 99.9%, 평균 수행 시간 54.516 ~ 56.073
     */
    private static final long N = 10_000_000L;

    @Benchmark
    public long legacySum() {
        // Benchmark          Mode  Cnt  Score   Error  Units
        // JmhTest.legacySum  avgt   20  0.408 ± 0.004  ms/op
        long result = 0;

        for (int i = 0; i < N; i++) {
            result += 1;
        }

        return result;
    }

    @Benchmark
    public long sequentialSum() {
        // Benchmark              Mode  Cnt  Score    Error  Units
        // JmhTest.sequentialSum  avgt   20  56.886 ± 2.829  ms/op
        return Stream.iterate(1L, i -> i + 1)
                     .limit(N)
                     .reduce(0L, Long::sum);
    }

    @Benchmark
    public long parallelSum() {
        // Benchmark            Mode  Cnt  Score    Error  Units
        // JmhTest.parallelSum  avgt   20  56.238 ± 2.439  ms/op
        return Stream.iterate(1L, i -> i + 1)
                     .parallel()
                     .limit(N)
                     .reduce(0L, Long::sum);

        // reducing 연산을 실행하는 시점에 전체 숫자 리스트가 존재하지 않기 때문에
        // 청크 단위로 분할할 수 없다 -> 병렬 처리가 불가능하다.
        // 각각 합계가 다른 스레드에서 수행은 되었지만, 순차처리 방식과 다르지 않기 때문에
        // 스레드를 할당하는 오버헤드만 증가한 꼴
    }
    // 1. 박싱, 언박싱을 하지 않도록 개선
    // 2. 청크로 불할할 수 있는 숫자 범위 생성

    @Benchmark
    public long rangedSum() {
        // Benchmark          Mode  Cnt  Score   Error  Units
        // JmhTest.rangedSum  avgt   20  3.415 ± 0.070  ms/op
        return LongStream.rangeClosed(1, N)
                         .reduce(0L, Long::sum);
    }
    // + 병렬처리
    // 최종 진화

    /**
     * <p>어떤 알고리즘을 선택하냐 보다 어떤 자료구조를 선택하냐가 더 중요할 때가 있다!</p>
     * {@code java.util.stream.LongStream.rangeClosed()}
     * <br>
     * 기본형인 <b>long을 직접 사용</b>해서 boxing, unboxing 오버헤드가 사라진다.
     * <br>
     * 청크로 분할할 수 있는 <b>숫자 범위를 생산</b>한다 -> 병렬 처리가 가능해진다.
     * <br><br>
     * <p>
     * <h4>우리는 언제 병렬처리를 해야하나?</h4>
     * 1. 각 서브스트림을 각각 스레드의 리듀싱 연산으로 할당 -> 각 스레드의 연산 결과를 다시 하나로 합침
     * <br>
     * 2. 멀티코어간의 데이터 이동은 비용이 매우 비쌈
     * <br>
     * 따라서 코어간의 데이터 전송 시간보다 훨씬 오래결리는 경우에만 병렬처리를 하는 것이 좋음
     * </p>
     */
    @Benchmark
    public long rangedParallelSum() {
        // Benchmark                  Mode  Cnt  Score   Error  Units
        // JmhTest.rangedParallelSum  avgt   20  0.623 ± 0.190  ms/op
        return LongStream.rangeClosed(1, N)
                         .parallel()
                         .reduce(0L, Long::sum);
    }

    @Test
    public void sideEffectSum() {
        final int n = 100;
        final int result = 5050;
        for (int i = 0; i < 5; i++) {
            Accumulator accumulator = new Accumulator();
            LongStream.rangeClosed(1, n)
                      .parallel() // 동시에 여러 스레드에서 total값에 접근하기 때문에 목표한 값이 나오지 않는다.
                      .forEach(accumulator::add);
            System.out.println("accumulator : " + accumulator.total);

            AtomicLong value = new AtomicLong(0L); // Atomic 객체를 사용하면 레이스 컨디션 방어는 된다.
            LongStream.rangeClosed(1, n)
                      .parallel()
                      .forEach(value::addAndGet);
            assertEquals(result, value.get());
        }
    }
}
