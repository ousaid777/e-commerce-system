import java.util.List;

public class ECommerceSystem {
    private ShippingService shippingService;
    
    public ECommerceSystem() {
        this.shippingService = new ShippingServiceImpl();
    }
    
    public void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            
            if (product.getQuantity() < item.getQuantity()) {
                throw new IllegalArgumentException("Product " + product.getName() + " is out of stock");
            }
            
            if (product.isExpired()) {
                throw new IllegalArgumentException("Product " + product.getName() + " is expired");
            }
        }
        
        double subtotal = cart.getSubtotal();
        double shippingFee = calculateShippingFee(cart.getShippingWeight());
        double totalAmount = subtotal + shippingFee;
        
        if (customer.getBalance() < totalAmount) {
            throw new IllegalArgumentException("Insufficient balance. Required: " + totalAmount + 
                ", Available: " + customer.getBalance());
        }
        
        customer.deductAmount(totalAmount);
        
        for (CartItem item : cart.getItems()) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }
        
        List<ShippableItem> shippableItems = cart.getShippableItems();
        if (!shippableItems.isEmpty()) {
            shippingService.shipItems(shippableItems);
        }
        
        printReceipt(cart, subtotal, shippingFee, totalAmount, customer);
        
        cart.clear();
    }
    
    private double calculateShippingFee(double weight) {
        if (weight <= 0) {
            return 0;
        }
        return 10 + (weight * 2);
    }
    
    private void printReceipt(Cart cart, double subtotal, double shippingFee, double totalAmount, Customer customer) {
        System.out.println("** Checkout receipt **");
        
        for (CartItem item : cart.getItems()) {
            System.out.println(item.getQuantity() + "x " + item.getProduct().getName() + " " + 
                String.format("%.0f", item.getTotalPrice()));
        }
        
        System.out.println("----------------------");
        System.out.println("Subtotal " + String.format("%.0f", subtotal));
        System.out.println("Shipping " + String.format("%.0f", shippingFee));
        System.out.println("Amount " + String.format("%.0f", totalAmount));
        System.out.println("Customer balance after payment: " + String.format("%.2f", customer.getBalance()));
    }
} 