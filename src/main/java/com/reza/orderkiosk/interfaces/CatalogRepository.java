package com.reza.orderkiosk.interfaces;

import com.reza.orderkiosk.model.MenuItem;
import com.reza.orderkiosk.model.Category;
import java.util.List;

public interface CatalogRepository {
    List<MenuItem> all();
    List<MenuItem> byCategory(Category category);
}
