package com.gooroomee.chapter09.study.observer;

@FunctionalInterface
public interface Observer {
    void notify(String tweet);
}
