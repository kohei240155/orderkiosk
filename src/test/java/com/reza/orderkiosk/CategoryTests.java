package com.reza.orderkiosk;

import com.reza.orderkiosk.model.*;
import com.reza.orderkiosk.repo.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTests {
  @Test
  void filters_by_category_counts() {
    var repo = new InMemoryCatalogRepository();
    assertEquals(2, repo.byCategory(Category.DRINK).size());
    assertEquals(1, repo.byCategory(Category.BAKERY).size());
  }
}
