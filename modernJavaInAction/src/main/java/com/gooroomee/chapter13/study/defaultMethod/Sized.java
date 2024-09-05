package com.gooroomee.chapter13.study.defaultMethod;

public interface Sized {
    // 반드시 구현해야한다.
    int size();

    // 해당 interface를 구현한 클래스들이 반드시 구현할 필요가 없다.
    // 구현된 내용까지 전체를 상속받게 된다.
    // 즉, API를 사용하던 곳에서 에러가 발생하지 않는다.
    default boolean isEmpty() {
        return size() == 0;
    }
    default int doubleIt(int size) {
        return size * 2;
    }
}
