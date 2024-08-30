package com.gooroomee.chapter10.study;

import com.gooroomee.domain.Trade;

import java.util.function.Consumer;

public class LambdaTradeBuilder {
    public Trade trade = new Trade();

    public void quantity(int quantity) {
        trade.setQuantity(quantity);
    }

    public void price(double price) {
        trade.setPrice(price);
    }

    public void stock(Consumer<LambdaStockBuilder> consumer) {
        LambdaStockBuilder stockBuilder = new LambdaStockBuilder();
        consumer.accept(stockBuilder);
        trade.setStock(stockBuilder.stock);
    }
}
