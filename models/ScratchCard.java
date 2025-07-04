package models;

public class ScratchCard extends Product {

    public ScratchCard(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    @Override
    public String toString() {
        return super.toString() + " (Digital)";
    }
}