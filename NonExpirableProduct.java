public class NonExpirableProduct extends Product {
    private double weight;
    private boolean requiresShipping;
    
    public NonExpirableProduct(String name, double price, int quantity, double weight, boolean requiresShipping) {
        super(name, price, quantity);
        this.weight = weight;
        this.requiresShipping = requiresShipping;
    }
    
    @Override
    public boolean isExpirable() {
        return false;
    }
    
    @Override
    public boolean requiresShipping() {
        return requiresShipping;
    }
    
    @Override
    public double getWeight() {
        return weight;
    }
    
    @Override
    public boolean isExpired() {
        return false;
    }
} 