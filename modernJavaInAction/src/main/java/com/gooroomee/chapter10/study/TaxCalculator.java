package com.gooroomee.chapter10.study;

import com.gooroomee.domain.Order;

import java.util.function.DoubleUnaryOperator;
import java.util.function.IntUnaryOperator;

public class TaxCalculator {
    public DoubleUnaryOperator taxFunction = d -> d;
    public IntUnaryOperator intFunction = i -> i;

    public TaxCalculator with(DoubleUnaryOperator f) {
        taxFunction = taxFunction.andThen(f);
        return this;
    }

    public TaxCalculator with(IntUnaryOperator f) {
        intFunction = intFunction.andThen(f);
        return this;
    }

    public double calculate(Double value) {
        return taxFunction.applyAsDouble(value);
    }

    public int calculate(int value) {
        return intFunction.applyAsInt(value);
    }
}
