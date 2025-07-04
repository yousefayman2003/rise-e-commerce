package services;

import models.*;
import interfaces.Shippable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutService {
    private ShippingService shippingService;

    public CheckoutService() {
        this.shippingService = new ShippingService();
    }

    public void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Cannot checkout with empty cart");
        }

        System.out.printf("Customer balance before checkout: $%.2f%n", customer.getBalance());

        double subtotal = cart.getSubtotal();
        List<Shippable> shippableItems = getShippableItems(cart);
        double shippingCost = shippingService.calculateShippingFee(shippableItems);
        double total = subtotal + shippingCost;

        if (customer.getBalance() < total) {
            throw new IllegalStateException("Insufficient balance. Required: $" + total + ", Available: $" + customer.getBalance());
        }

        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            product.reduceQuantity(quantity);
        }

        customer.deductBalance(total);

        if (!shippableItems.isEmpty()) {
            shippingService.processShipment(shippableItems);
        }

        System.out.println("\n** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %s %.0f%n",
                item.getQuantity(),
                item.getProduct().getName(),
                item.getProduct().getPrice() * item.getQuantity());
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal %.0f%n", subtotal);
        System.out.printf("Shipping %.0f%n", shippingCost);
        System.out.printf("Amount %.0f%n", total);
        System.out.printf("Customer balance after checkout: $%.2f%n", customer.getBalance());
    }

    private List<Shippable> getShippableItems(Cart cart) {
        List<Shippable> shippableItems = new ArrayList<>();

        for (CartItem item : cart.getItems()) {
            if (item.getProduct() instanceof Shippable) {
                Shippable shippableProduct = (Shippable) item.getProduct();

                for (int i = 0; i < item.getQuantity(); i++) {
                    shippableItems.add(shippableProduct);
                }
            }
        }

        return shippableItems;
    }
}