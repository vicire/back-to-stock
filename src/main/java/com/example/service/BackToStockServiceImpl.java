package com.example.service;

import com.example.model.Product;
import com.example.model.User;
import com.example.util.ComparatorStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class BackToStockServiceImpl implements BackToStockService {
    private Map<Product, List<User>> subscribersByProduct = new HashMap<>();

    public BackToStockServiceImpl(Product...products) {
        for(Product product : products) {
            if (product == null || product.getCategory() == null || product.getId() == null) {
                throw new RuntimeException("Product or his category or his id can`t be null");
            }
            this.subscribersByProduct.put(product, new ArrayList<>());
        }
    }

    @Override
    public void subscribe(User user, Product product) {
        if (user == null || user.getName() == null) {
            throw new NoSuchElementException("There is no such element ");
        }
        if (!subscribersByProduct.containsKey(product)) {
            throw new RuntimeException("This product " + product
                    + " is not in the allowed list for subscription");
        }
        subscribersByProduct.get(product).add(user);
    }

    @Override
    public List<User> subscribedUsers(Product product) {
        if (product == null || product.getCategory() == null) {
            throw new RuntimeException("There is no such product");
        }
        if (!subscribersByProduct.containsKey(product)) {
            throw new RuntimeException("There is no users, who has subscribed on this product " + product);
        }
        return subscribersByProduct.get(product).stream()
                .sorted(new ComparatorStrategy().getComparatorByProduct(product))
                .collect(Collectors.toList());
    }

    @Override
    public void unsubscribe(User user, Product product) {
        if (!subscribersByProduct.containsKey(product)) {
            throw new RuntimeException("There is no such product " + product + " in our subscription list.");
        }
        subscribersByProduct.get(product).remove(user);
    }
}
