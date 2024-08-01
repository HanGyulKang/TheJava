package com.gooroomee.chapter03;

import com.gooroomee.AppTest;
import com.gooroomee.chapter02.Chapter02App;
import com.gooroomee.chapter03.study.BufferedReaderProcessor;
import com.gooroomee.chapter03.study.TriFunction;
import com.gooroomee.domain.Apple;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.*;
import java.util.logging.Logger;

public class Chapter03AppTest extends AppTest {

    private String runTest;
    private final String FILE_PATH = "./src/test/resources/data.txt";
    Logger log = Logger.getLogger(Chapter03AppTest.class.getName());

    private void process(Runnable r) {
        log.info("start Runnable");
        r.run();
    }

    @Test
    public void lambdaRunnableTest() {
        process(() -> runTest = "run");
        Assert.assertEquals("run", runTest);
    }

    @Test
    public void functionalInterfaceTest() {
        String read = readFile(BufferedReader::readLine);
        Assert.assertEquals("data test 1", read);

        String s = readFile((BufferedReader br) -> br.readLine() + br.readLine());
        Assert.assertEquals("data test 1data test 2", s);

        /*
          람다에서 지역 변수에 바로 접근할 수 있다는 가정이 생기면
          람다가 스레드에서 실행된다면 변수를 할당한 스레드가 사라졌을 때 람다를 실행하는 스레드는 몰루? 상태가 된다.
          인스턴스 변수의 경우 스레드들이 공유하는 힙에 존재함으로 제약이 딱히 없다.
         */
        int port = 1337; // OR final int port = 1337;
        Runnable r = () -> System.out.println(port);
//        port = 6125; // 람다표현식에서 사용되는 변수의 경우 값을 변경할 수 없다. final 변수처럼 다루던지 final을 명시해주는 것이 좋다.

    }

    private String readFile(BufferedReaderProcessor p) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            return p.process(br);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    public void methodReference() {
        String nameA = "test1";
        String nameB = "Test2";

        boolean filterA = this.filter(nameA, this::isValidName);
        boolean filterB = this.filter(nameB, this::isValidName);
        Assert.assertFalse(filterA);
        Assert.assertTrue(filterB);
    }

    private boolean isValidName(String str) {
        return Character.isUpperCase(str.charAt(0));
    }

    private boolean filter(String str, Predicate<String> p) {
        return p.test(str);
    }

    @Test
    public void constructorReference() {
        Supplier<Apple> appleSupplier = Apple::new;
        Apple apple = appleSupplier.get();
        Assert.assertEquals(Apple.Color.RED, apple.getColor());

        Apple apple2 = appleSupplier.get();
        Apple apple3 = appleSupplier.get();
        Assert.assertNotEquals(apple2, apple3);

        BiFunction<Integer, Apple.Color, Apple> appleBiFunction = Apple::new;
        Apple appliedAppleA = appleBiFunction.apply(90, Apple.Color.BLUE);
        Assert.assertEquals(Apple.Color.BLUE, appliedAppleA.getColor());
        Assert.assertEquals(90, appliedAppleA.getWeight());

        BiFunction<Integer, Apple.Color, Apple> appleBiFunction2 = (weight, color) -> new Apple(weight, color);
        Apple appliedAppleB = appleBiFunction2.apply(70, Apple.Color.BLUE);

        TriFunction<Integer, Apple.Color, Date, Apple> appleTriFunction = Apple::new;
        Apple appliedAppleC = appleTriFunction.apply(70, Apple.Color.BLUE, new Date());

        System.out.println(appliedAppleB);
        System.out.println(appliedAppleC);
    }

    @Test
    public void sortingTest() {
        List<Apple> list = apples.stream()
                                 .sorted(Comparator.comparing(Apple::getWeight)
                                                   .reversed()
                                                   .thenComparing(Apple::getCountry))
                                 .toList();
        list.forEach(System.out::println);
    }

    @Test
    public void combinePredicateTest() {
        Predicate<Apple> isGreenApple = (Apple apple) -> apple.isGreenApple(apple);
        Predicate<Apple> negate = isGreenApple.negate();
        List<Apple> notGreenApple = Chapter02App.abstractFilter(apples, negate);
        notGreenApple.forEach(a -> Assert.assertTrue(a.colorWithout(Apple.Color.GREEN)));

        // combine
        Predicate<Apple> and = isGreenApple.and(apple -> apple.getWeight() > 100);
        Predicate<Apple> or = isGreenApple.or(apple -> apple.getWeight() > 140);

        List<Apple> andApples = Chapter02App.abstractFilter(apples, and);
        List<Apple> orApples = Chapter02App.abstractFilter(apples, or);

        andApples.forEach(a -> {
            Assert.assertTrue(a.colorOf(Apple.Color.GREEN));
            Assert.assertTrue(a.isWeightGreaterThan(100));
        });

        orApples.forEach(a -> Assert.assertTrue(a.colorOf(Apple.Color.GREEN) || a.isWeightGreaterThan(140)));
    }

    @Test
    public void combineFunction() {
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        // andThen으로 조합 : f 실행 -> 결과를 g로 넘김
        Function<Integer, Integer> andThenFunction = f.andThen(g);
        int andThenResult = andThenFunction.apply(7);

        Assert.assertEquals((7 + 1) * 2, andThenResult);

        // compose의 경우 인수로 주어진 g부터 실행 후에 f를 실행한다.
        Function<Integer, Integer> composeFunction = f.compose(g);
        int composeResult = composeFunction.apply(3);

        Assert.assertEquals((3 * 2) + 1, composeResult);
    }
}