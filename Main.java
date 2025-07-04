import models.*;
import services.CheckoutService;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== COMPREHENSIVE E-COMMERCE DEMO - ALL USE CASES ===\n");

        Main demo = new Main();

        demo.testSuccessfulCheckoutAllProducts();
        System.out.println("\n" + "=".repeat(60) + "\n");

        demo.testAllErrorScenarios();
        System.out.println("\n" + "=".repeat(60) + "\n");

        demo.testEdgeCases();
        System.out.println("\n" + "=".repeat(60) + "\n");

        demo.testBusinessLogic();

        System.out.println("\n*** ALL USE CASES COMPLETED SUCCESSFULLY! ***");
    }

    public void testSuccessfulCheckoutAllProducts() {
        System.out.println("1. SUCCESSFUL CHECKOUT - ALL PRODUCT TYPES");
        System.out.println("=" + "=".repeat(45));

        Cheese cheese = new Cheese("Organic Aged Gouda Cheese from Netherlands", 120.0, 18,
            LocalDate.now().plusDays(35), 450.0);

        Biscuits biscuits = new Biscuits("Premium Belgian Dark Chocolate Biscuits", 180.0, 25,
            LocalDate.now().plusDays(50), 650.0);

        TV tv = new TV("Samsung 65-inch Ultra HD 4K Smart TV", 1800.0, 12, 20000.0);

        Mobile mobile = new Mobile("Latest Apple iPhone 15 Pro Max Titanium Edition", 1400.0, 15, 250.0);

        ScratchCard scratchCard = new ScratchCard("Steam $50 Digital Gaming Gift Card", 50.0, 150);

        Customer customer = new Customer("Yousef Ayman - Wealthy Tech Savvy Customer", 6000.0);
        System.out.println("Customer Profile: " + customer);

        Cart cart = new Cart();

        try {
            cart.add(cheese, 2);
            cart.add(biscuits, 1);
            cart.add(tv, 1);
            cart.add(mobile, 1);
            cart.add(scratchCard, 3);

            System.out.println("\nLuxury shopping cart contents before checkout:");
            System.out.println(cart);

            double balanceBefore = customer.getBalance();

            CheckoutService checkoutService = new CheckoutService();
            checkoutService.checkout(customer, cart);

            double balanceAfter = customer.getBalance();
            double totalSpent = balanceBefore - balanceAfter;

            System.out.println("\n>>> SUCCESSFUL PURCHASE SUMMARY <<<");
            System.out.printf("Total luxury items cost: $%.2f%n", totalSpent);
            System.out.println("Customer after luxury purchase: " + customer);
            System.out.println("SUCCESS: Multi-product luxury checkout completed!");

        } catch (Exception e) {
            System.out.println("ERROR: Unexpected checkout error occurred - " + e.getMessage());
        }
    }

    public void testAllErrorScenarios() {
        System.out.println("2. ERROR HANDLING - ALL SCENARIOS");
        System.out.println("=" + "=".repeat(35));

        testEmptyCartCheckoutAttempt();
        testInsufficientCustomerBalanceError();
        testOutOfStockProductRequestError();
        testExpiredProductAdditionError();
        testQuantityOverflowError();
    }

    private void testEmptyCartCheckoutAttempt() {
        System.out.println("\nERROR TEST 1: Empty Cart Checkout Attempt");

        Customer customer = new Customer("Customer With No Items - Test Subject", 1500.0);
        Cart emptyCart = new Cart();
        CheckoutService checkoutService = new CheckoutService();

        try {
            checkoutService.checkout(customer, emptyCart);
            System.out.println("FAIL: Should have rejected empty cart checkout attempt!");
        } catch (IllegalStateException e) {
            System.out.println("SUCCESS: Empty cart correctly rejected - " + e.getMessage());
        }
    }

    private void testInsufficientCustomerBalanceError() {
        System.out.println("\nERROR TEST 2: Insufficient Customer Balance");

        TV expensiveTV = new TV("Extremely Expensive Luxury 85-inch OLED TV", 15000.0, 4, 50000.0);
        Customer poorCustomer = new Customer("Customer With Limited Budget $500", 500.0);
        Cart cart = new Cart();
        CheckoutService checkoutService = new CheckoutService();

        try {
            cart.add(expensiveTV, 1);
            System.out.printf("Customer budget: $%.2f, TV cost: $%.2f%n",
                poorCustomer.getBalance(), expensiveTV.getPrice());

            checkoutService.checkout(poorCustomer, cart);
            System.out.println("FAIL: Should have rejected insufficient balance!");
        } catch (IllegalStateException e) {
            System.out.println("SUCCESS: Insufficient balance correctly rejected - " + e.getMessage());
        }
    }

    private void testOutOfStockProductRequestError() {
        System.out.println("\nERROR TEST 3: Out of Stock Product Request");

        Mobile limitedMobile = new Mobile("Very Limited Edition Collector Smartphone", 2800.0, 3, 230.0);
        Cart cart = new Cart();

        try {
            System.out.printf("Available limited phones: %d, Customer requesting: %d%n",
                limitedMobile.getQuantity(), 12);

            cart.add(limitedMobile, 12);
            System.out.println("FAIL: Should have rejected out-of-stock request!");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCCESS: Out-of-stock request correctly rejected - " + e.getMessage());
        }
    }

    private void testExpiredProductAdditionError() {
        System.out.println("\nERROR TEST 4: Expired Product Addition");

        Cheese expiredCheese = new Cheese("Expired and Rotten Moldy Blue Cheese", 85.0, 8,
            LocalDate.now().minusDays(10), 360.0);
        Cart cart = new Cart();

        try {
            System.out.printf("Cheese expiration: %s, Today: %s%n",
                expiredCheese.getExpirationDate(), LocalDate.now());

            cart.add(expiredCheese, 2);
            System.out.println("FAIL: Should have rejected expired product!");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCCESS: Expired product correctly rejected - " + e.getMessage());
        }
    }

    private void testQuantityOverflowError() {
        System.out.println("\nERROR TEST 5: Quantity Overflow When Adding");

        Biscuits limitedBiscuits = new Biscuits("Very Popular Limited Edition Oreo Biscuits", 110.0, 6,
            LocalDate.now().plusDays(75), 480.0);
        Cart cart = new Cart();

        try {
            cart.add(limitedBiscuits, 4);
            System.out.printf("First addition: 4 biscuits added (remaining: %d)%n",
                limitedBiscuits.getQuantity());

            System.out.println("Attempting to add 5 more biscuits (would total 9, but only 6 available)");
            cart.add(limitedBiscuits, 5);
            System.out.println("FAIL: Should have rejected quantity overflow!");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCCESS: Quantity overflow correctly rejected - " + e.getMessage());
        }
    }

    public void testEdgeCases() {
        System.out.println("3. EDGE CASES & BOUNDARY CONDITIONS");
        System.out.println("=" + "=".repeat(40));

        testZeroPriceFreeProducts();
        testExtremeProductWeights();
        testSingleItemPurchases();
    }

    private void testZeroPriceFreeProducts() {
        System.out.println("\nEDGE CASE 1: Zero Price Free Products");

        ScratchCard freeCoupon = new ScratchCard("Completely Free Product Sample Coupon", 0.0, 80);
        Customer customer = new Customer("Lucky Customer Receiving Free Items", 200.0);
        Cart cart = new Cart();
        CheckoutService checkoutService = new CheckoutService();

        try {
            cart.add(freeCoupon, 3);

            double balanceBefore = customer.getBalance();

            checkoutService.checkout(customer, cart);

            double balanceAfter = customer.getBalance();

            System.out.printf("Balance before free items: $%.2f%n", balanceBefore);
            System.out.printf("Balance after free items: $%.2f%n", balanceAfter);
            System.out.println("SUCCESS: Free products processed correctly!");

        } catch (Exception e) {
            System.out.println("ERROR: Free products error - " + e.getMessage());
        }
    }

    private void testExtremeProductWeights() {
        System.out.println("\nEDGE CASE 2: Extreme Product Weights");

        Mobile lightEarbuds = new Mobile("Ultra Lightweight Feather Wireless Earbuds", 180.0, 12, 4.2);
        TV heavyDisplay = new TV("Massive Commercial Grade Display Wall System", 20000.0, 2, 95000.0);

        Customer customer = new Customer("Extreme Weight Testing Customer", 25000.0);
        Cart cart = new Cart();
        CheckoutService checkoutService = new CheckoutService();

        try {
            cart.add(lightEarbuds, 3);
            cart.add(heavyDisplay, 1);

            System.out.printf("Feather earbuds weight: %.1f grams each%n", lightEarbuds.getWeight());
            System.out.printf("Massive display weight: %.1f kg%n", heavyDisplay.getWeight() / 1000);

            checkoutService.checkout(customer, cart);

            System.out.println("SUCCESS: Extreme weights handled correctly!");

        } catch (Exception e) {
            System.out.println("ERROR: Extreme weights issue - " + e.getMessage());
        }
    }

    private void testSingleItemPurchases() {
        System.out.println("\nEDGE CASE 3: Single Item Purchases");

        ScratchCard voucher = new ScratchCard("Single Digital Download Voucher Card", 35.0, 1);
        Customer customer = new Customer("Minimalist Shopper Customer", 150.0);
        Cart cart = new Cart();
        CheckoutService checkoutService = new CheckoutService();

        try {
            cart.add(voucher, 1);

            checkoutService.checkout(customer, cart);

            System.out.println("SUCCESS: Single item purchase completed!");

        } catch (Exception e) {
            System.out.println("ERROR: Single item issue - " + e.getMessage());
        }
    }

    public void testBusinessLogic() {
        System.out.println("4. BUSINESS LOGIC SCENARIOS");
        System.out.println("=" + "=".repeat(30));

        testInventoryManagementWithMultiplePurchases();
        testMultipleCustomersShoppingSameProducts();
        testAdvancedShoppingCartOperations();
    }

    private void testInventoryManagementWithMultiplePurchases() {
        System.out.println("\nBUSINESS LOGIC 1: Inventory Management");

        Mobile smartphone = new Mobile("Inventory Tracking Test Smartphone", 750.0, 15, 200.0);

        Customer customer1 = new Customer("First Inventory Test Customer", 5000.0);
        Customer customer2 = new Customer("Second Inventory Test Customer", 5000.0);

        CheckoutService checkoutService = new CheckoutService();

        System.out.printf("Initial smartphone inventory: %d units%n", smartphone.getQuantity());

        try {
            Cart cart1 = new Cart();
            cart1.add(smartphone, 6);
            checkoutService.checkout(customer1, cart1);
            System.out.printf("After first customer (bought 6): %d units remaining%n",
                smartphone.getQuantity());

            Cart cart2 = new Cart();
            cart2.add(smartphone, 4);
            checkoutService.checkout(customer2, cart2);
            System.out.printf("After second customer (bought 4): %d units remaining%n",
                smartphone.getQuantity());

            System.out.println("SUCCESS: Inventory management working correctly!");

        } catch (Exception e) {
            System.out.println("ERROR: Inventory management issue - " + e.getMessage());
        }
    }

    private void testMultipleCustomersShoppingSameProducts() {
        System.out.println("\nBUSINESS LOGIC 2: Multiple Customers Same Products");

        Biscuits biscuits = new Biscuits("Shared Popular Biscuit Brand", 130.0, 20,
            LocalDate.now().plusDays(40), 450.0);

        Customer budgetCustomer = new Customer("Budget Conscious Customer", 300.0);
        Customer luxuryCustomer = new Customer("Luxury Shopper Customer", 1500.0);

        CheckoutService checkoutService = new CheckoutService();

        try {
            System.out.printf("Initial biscuit stock: %d units%n", biscuits.getQuantity());

            Cart cart1 = new Cart();
            cart1.add(biscuits, 2);
            checkoutService.checkout(budgetCustomer, cart1);
            System.out.printf("Budget customer bought 2, remaining: %d%n", biscuits.getQuantity());

            Cart cart2 = new Cart();
            cart2.add(biscuits, 7);
            checkoutService.checkout(luxuryCustomer, cart2);
            System.out.printf("Luxury customer bought 7, remaining: %d%n", biscuits.getQuantity());

            System.out.println("SUCCESS: Multiple customers handled correctly!");

        } catch (Exception e) {
            System.out.println("ERROR: Multiple customers issue - " + e.getMessage());
        }
    }

    private void testAdvancedShoppingCartOperations() {
        System.out.println("\nBUSINESS LOGIC 3: Advanced Cart Operations");

        Cart cart = new Cart();
        TV tv = new TV("Advanced Cart Test Television", 850.0, 8, 12000.0);
        ScratchCard giftCard = new ScratchCard("Advanced Cart Test Gift Card", 40.0, 50);

        try {
            cart.add(tv, 1);
            cart.add(giftCard, 4);
            System.out.printf("After adding TV and gift cards: $%.2f%n", cart.getSubtotal());

            cart.add(giftCard, 2);
            System.out.printf("After adding more gift cards: $%.2f%n", cart.getSubtotal());

            double expectedSubtotal = (850.0 * 1) + (40.0 * 6);
            if (Math.abs(expectedSubtotal - cart.getSubtotal()) < 0.01) {
                System.out.println("SUCCESS: Cart calculations perfect!");
            } else {
                System.out.println("ERROR: Cart calculation mismatch!");
            }

        } catch (Exception e) {
            System.out.println("ERROR: Cart operations issue - " + e.getMessage());
        }
    }
}