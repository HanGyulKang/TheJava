package com.gooroomee.chapter09.study.strategy;

public class IsTweet implements ValidationStrategy {
    @Override
    public boolean execute(String s) {
        return s != null && s.startsWith("tweet@");
    }
}
