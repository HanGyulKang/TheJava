package com.gooroomee.chapter03.study;

public interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}
