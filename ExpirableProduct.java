import java.time.LocalDate;

public class ExpirableProduct extends Product {
    private LocalDate expiryDate;
    private double weight;
    private boolean requiresShipping;
    
    public ExpirableProduct(String name, double price, int quantity, LocalDate expiryDate, double weight, boolean requiresShipping) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
        this.weight = weight;
        this.requiresShipping = requiresShipping;
    }
    
    @Override
    public boolean isExpirable() {
        return true;
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
        return LocalDate.now().isAfter(expiryDate);
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
} 