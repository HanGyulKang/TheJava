package com.gooroomee.chapter09.study.strategy;

@FunctionalInterface
public interface ValidationStrategy {
    boolean execute(String s);
}
