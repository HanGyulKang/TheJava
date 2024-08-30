package com.gooroomee.chapter11;

import java.util.Optional;

public class Laboratory {
    public static class Person {
        private Car car;

        public Car getCar() {
            return car;
        }
    }

    public static class Car {
        private Insurance insurance;

        public Insurance getInsurance() {
            return insurance;
        }
    }

    public static class Insurance {
        private String name;

        public String getName() {
            return name;
        }
    }

    public static class Person2 {
        private Optional<OptionalCar> car;

        public Optional<OptionalCar> getCar() {
            return car;
        }
    }

    public static class OptionalCar {
        private Optional<OptionalInsurance> insurance;

        public Optional<OptionalInsurance> getInsurance() {
            return insurance;
        }
    }

    public static class OptionalInsurance {
        private Optional<String> name;

        public Optional<String> getName() {
            return name;
        }
    }
}
