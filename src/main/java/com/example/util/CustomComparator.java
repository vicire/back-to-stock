package com.example.util;

import com.example.model.ProductCategory;
import com.example.model.User;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class CustomComparator {
    private Map<ProductCategory, Comparator<User>> sortedStrategy = new HashMap<>();

    private void addDefaultStrategyByProduct() {
        sortedStrategy.put(ProductCategory.MEDICAL, new MedicalComparator());
        sortedStrategy.put(ProductCategory.BOOKS, new GeneralComparator());
        sortedStrategy.put(ProductCategory.DIGITAL, new GeneralComparator());
    }

    public void addComparatorByProductType(ProductCategory productCategory, Comparator<User> comparator) {
        sortedStrategy.put(productCategory, comparator);
    }

    public Map<ProductCategory, Comparator<User>> getSortedStrategy() {
        if (sortedStrategy.isEmpty()) {
            addDefaultStrategyByProduct();
        }
        return sortedStrategy;
    }
}
