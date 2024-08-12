package com.gooroomee.chapter07.study;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {
    private final long[] numbers;
    private final int start;
    private final int end;
    private static final long THRESHOLD = 10_000;

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    public ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start; // 테스크가 더할 배열의 길이
        if (length <= THRESHOLD) {
            return computeSequentially(); // 기준 값보다 같거나 작으면 순차실행
        }

        // 배열의 앞 절반을 더할 서브테스크 생성
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);

        // 새로 생성한 테스크를 비동기로 실행
        // ForkJoinPool의 invoke를 여기서 사용하면 안 된다. 병렬 계산을 시작할 때만 invoke를 사용한다.
        leftTask.fork();

        // 배열의 나머지 절반을 더하도록 서브테스크 생성
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);

        // 두 번 째 서브테스크를 동기 실행. 이 때 추가 분할 발생 가능
        // fork 메서드를 호출하는 것 보다는 compute 메서드를 호출해서 스레드를 재사용시킴으로서 불필요한 오버헤드를 피하게 한다.
        Long rightResult = rightTask.compute();

        // 첫 번째 서브테스크의 결과를 읽거나 아직 결과가 없으면 기다림
        // 이 기다리는 이유로 두 서브테스크가 모두 시작된 다음에 join을 불러야한다. 안 그러면 순차 알고리즘보다 느리고 복잡해진다.
        Long leftResult = leftTask.join();
        return leftResult + rightResult;
    }

    private Long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }
}
