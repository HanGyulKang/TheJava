package com.gooroomee.chapter04.quiz;

import com.gooroomee.domain.Dish;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Quiz {
    private static Dish dishA = new Dish("쌀밥", true, Dish.Type.OTHER, 1280);
    private static Dish dishB = new Dish("된장국", false, Dish.Type.OTHER, 1400);
    private static Dish dishC = new Dish("샐러드", true, Dish.Type.VEGETABLE, 1310);
    private static Dish dishD = new Dish("햄버거", false, Dish.Type.MEAT, 2200);
    private static Dish dishE = new Dish("요거트", false, Dish.Type.OTHER, 800);
    private static Dish dishF = new Dish("콜라", false, Dish.Type.DRINK, 600);
    private static Dish dishG = new Dish("연어스테이크", false, Dish.Type.FISH, 1700);

    private static List<Dish> menu = List.of(dishA, dishB, dishC, dishD, dishE, dishF, dishG);

    public static List<String> quiz() {
        List<String> highCaloricDished = new ArrayList<>();
        Iterator<Dish> iterator = menu.iterator();
        while (iterator.hasNext()) {
            Dish dish = iterator.next();
            if (dish.getCalories() > 300) {
                highCaloricDished.add(dish.getDishName());
            }
        }

        return highCaloricDished;
    }

    // stream 활용
    public static List<String> answer() {
        return menu.stream()
                   .filter(d -> d.getCalories() > 300)
                   .map(Dish::getDishName)
                   .toList();
    }

    public static void main(String[] args) {
        List<String> quiz = quiz();
        List<String> answer = answer();
        if (answer.equals(quiz)) {
            System.out.println("pass");
        }
    }
}
