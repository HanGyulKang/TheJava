package com.gooroomee.chapter02;

import com.gooroomee.chapter01.study.Apple;
import com.gooroomee.chapter02.study.AppleGreenColorPredicate;
import com.gooroomee.chapter02.study.AppleHeavyWeightPredicate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class AppTest {

    Apple appleA;
    Apple appleB;
    Apple appleC;
    Apple appleD;
    Apple appleE;

    List<Apple> apples;

    @Before
    public void before() {
        appleA = new Apple(180, Apple.Color.BLUE);
        appleB = new Apple(160, Apple.Color.GREEN);
        appleC = new Apple(140, Apple.Color.RED);
        appleD = new Apple(120, Apple.Color.GREEN);
        appleE = new Apple(100, Apple.Color.RED);
        apples = List.of(appleA, appleB, appleC, appleD, appleE);
    }

    @Test
    public void applePredicateTest() {
        List<Apple> greenApples = App.filterApples(apples, new AppleGreenColorPredicate());
        List<Apple> heavyApples = App.filterApples(apples, new AppleHeavyWeightPredicate());
        Assert.assertEquals(2, greenApples.size());
        Assert.assertEquals(2, heavyApples.size());
    }

    @Test
    public void appleLambdaTest() {
        List<Apple> redApples = App.filterApples(apples, a -> Apple.Color.RED == a.getColor());
        redApples.forEach(a -> Assert.assertEquals(Apple.Color.RED, a.getColor()));
    }

    @Test
    public void appleAbstractFilterTest() {
        List<Apple> redApples = App.abstractFilter(apples, a -> Apple.Color.RED == a.getColor());
        redApples.forEach(a -> Assert.assertEquals(Apple.Color.RED, a.getColor()));
    }

    @Test
    public void appleStreamFilterTest() {
        List<Apple> redApples = App.abstractStreamFilter(apples, a -> Apple.Color.RED == a.getColor());
        redApples.forEach(a -> Assert.assertEquals(Apple.Color.RED, a.getColor()));
    }

    @Test
    public void appleSortingTest() {
        List<Apple> list = apples.stream().sorted(Comparator.comparing(Apple::getWeight)).toList();
        for (int i = 0; i < list.size() - 1; i++) {
            Assert.assertTrue(list.get(i).getWeight() <= list.get(i + 1).getWeight());
        }
    }

    @Test
    public void runnableTest() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("run t1");
            }
        });
        t1.start();

        // lambda
        Thread t2 = new Thread(() -> System.out.println("run t2"));
        t2.start();
    }

    @Test
    public void CallableTest() {
        ExecutorService ex = Executors.newCachedThreadPool();
        Future<String> submit1 = ex.submit(new Callable<String>() {
            @Override
            public String call() {
                return Thread.currentThread().getName();
            }
        });

        try {
            String s = submit1.get();
            Assert.assertNotNull(s);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        // lambda
        Future<String> submit2 = ex.submit(() -> Thread.currentThread().getName());
        try {
            String s = submit2.get();
            Assert.assertNotNull(s);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}