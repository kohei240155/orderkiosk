package com.reza.orderkiosk.model;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuItem {
    private final String name;
    private final BigDecimal price;
    private final Category category;

    public MenuItem(String name, BigDecimal price, Category category) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name required");
        if (price == null || price.signum() < 0) throw new IllegalArgumentException("price must be non negative");
        if (category == null) throw new IllegalArgumentException("category required");
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return name + " (" + category + ", " + price + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem m)) return false;
        return Objects.equals(name, m.name) && Objects.equals(price, m.price) && category == m.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, category);
    }
}
