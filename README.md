# E-Commerce System

A comprehensive Java e-commerce system with product management, shopping cart functionality, and checkout processing.

## Features

- **Product Management**: Support for different product types (Cheese, Biscuits, TV, Mobile, ScratchCard)
- **Expiration Handling**: Automatic validation for expirable products
- **Shipping Integration**: Weight-based shipping calculations for physical products
- **Shopping Cart**: Add products, validate quantities, calculate totals
- **Checkout Process**: Balance validation, inventory updates, receipt generation
- **Error Handling**: Comprehensive validation for all business scenarios

## Project Structure

```
e-commerce/
├── interfaces/
│   ├── Expirable.java      # Interface for products that can expire
│   └── Shippable.java      # Interface for products that require shipping
├── models/
│   ├── Product.java        # Abstract base class for all products
│   ├── Cheese.java         # Expirable and shippable product
│   ├── Biscuits.java       # Expirable product (non-shippable)
│   ├── TV.java             # Shippable product
│   ├── Mobile.java         # Shippable product
│   ├── ScratchCard.java    # Digital product (non-shippable, non-expirable)
│   ├── Customer.java       # Customer with balance management
│   ├── Cart.java           # Shopping cart with validation
│   └── CartItem.java       # Individual cart item
├── services/
│   ├── CheckoutService.java    # Handles checkout process
│   └── ShippingService.java    # Calculates shipping costs and processes shipments
└── Main.java              # Comprehensive demo with all use cases
```

## System Workflow
<img width="257" height="1580" alt="Image" src="https://github.com/user-attachments/assets/b2e49555-507d-4ab9-832a-250726b5db2f" />

## Design Overview
<img width="2083" height="1113" alt="Image" src="https://github.com/user-attachments/assets/06c18342-aefc-4dbe-a176-adda04bcac98" />

## Product Types

### Cheese

- Implements: `Expirable`, `Shippable`
- Features: Expiration date validation, weight-based shipping
- Example: Organic Aged Gouda Cheese

### Biscuits

- Implements: `Expirable`
- Features: Expiration date validation, non-shippable (digital/pickup)
- Example: Premium Belgian Dark Chocolate Biscuits

### TV

- Implements: `Shippable`
- Features: Weight-based shipping, no expiration
- Example: Samsung 65-inch Ultra HD 4K Smart TV

### Mobile

- Implements: `Shippable`
- Features: Weight-based shipping, no expiration
- Example: Apple iPhone 15 Pro Max

### ScratchCard

- No interfaces (digital product)
- Features: Instant delivery, no shipping or expiration
- Example: Steam $50 Digital Gaming Gift Card

## Business Rules

1. **Expirable Products**: Cannot be added to cart if expired
2. **Stock Validation**: Cannot exceed available quantity
3. **Balance Check**: Customer must have sufficient balance for total amount
4. **Shipping Calculation**: Base fee + weight-based fee for shippable items
5. **Inventory Management**: Stock quantities updated after successful checkout

## Usage

### Compile and Run

```bash
javac *.java interfaces/*.java models/*.java services/*.java
java Main
```

### Example Output

```
** Shipment notice **
2x Cheese 400g
Total package weight 1.1kg

** Checkout receipt **
2x Cheese 200
----------------------
Subtotal 350
Shipping 30
Amount 380
Customer balance after payment: $4620.00
```

## Error Handling

The system handles various error scenarios:

- Empty cart checkout attempts
- Insufficient customer balance
- Out of stock products
- Expired product additions
- Quantity overflow errors

## Demo Coverage

The Main.java file demonstrates:

1. **Successful Operations**: Multi-product checkout with all product types
2. **Error Scenarios**: All possible error conditions
3. **Edge Cases**: Zero-price products, extreme weights, single items
4. **Business Logic**: Inventory management, multiple customers, cart operations

## Technical Details

- **Java Version**: Compatible with Java 8+
- **Architecture**: Clean separation of concerns with interfaces, models, and services
- **Design Patterns**: Strategy pattern for product behaviors, Service layer for business logic
- **Error Handling**: Comprehensive exception handling with descriptive messages
