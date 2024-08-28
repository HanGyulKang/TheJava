package com.gooroomee.chapter09;

import com.gooroomee.AppTest;
import com.gooroomee.domain.Dish;
import com.gooroomee.domain.Point;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Test
    public void lambdaTestingTest() {
        Point p1 = new Point(5, 5);
        Point p2 = p1.moveRightBy(10);
        assertEquals(15, p2.x());
        assertEquals(5, p2.y());

        Point p3 = new Point(10, 15);
        Point p4 = new Point(10, 20);
        int compare = Point.compareByXAndThenY.compare(p3, p4);
        assertTrue(compare < 0);
    }

    @Test
    public void debuggingTest() {
        List<Point> points = Arrays.asList(new Point(12, 2), null);
        ThrowingRunnable r2 = () -> points.stream().map(Point::x).forEach(System.out::println);
        assertThrows(NullPointerException.class, r2);
        // 아 씁 미래의 자바 컴파일러에게 람다표현식 디버깅을 맡겼다.
    }
}