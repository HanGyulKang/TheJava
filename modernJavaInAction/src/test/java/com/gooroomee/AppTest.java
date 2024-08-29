package com.gooroomee;

import com.gooroomee.chapter10.study.MethodChainingOrderBuilder;
import com.gooroomee.domain.*;
import org.junit.Before;
import org.openjdk.jmh.annotations.State;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AppTest {
    protected Apple appleA;
    protected Apple appleB;
    protected Apple appleC;
    protected Apple appleD;
    protected Apple appleE;
    protected Apple appleF;
    protected Apple appleG;
    protected Apple appleH;
    protected Apple appleI;

    protected List<Apple> apples;

    protected Dish dishA;
    protected Dish dishB;
    protected Dish dishC;
    protected Dish dishD;
    protected Dish dishE;
    protected Dish dishF;
    protected Dish dishG;

    protected List<Dish> menu;

    protected Trader raoul;
    protected Trader mario;
    protected Trader alan;
    protected Trader brian;

    protected List<Transaction> transactions;

    protected Order order = new Order();
    protected Trade trade1 = new Trade();
    protected Trade trade2 = new Trade();
    protected Stock stock1 = new Stock();
    protected Stock stock2 = new Stock();

    @Before
    public void before() {
        appleA = new Apple(180, Apple.Color.BLUE, new Date(), Apple.Country.KOREA);
        appleB = new Apple(160, Apple.Color.GREEN, new Date(), Apple.Country.JAPAN);
        appleC = new Apple(140, Apple.Color.RED, new Date(), Apple.Country.USA);
        appleD = new Apple(120, Apple.Color.GREEN, new Date(), Apple.Country.JAPAN);
        appleE = new Apple(100, Apple.Color.RED, new Date(), Apple.Country.USA);
        appleF = new Apple(100, Apple.Color.RED, new Date(), Apple.Country.JAPAN);
        appleG = new Apple(100, Apple.Color.GREEN, new Date(), Apple.Country.JAPAN);
        appleH = new Apple(100, Apple.Color.BLUE, new Date(), Apple.Country.USA);
        appleI = new Apple(100, Apple.Color.RED, new Date(), Apple.Country.JAPAN);

        apples = List.of(appleA, appleB, appleC, appleD, appleE, appleF, appleG, appleH, appleI);

        dishA = new Dish("쌀밥", true, Dish.Type.OTHER, 1280);
        dishB = new Dish("된장국", false, Dish.Type.OTHER, 1400);
        dishC = new Dish("샐러드", true, Dish.Type.VEGETABLE, 1310);
        dishD = new Dish("햄버거", false, Dish.Type.MEAT, 2200);
        dishE = new Dish("요거트", false, Dish.Type.OTHER, 800);
        dishF = new Dish("콜라", false, Dish.Type.DRINK, 600);
        dishG = new Dish("연어스테이크", false, Dish.Type.FISH, 1700);

        menu = List.of(dishA, dishB, dishC, dishD, dishE, dishF, dishG);

        raoul = new Trader("Raoul", "Cambridge");
        mario = new Trader("Mario", "Milan");
        alan = new Trader("Alan", "Cambridge");
        brian = new Trader("Brian", "Cambridge");

        transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );


        // 매우 장황하다.
//        order.setCustomer("BigBank");
//        trade1.setType(Trade.Type.BUY);
//        stock1.setSymbol("IBM");
//        stock1.setMarket("NYSE");
//        trade1.setStock(stock1);
//        trade1.setPrice(125.00);
//        trade1.setQuantity(80);
//
//        order.addTrade(trade1);
//
//        trade2.setType(Trade.Type.BUY);
//        stock2.setSymbol("GOOGLE");
//        stock2.setMarket("NYSE");
//        trade2.setStock(stock2);
//        trade2.setPrice(375.00);
//        trade2.setQuantity(50);
//
//        order.addTrade(trade2);

        // DSL을 이용한다면?
        order = MethodChainingOrderBuilder.forCustomer("BigBank")
                                          .buy(80)
                                          .stock("IBM")
                                          .on("NYSE")
                                          .at(125.00)
                                          .sell(50)
                                          .stock("GOOGLE")
                                          .on("NASDAQ")
                                          .at(375.00)
                                          .end();
    }
}
