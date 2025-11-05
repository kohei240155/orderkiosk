# Order Kiosk (Weeks 0-3)

## Project Description

This is a console-based order kiosk application. Users can browse a menu, add items to their cart, and get a receipt with tax calculation.

## Main Classes

**Model Package (`com.reza.orderkiosk.model`)**

- `MenuItem` - represents menu items (name and price)
- `Cart` - shopping cart that holds items and calculates subtotal
- `CartItem` - individual line item in the cart
- `ReceiptService` - generates formatted receipt with tax
- `FlatRateTaxCalculator` - calculates 6% tax

**CLI Package (`com.reza.orderkiosk.cli`)**

- `Main` - main program with console interface

**Interfaces (`com.reza.orderkiosk.interfaces`)**

- `TaxCalculator` - interface for tax calculation
- `CatalogRepository` - interface for menu data

**Repository (`com.reza.orderkiosk.repo`)**

- `InMemoryCatalogRepository` - stores menu items in memory

## How It Works

1. Program starts and loads menu (Coffee, Tea, Sandwich)
2. User types commands to interact with the system
3. Items are added to cart
4. Receipt is generated with subtotal, tax, and total

## How to Build

```bash
mvn clean compile
```

Or build a JAR file:

```bash
mvn clean package
```

## How to Run

```bash
java -jar target/orderkiosk-1.0-SNAPSHOT.jar
```

### Example

```
Welcome to Order Kiosk CLI
Commands, menu | add <Name> <Qty> | remove <Name> | show | receipt | quit
> menu
Coffee 3.00
Tea 2.50
Sandwich 5.00
> add Coffee 2
Added 2 x Coffee
> add Sandwich 1
Added 1 x Sandwich
> show
Coffee x2 = 6.00
Sandwich x1 = 5.00
> receipt
Item                 Qty   Price   Total
Coffee                 2    3.00    6.00
Sandwich               1    5.00    5.00
----------------------------------------
Subtotal:                      11.00
Tax:                            0.66
Total:                         11.66
> quit
Bye
```

## How to Run Tests

```bash
mvn test
```

Test results will be shown in the console and saved to `target/surefire-reports/`.

The project has two test files:

- `CartTest` - tests cart operations
- `ReceiptServiceTest` - tests receipt generation

## Notes

- Menu items are hardcoded in `InMemoryCatalogRepository` (Coffee $3.00, Tea $2.50, Sandwich $5.00)
- Tax rate is set to 6% in the Main class
- Uses JUnit 5 for testing
