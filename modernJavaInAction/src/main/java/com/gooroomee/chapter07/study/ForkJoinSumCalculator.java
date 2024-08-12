package com.gooroomee.chapter07.study;

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
        leftTask.fork();
        // 배열의 나머지 절반을 더하도록 서브테스크 생성
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);
        // 두 번 째 서브테스크를 동기 실행. 이 때 추가 분할 발생 가능
        Long rightResult = rightTask.compute();
        // 첫 번째 서브테스크의 결과를 읽거나 아직 결과가 없으면 기다림
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
