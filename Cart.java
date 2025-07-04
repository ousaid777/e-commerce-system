import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;
    
    public Cart() {
        this.items = new ArrayList<>();
    }
    
    public void add(Product product, int quantity) {
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for " + product.getName() + 
                ". Available: " + product.getQuantity() + ", Requested: " + quantity);
        }
        
        if (product.isExpired()) {
            throw new IllegalArgumentException("Product " + product.getName() + " is expired");
        }
        
        for (CartItem item : items) {
            if (item.getProduct().getName().equals(product.getName())) {
                int newQuantity = item.getQuantity() + quantity;
                if (product.getQuantity() < newQuantity) {
                    throw new IllegalArgumentException("Insufficient stock for " + product.getName() + 
                        ". Available: " + product.getQuantity() + ", Requested: " + newQuantity);
                }
                item = new CartItem(product, newQuantity);
                return;
            }
        }
        
        items.add(new CartItem(product, quantity));
    }
    
    public List<CartItem> getItems() {
        return items;
    }
    
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    public double getSubtotal() {
        return items.stream()
                   .mapToDouble(CartItem::getTotalPrice)
                   .sum();
    }
    
    public double getShippingWeight() {
        return items.stream()
                   .filter(item -> item.getProduct().requiresShipping())
                   .mapToDouble(CartItem::getTotalWeight)
                   .sum();
    }
    
    public List<ShippableItem> getShippableItems() {
        List<ShippableItem> shippableItems = new ArrayList<>();
        for (CartItem item : items) {
            if (item.getProduct().requiresShipping()) {
                for (int i = 0; i < item.getQuantity(); i++) {
                    final Product product = item.getProduct();
                    shippableItems.add(new ShippableItem() {
                        @Override
                        public String getName() {
                            return product.getName();
                        }
                        
                        @Override
                        public double getWeight() {
                            return product.getWeight();
                        }
                    });
                }
            }
        }
        return shippableItems;
    }
    
    public void clear() {
        items.clear();
    }
} 