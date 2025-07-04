package services;

import interfaces.Shippable;
import java.util.List;

public class ShippingService {
    private static final double SHIPPING_RATE_PER_GRAM = 0.01;
    private static final double BASE_SHIPPING_FEE = 10.0;

    public void processShipment(List<Shippable> shippableItems) {
        if (shippableItems.isEmpty()) {
            return;
        }

        System.out.println("** Shipment notice **");

        java.util.Map<String, Integer> itemCounts = new java.util.HashMap<>();
        java.util.Map<String, Double> itemWeights = new java.util.HashMap<>();

        for (Shippable item : shippableItems) {
            String name = item.getName();
            itemCounts.put(name, itemCounts.getOrDefault(name, 0) + 1);
            itemWeights.put(name, item.getWeight());
        }

        double totalWeight = 0;

        for (String itemName : itemCounts.keySet()) {
            int quantity = itemCounts.get(itemName);
            double itemWeight = itemWeights.get(itemName);
            totalWeight += itemWeight * quantity;

            String weightDisplay;
            if (itemWeight >= 1000) {
                weightDisplay = String.format("%.1fkg", itemWeight / 1000);
            } else {
                weightDisplay = String.format("%.0fg", itemWeight);
            }

            System.out.printf("%dx %-12s %s%n", quantity, itemName, weightDisplay);
        }

        String totalWeightDisplay;
        if (totalWeight >= 1000) {
            totalWeightDisplay = String.format("%.1fkg", totalWeight / 1000);
        } else {
            totalWeightDisplay = String.format("%.0fg", totalWeight);
        }

        System.out.printf("Total package weight %s%n", totalWeightDisplay);
    }

    public double calculateShippingFee(List<Shippable> shippableItems) {
        if (shippableItems.isEmpty()) {
            return 0;
        }

        double totalWeight = shippableItems.stream()
                                          .mapToDouble(Shippable::getWeight)
                                          .sum();

        return BASE_SHIPPING_FEE + (totalWeight * SHIPPING_RATE_PER_GRAM);
    }
}