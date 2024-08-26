package com.gooroomee.chapter09.study.observer;

public interface Subject {
    void registerObserver(Observer observer);
    void notifyObservers(String tweet);
}
