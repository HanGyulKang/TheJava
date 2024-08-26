package com.gooroomee.chapter09.study.templateMethod;

import com.gooroomee.domain.Customer;
import com.gooroomee.domain.Database;

import java.util.function.Consumer;

public abstract class OnlineBanking {

    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }
}
