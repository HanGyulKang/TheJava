package com.gooroomee.domain;

import java.util.Objects;

public class Dish {

    private final String dishName;
    private final int calories;

    public String getDishName() {
        return dishName;
    }

    public int getCalories() {
        return calories;
    }

    public Dish(String dishName, int calories) {
        this.dishName = dishName;
        this.calories = calories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return calories == dish.calories && Objects.equals(dishName, dish.dishName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishName, calories);
    }

    @Override
    public String toString() {
        return "Dish{" +
               "dishName='" + dishName + '\'' +
               ", calories=" + calories +
               '}';
    }
}
