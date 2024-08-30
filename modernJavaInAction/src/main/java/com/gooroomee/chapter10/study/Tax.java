package com.gooroomee.chapter10.study;

public enum Tax {
    REGIONAL {
        @Override
        public double calculate(double value) {
            return value * 1.1;
        }
    },
    GENERAL {
        @Override
        public double calculate(double value) {
            return value * 1.3;
        }
    },
    SURCHARGE {
        @Override
        public double calculate(double value) {
            return value * 1.05;
        }
    };

    public abstract double calculate(double value);
}
