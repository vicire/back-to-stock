package com.example.util;

import com.example.model.Product;
import com.example.model.User;
import java.util.Comparator;

public class ComparatorStrategy {

    public Comparator<User> getComparatorByProduct(Product product) {
        return new CustomComparator().getSortedStrategy().get(product.getCategory());
    }
}
