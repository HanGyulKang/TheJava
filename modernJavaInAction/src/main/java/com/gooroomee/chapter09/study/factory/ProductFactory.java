package com.gooroomee.chapter09.study.factory;

import com.gooroomee.chapter09.study.factory.product.Bond;
import com.gooroomee.chapter09.study.factory.product.Loan;
import com.gooroomee.chapter09.study.factory.product.Product;
import com.gooroomee.chapter09.study.factory.product.Stock;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ProductFactory {
    public static Product createProduct(ProductType productType) {
        return switch (productType) {
            case BOND -> new Bond();
            case LOAN -> new Loan();
            case STOCK -> new Stock();
        };
    }

    public static Product createProduct(ProductType productType, boolean b) {
        Map<ProductType, Supplier<? extends Product>> map = new HashMap<>();
        map.put(ProductType.BOND, () -> createProduct(productType));
        map.put(ProductType.LOAN, () -> createProduct(productType));
        map.put(ProductType.STOCK, () -> createProduct(productType));

        Supplier<? extends Product> supplier = map.get(productType);
        if (supplier != null) return supplier.get();

        throw new IllegalArgumentException("No such product type: " + productType);
    }
}
