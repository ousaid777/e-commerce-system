import java.util.List;

public interface ShippingService {
    void shipItems(List<ShippableItem> items);
}

class ShippingServiceImpl implements ShippingService {
    @Override
    public void shipItems(List<ShippableItem> items) {
        if (items.isEmpty()) {
            return;
        }
        
        System.out.println("** Shipment notice **");
        
        java.util.Map<String, Integer> itemCounts = new java.util.HashMap<>();
        double totalWeight = 0;
        
        for (ShippableItem item : items) {
            String name = item.getName();
            itemCounts.put(name, itemCounts.getOrDefault(name, 0) + 1);
            totalWeight += item.getWeight();
        }
        
        for (java.util.Map.Entry<String, Integer> entry : itemCounts.entrySet()) {
            String name = entry.getKey();
            int count = entry.getValue();
            double weight = items.stream()
                               .filter(item -> item.getName().equals(name))
                               .findFirst()
                               .get()
                               .getWeight();
            System.out.println(count + "x " + name + " " + (int)(weight * 1000) + "g");
        }
        
        System.out.println("Total package weight " + String.format("%.1f", totalWeight) + "kg");
    }
} 