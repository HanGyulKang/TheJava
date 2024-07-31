package com.gooroomee.chapter03.study;

public interface QuadFunction<T, V, U, W, R> {
    R apply(T t, V v, U u, W w);
}
