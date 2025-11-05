package com.reza.orderkiosk.interfaces;

import com.reza.orderkiosk.model.MenuItem;
import java.util.List;

public interface CatalogRepository {
    List<MenuItem> all();
}
