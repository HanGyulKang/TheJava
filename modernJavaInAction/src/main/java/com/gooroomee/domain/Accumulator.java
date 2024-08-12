package com.gooroomee.domain;

public class Accumulator {
    public long total = 0;

    public void add(long value) {
        total += value;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
