package models;

import interfaces.Expirable;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void add(Product product, int quantity) {
        if (product instanceof Expirable) {
            Expirable expirable = (Expirable) product;
            if (expirable.isExpired()) {
                throw new IllegalArgumentException("Cannot add expired product: " + product.getName());
            }
        }

        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock. Available: " + product.getQuantity() + ", Requested: " + quantity);
        }

        CartItem existingItem = findItem(product);
        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + quantity;
            if (newQuantity > product.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock. Available: " + product.getQuantity() + ", Total requested: " + newQuantity);
            }
            existingItem.setQuantity(newQuantity);
        } else {
            items.add(new CartItem(product, quantity));
        }
    }

    private CartItem findItem(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().equals(product)) {
                return item;
            }
        }
        return null;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getSubtotal() {
        double subtotal = 0;
        for (CartItem item : items) {
            subtotal += item.getProduct().getPrice() * item.getQuantity();
        }
        return subtotal;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cart Contents:\n");
        for (CartItem item : items) {
            sb.append(String.format("  %dx %s\n", item.getQuantity(), item.getProduct().getName()));
        }
        sb.append(String.format("Subtotal: $%.2f", getSubtotal()));
        return sb.toString();
    }
}