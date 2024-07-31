package com.gooroomee.chapter03;

import com.gooroomee.App;
import com.gooroomee.AppTest;
import com.gooroomee.chapter02.Chapter02App;
import com.gooroomee.chapter03.study.BufferedReaderProcessor;
import com.gooroomee.chapter03.study.TriFunction;
import com.gooroomee.domain.Apple;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class Chapter03AppTest extends AppTest {

    private String runTest;
    private final String FILE_PATH = "./src/test/java/com/gooroomee/chapter03/data.txt";
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
}