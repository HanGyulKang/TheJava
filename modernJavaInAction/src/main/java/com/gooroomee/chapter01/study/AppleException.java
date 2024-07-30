package com.gooroomee.chapter01.study;

public class AppleException extends RuntimeException {

    public static class NoAppleException extends AppleException {

        public static NoAppleException thereAreNoApplesOfColor() {
            return new NoAppleException("There are no apples of color");
        }

        private NoAppleException(String message) {
            super(message);
        }
    }

    private AppleException(String message) {
        super(message);
    }
}
