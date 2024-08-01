package com.gooroomee.domain;

import java.util.Objects;

public class Trader {
    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    private final String name;
    private final String city;

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trader trader = (Trader) o;
        return Objects.equals(name, trader.name) && Objects.equals(city, trader.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city);
    }

    @Override
    public String toString() {
        return "Trader{" +
               "name='" + name + '\'' +
               ", city='" + city + '\'' +
               '}';
    }
}
