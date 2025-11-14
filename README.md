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

- `MainFrame` - GUI main window (Swing)
- `CartTableModel` - Cart table model

**CLI** (`com.reza.orderkiosk.cli`)

- `Main` - Console version main program

**Repository** (`com.reza.orderkiosk.repo`)

- `InMemoryCatalogRepository` - Menu data (in-memory)
- `FileReceiptRepository` - Receipt storage (file-based)

## Logic Flow

1. Display menu by category
2. Add/remove items to/from cart
3. Calculate tax and display total
4. Generate and save receipt

## How to Build

```bash
mvn clean package
```

## How to Run

### GUI Version

```bash
java -cp target/orderkiosk-1.0-SNAPSHOT.jar com.reza.orderkiosk.ui.MainFrame
```

### CLI Version

```bash
java -cp target/orderkiosk-1.0-SNAPSHOT.jar com.reza.orderkiosk.cli.Main
```

## How to Run Tests

```bash
mvn test
```
