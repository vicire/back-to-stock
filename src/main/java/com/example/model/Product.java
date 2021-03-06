package com.example.model;

import java.util.Objects;

public class Product {

    private final String id;
    private final ProductCategory category;

    public Product(String id, ProductCategory category) {
        this.id = id;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public ProductCategory getCategory() {
        return category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product product = (Product) obj;
        return Objects.equals(product.id, id) && Objects.equals(product.category, category);
    }

    @Override
    public String toString() {
        return "Product - {"
                + "id: " + id
                + ", category: " + category + " }";
    }
}
