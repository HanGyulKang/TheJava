package com.gooroomee.chapter13.study.classes;

import com.gooroomee.chapter13.study.defaultMethod.Sized;

public class Sizer implements Sized {

    private final Triangle triangle;

    public Sizer(Triangle triangle) {
        this.triangle = triangle;
    }

    @Override
    public int size() {
        return this.triangle.getSize();
    }
}
