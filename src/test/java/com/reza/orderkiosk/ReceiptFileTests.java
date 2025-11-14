package com.reza.orderkiosk;

import com.reza.orderkiosk.model.*;
import com.reza.orderkiosk.repo.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import java.math.BigDecimal;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

class ReceiptFileTests {
    @TempDir
    Path temp;

    @Test
    void writes_receipt_file() throws Exception {
        var cart = new Cart();
        cart.add(new MenuItem("Coffee", new BigDecimal("3.00"), Category.COFFEE), 2);
        var svc = new ReceiptService(new BigDecimal("0.00")::add); // dummy tax
        var saver = new ReceiptSaver(svc, new FileReceiptRepository(temp));
        Path p = saver.renderAndSave(cart);
        assertTrue(Files.exists(p));
        assertTrue(Files.size(p) > 0);
    }
}
