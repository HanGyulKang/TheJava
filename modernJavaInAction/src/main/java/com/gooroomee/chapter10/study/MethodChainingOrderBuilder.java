package com.gooroomee.chapter10.study;

import com.gooroomee.domain.Order;
import com.gooroomee.domain.Trade;

public class MethodChainingOrderBuilder {
    private MethodChainingOrderBuilder() {
    }

    public final Order order = new Order(); // builder로 감싼 주문

    private MethodChainingOrderBuilder(String customer) {
        order.setCustomer(customer);
    }

    public static MethodChainingOrderBuilder forCustomer(String customer) {
        return new MethodChainingOrderBuilder(customer);
    }

    public TradeBuilder buy(int quantity) {
        return new TradeBuilder(this, Trade.Type.BUY, quantity);
    }

    public TradeBuilder sell(int quantity) {
        return new TradeBuilder(this, Trade.Type.SELL, quantity);
    }

    protected MethodChainingOrderBuilder addTrade(Trade trade) {
        order.addTrade(trade);
        return this;
    }

    public Order end() {
        return order;
    }
}
