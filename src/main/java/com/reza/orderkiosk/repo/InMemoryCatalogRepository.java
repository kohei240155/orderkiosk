package com.reza.orderkiosk.repo;

import com.reza.orderkiosk.interfaces.CatalogRepository;
import com.reza.orderkiosk.model.*;
import java.math.BigDecimal;
import java.util.List;

public class InMemoryCatalogRepository implements CatalogRepository {
    private final List<MenuItem> data = List.of(
        new MenuItem("Americano", new BigDecimal("3.50"), Category.DRINK),
        new MenuItem("Latte", new BigDecimal("4.20"), Category.DRINK),
        new MenuItem("Croissant", new BigDecimal("4.25"), Category.BAKERY),
        new MenuItem("Chips", new BigDecimal("2.50"), Category.SNACK),
        new MenuItem("Cookies", new BigDecimal("3.00"), Category.SNACK),
        new MenuItem("Granola Bar", new BigDecimal("2.00"), Category.SNACK)
    );

    @Override
    public List<MenuItem> all() {
        return data;
    }

    @Override
    public List<MenuItem> byCategory(Category c) {
        return data.stream().filter(mi -> mi.getCategory() == c).toList();
    }
}
