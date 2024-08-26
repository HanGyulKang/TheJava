package com.gooroomee.domain;

import java.util.Objects;

public class Customer {
    public int customerId;
    public String customerName;
    public String accountNumber;

    private Customer() {}

    public Customer(int customerId, String customerName, String accountNumber) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.accountNumber = accountNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerId == customer.customerId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(customerId);
    }
}
