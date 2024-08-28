package com.gooroomee.chapter09;

import com.gooroomee.AppTest;
import com.gooroomee.chapter09.study.chainOfResponsibility.HeaderTextProcessing;
import com.gooroomee.chapter09.study.chainOfResponsibility.ProcessingObject;
import com.gooroomee.chapter09.study.chainOfResponsibility.SpellCheckerProcessing;
import com.gooroomee.domain.Dish;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class Chapter09AppTest extends AppTest {
    /**
     * Lambda expression 으로 리팩토링, 테스팅, 디버깅 하기
     */

    public interface Task {
        void execute();
    }

    public static void doSomething(Runnable r) {
        r.run();
    }

    public static void doSomething(Task r) {
        r.execute();
    }

    @Test
    public void anonymousClassToLambdaRefactoringTest() {
        int a = 10;
        Runnable r1 = () -> {
//            int a = 2; // compile error.
            System.out.println(a);
        };

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                int a = 2;
                System.out.println(a);
            }
        };
        r2.run();

        // 익명 클래스를 람다로 리팩토링했을 떄 모호함...
        doSomething(new Task() {
            @Override
            public void execute() {
                System.out.println("Danger danger!!");
            }
        });
        // to lambda
        // 어떤 인자를 보내야하는지 알 수가 없음
        // doSomething(() -> System.out.println("Danger danger!!"));
        // [ RESOLVE ]
        doSomething((Task) () -> System.out.println("Danger danger!!"));
    }

    @Test
    public void methodReferenceRefactoringTest() {
        List<Dish> asIs = menu.stream()
                              .sorted(Comparator.comparing(m -> m.getCalories()))
                              .toList();
        // refactoring
        List<Dish> toBe = menu.stream()
                              .sorted(Comparator.comparing(Dish::getCalories))
                              .toList();
    }

    @Test
    public void dataProcessingRefactoringTest() {
        // as-is
        List<String> asIs = new ArrayList<>();
        for (Dish dish : menu) {
            if (dish.getCalories() > 300) {
                asIs.add(dish.getDishName());
            }
        }
        // to-be
        List<String> toBe = menu.stream()
                                .filter(dish -> dish.getCalories() > 300)
                                .map(Dish::getDishName)
                                .toList();
    }
}