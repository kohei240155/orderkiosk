# Order Kiosk

## Project Overview

An order kiosk application that allows users to browse a menu, add items to cart, and generate receipts with tax calculation.
Includes both CLI and GUI versions.

## Main Classes

**Model** (`com.reza.orderkiosk.model`)

- `MenuItem` - Menu item with name, price, and category
- `Category` - Categories (drinks, food)
- `Cart` - Shopping cart with add/remove items and subtotal calculation
- `Order` - Order with cart, tax calculation, and total
- `ReceiptService` - Receipt generation and saving
- `FlatRateTaxCalculator` - Tax calculation (6%)

**UI** (`com.reza.orderkiosk.ui`)

- `MainFrame` - GUI main window (Swing) with complete checkout flow
- `CartTableModel` - Cart table model
- `ReceiptFormatter` - Formats Order snapshot as readable receipt text
- `ReceiptDialog` - Modal dialog displaying formatted receipt

**CLI** (`com.reza.orderkiosk.cli`)

- `Main` - Console version main program

**Repository** (`com.reza.orderkiosk.repo`)

- `InMemoryCatalogRepository` - Menu data (in-memory)
- `FileReceiptRepository` - Receipt storage (file-based)

## Logic Flow

1. Display menu by category (Drinks, Bakery)
2. Add/remove items to/from cart with quantity selection
3. Calculate tax (6%) and display subtotal, tax, and total
4. **Checkout flow:**
   - Prompt for customer name
   - Create immutable Order snapshot
   - Format and save receipt to file (`~/kiosk-receipts/`)
   - Display receipt in modal dialog
   - Clear cart after successful checkout

## How to Build

```bash
mvn clean package
```

## How to Run

### GUI Version (Recommended)

```bash
java -cp target/classes com.reza.orderkiosk.cli.Main
```

This launches the GUI where you can:

- Browse items by category (Drinks/Bakery)
- Add items to cart with quantity
- View cart with totals (subtotal, tax, total)
- Complete checkout with customer name
- View and save receipts

Receipts are saved to: `~/kiosk-receipts/`

### CLI Version

```bash
java -cp target/orderkiosk-1.0-SNAPSHOT.jar com.reza.orderkiosk.cli.Main
```

## How to Run Tests

```bash
mvn test
```
