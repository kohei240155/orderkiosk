package com.reza.orderkiosk.model;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuItem {
    private final String name;
    private final BigDecimal price; // immutable

    public MenuItem(String name, BigDecimal price) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name required");
        if (price == null || price.signum() < 0) throw new IllegalArgumentException("price must be non negative");
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " (" + price + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem m)) return false;
        return Objects.equals(name, m.name) && Objects.equals(price, m.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
