package com.reza.orderkiosk.interfaces;

import java.math.BigDecimal;

public interface TaxCalculator {
    BigDecimal tax(BigDecimal amount);
}
