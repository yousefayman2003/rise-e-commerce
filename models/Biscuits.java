package models;

import interfaces.Expirable;
import java.time.LocalDate;

public class Biscuits extends Product implements Expirable {
    private LocalDate expirationDate;
    private double weight;

    public Biscuits(String name, double price, int quantity, LocalDate expirationDate, double weight) {
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

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" (Expires: %s, Weight: %.1fg)",
            expirationDate, weight);
    }
}