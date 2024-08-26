package com.gooroomee.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Database {
    public List<Customer> customers = new ArrayList<>();

    public Database() {
        Customer john = new Customer(1, "John", "1111-1111");
        Customer bbk = new Customer(2, "Bbk", "1111-1112");
        Customer kim = new Customer(3, "Kim", "1111-1113");
        Customer kang = new Customer(4, "Kang", "1111-1114");
        Customer song = new Customer(5, "Song", "1111-1115");
        Customer bong = new Customer(6, "Bong", "1111-1116");
        Customer lim = new Customer(7, "Lim", "1111-1117");

        this.customers.add(john);
        this.customers.add(bbk);
        this.customers.add(kim);
        this.customers.add(kang);
        this.customers.add(song);
        this.customers.add(bong);
        this.customers.add(lim);
    }

    public static class CustomerResponse {
        public Customer customer;
    }

    public static Customer getCustomerWithId(int id) {
        Database database = new Database();
        return database.customers.stream()
                                 .filter(c -> c.getCustomerId() == id)
                                 .findFirst()
                                 .map(c -> new Customer(c.customerId, c.customerName, c.accountNumber))
                                 .orElseThrow(IllegalArgumentException::new);
    }
}
