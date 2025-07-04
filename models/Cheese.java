package models;

import interfaces.Expirable;
import interfaces.Shippable;
import java.time.LocalDate;

public class Cheese extends Product implements Expirable, Shippable {
    private LocalDate expirationDate;
    private double weight;

    public Cheese(String name, double price, int quantity, LocalDate expirationDate, double weight) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
        this.weight = weight;
    }

    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" (Expires: %s, Weight: %.1fg)",
            expirationDate, weight);
    }
}