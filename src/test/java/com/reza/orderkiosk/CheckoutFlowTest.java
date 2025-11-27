package com.reza.orderkiosk;

import com.reza.orderkiosk.model.*;
import com.reza.orderkiosk.repo.FileReceiptRepository;
import com.reza.orderkiosk.ui.ReceiptFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutFlowTest {

    @TempDir
    Path tempDir;

    private Cart cart;
    private FlatRateTaxCalculator taxCalc;
    private FileReceiptRepository receiptRepo;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        taxCalc = new FlatRateTaxCalculator(new BigDecimal("0.06"));
        receiptRepo = new FileReceiptRepository(tempDir);
    }

    @Test
    void testCompleteCheckoutFlow() throws IOException {
        // 1. Add items to cart
        var coffee = new MenuItem("Coffee", new BigDecimal("3.50"), Category.DRINK);
        var croissant = new MenuItem("Croissant", new BigDecimal("2.75"), Category.BAKERY);
        
        cart.add(coffee, 2);
        cart.add(croissant, 1);

        // 2. Verify cart calculations
        BigDecimal expectedSubtotal = new BigDecimal("9.75"); // 3.50*2 + 2.75
        assertEquals(0, expectedSubtotal.compareTo(cart.getSubtotal()));

        // Tax calculation: 9.75 * 0.06 = 0.585
        BigDecimal actualTax = cart.getTax(taxCalc);
        assertTrue(actualTax.compareTo(BigDecimal.ZERO) > 0);

        BigDecimal actualTotal = cart.getTotal(taxCalc);
        assertEquals(0, expectedSubtotal.add(actualTax).compareTo(actualTotal));

        // 3. Create order snapshot
        String customerName = "Alice";
        String orderId = "20251126_143000";
        Order order = new Order(
                orderId,
                customerName,
                Instant.now(),
                cart.items(),
                cart.getSubtotal(),
                cart.getTax(taxCalc),
                cart.getTotal(taxCalc)
        );

        // 4. Format receipt
        String receiptText = ReceiptFormatter.format(order);
        assertNotNull(receiptText);
        assertTrue(receiptText.contains("Cafe Receipt"));
        assertTrue(receiptText.contains("Customer: Alice"));
        assertTrue(receiptText.contains("Coffee"));
        assertTrue(receiptText.contains("Croissant"));
        assertTrue(receiptText.contains("Subtotal: 9.75"));
        assertTrue(receiptText.contains("Thank you!"));

        // 5. Save receipt to file
        List<String> receiptLines = receiptText.lines().toList();
        Path savedFile = receiptRepo.save(receiptLines);
        
        assertTrue(Files.exists(savedFile));
        assertTrue(savedFile.toString().contains("receipt_"));
        assertTrue(savedFile.toString().endsWith(".txt"));

        // 6. Verify file content
        String savedContent = Files.readString(savedFile);
        assertEquals(receiptText, savedContent);

        // 7. Simulate clearing cart after successful checkout
        cart.clear();
        assertTrue(cart.isEmpty());
        assertEquals(0, BigDecimal.ZERO.compareTo(cart.getSubtotal()));
    }

    @Test
    void testReceiptFormatterWithMultipleItems() {
        var item1 = new MenuItem("Espresso", new BigDecimal("2.50"), Category.DRINK);
        var item2 = new MenuItem("Latte", new BigDecimal("4.00"), Category.DRINK);
        var item3 = new MenuItem("Muffin", new BigDecimal("3.25"), Category.BAKERY);
        
        cart.add(item1, 1);
        cart.add(item2, 2);
        cart.add(item3, 1);

        Order order = new Order(
                "TEST001",
                "Bob",
                Instant.now(),
                cart.items(),
                cart.getSubtotal(),
                cart.getTax(taxCalc),
                cart.getTotal(taxCalc)
        );

        String receipt = ReceiptFormatter.format(order);
        
        // Verify all items are in the receipt
        assertTrue(receipt.contains("1 x Espresso @ 2.50"));
        assertTrue(receipt.contains("2 x Latte @ 4.00"));
        assertTrue(receipt.contains("1 x Muffin @ 3.25"));
        
        // Verify structure
        assertTrue(receipt.contains("Order: TEST001"));
        assertTrue(receipt.contains("Customer: Bob"));
        assertTrue(receipt.contains("------------------------------------------"));
    }

    @Test
    void testEmptyCartCannotCheckout() {
        assertTrue(cart.isEmpty());
        // In actual UI, this would show warning message
        // Here we just verify the condition
        if (!cart.isEmpty()) {
            fail("Empty cart should not proceed to checkout");
        }
    }

    @Test
    void testOrderImmutability() {
        var item = new MenuItem("Tea", new BigDecimal("2.00"), Category.DRINK);
        cart.add(item, 1);

        Order order = new Order(
                "ORDER123",
                "Charlie",
                Instant.now(),
                cart.items(),
                cart.getSubtotal(),
                cart.getTax(taxCalc),
                cart.getTotal(taxCalc)
        );

        // Modify cart after creating order
        cart.add(item, 5);
        
        // Order should remain unchanged
        assertEquals(1, order.lines().size());
        assertEquals(1, order.lines().get(0).getQuantity());
    }

    @Test
    void testReceiptRepositoryCreatesDirectory() throws IOException {
        Path newDir = tempDir.resolve("new-receipts");
        assertFalse(Files.exists(newDir));

        FileReceiptRepository repo = new FileReceiptRepository(newDir);
        repo.save(List.of("Test receipt"));

        assertTrue(Files.exists(newDir));
        assertTrue(Files.isDirectory(newDir));
    }
}
