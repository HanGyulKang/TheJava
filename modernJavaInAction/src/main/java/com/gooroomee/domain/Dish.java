package com.gooroomee.domain;

import java.util.Objects;

public class Dish {

    public enum Type {
        MEAT, FISH, VEGETABLE, OTHER, DRINK
    }

    public enum CaloricLevel {
        DIET, NORMAL, FAT
    }

    private final String dishName;
    private final boolean isVegetable;
    private final Type type;
    private final int calories;

    public String getDishName() {
        return dishName;
    }

    public Type getType() {
        return type;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isVegetable() {
        return isVegetable;
    }

    public Dish(String dishName, boolean isVegetable, Type type, int calories) {
        this.dishName = dishName;
        this.isVegetable = isVegetable;
        this.type = type;
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
               ", isVegetable=" + isVegetable +
               ", type=" + type +
               ", calories=" + calories +
               '}';
    }
}
