package com.gooroomee.chapter10.study;

import com.gooroomee.domain.Order;
import com.gooroomee.domain.Trade;

import java.util.function.Consumer;

public class LambdaOrderBuilder {

    private Order order = new Order();

    public static Order order(Consumer<LambdaOrderBuilder> consumer) {
        LambdaOrderBuilder lambdaOrderBuilder = new LambdaOrderBuilder();
        consumer.accept(lambdaOrderBuilder);
        return lambdaOrderBuilder.order;
    }

    public void forCustomer(String customer) {
        order.setCustomer(customer);
    }

    public void buy(Consumer<LambdaTradeBuilder> consumer) {
        trade(consumer, Trade.Type.BUY);
    }

    public void sell(Consumer<LambdaTradeBuilder> consumer) {
        trade(consumer, Trade.Type.SELL);
    }

    public void trade(Consumer<LambdaTradeBuilder> consumer, Trade.Type type) {
        LambdaTradeBuilder builder = new LambdaTradeBuilder();
        builder.trade.setType(type);
        consumer.accept(builder);
        order.addTrade(builder.trade);
    }
}
