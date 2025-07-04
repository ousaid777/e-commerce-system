import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Interactive E-Commerce System ===\n");
        
        ECommerceSystem ecommerce = new ECommerceSystem();
        
        List<Product> availableProducts = createAvailableProducts();
        
        Customer customer = createCustomer();
        
        Cart cart = new Cart();
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            displayMenu();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    displayProducts(availableProducts);
                    break;
                case 2:
                    addProductToCart(scanner, availableProducts, cart);
                    break;
                case 3:
                    viewCart(cart);
                    break;
                case 4:
                    checkout(ecommerce, customer, cart);
                    break;
                case 5:
                    System.out.println("Thank you for shopping with us!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }
    
    private static List<Product> createAvailableProducts() {
        List<Product> products = new ArrayList<>();
        
        products.add(new ExpirableProduct("Cheese", 100, 10, LocalDate.now().plusDays(30), 0.2, true));
        products.add(new ExpirableProduct("Biscuits", 150, 5, LocalDate.now().plusDays(15), 0.7, true));
        products.add(new ExpirableProduct("Milk", 80, 8, LocalDate.now().plusDays(7), 1.0, true));
        products.add(new ExpirableProduct("Bread", 60, 12, LocalDate.now().plusDays(5), 0.5, true));
        
        products.add(new NonExpirableProduct("TV", 500, 3, 15.0, true));
        products.add(new NonExpirableProduct("Mobile", 800, 8, 0.3, true));
        products.add(new NonExpirableProduct("Laptop", 1200, 4, 2.5, true));
        products.add(new NonExpirableProduct("Scratch Card", 50, 20, 0.0, false));
        products.add(new NonExpirableProduct("Gift Card", 100, 15, 0.0, false));
        
        return products;
    }
    
    private static Customer createCustomer() {
        Customer customer = new Customer("Customer", 2000);
        System.out.println("Welcome! Your balance is $" + String.format("%.2f", customer.getBalance()));
        System.out.println();
        
        return customer;
    }
    
    private static void displayMenu() {
        System.out.println("=== Shopping Menu ===");
        System.out.println("1. View Available Products");
        System.out.println("2. Add Product to Cart");
        System.out.println("3. View Cart");
        System.out.println("4. Checkout");
        System.out.println("5. Exit");
        System.out.println("=====================");
    }
    
    private static void displayProducts(List<Product> products) {
        System.out.println("\n=== Available Products ===");
        System.out.printf("%-3s %-15s %-8s %-8s %-10s %-8s%n", "ID", "Name", "Price", "Stock", "Type", "Shipping");
        System.out.println("------------------------------------------------");
        
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            String type = product.isExpirable() ? "Expirable" : "Non-Expirable";
            String shipping = product.requiresShipping() ? "Yes" : "No";
            
            System.out.printf("%-3d %-15s $%-7.0f %-8d %-10s %-8s%n", 
                i + 1, product.getName(), product.getPrice(), product.getQuantity(), type, shipping);
        }
        System.out.println();
    }
    
    private static void addProductToCart(Scanner scanner, List<Product> products, Cart cart) {
        displayProducts(products);
        
        System.out.print("Enter product ID to add: ");
        int productId = scanner.nextInt();
        
        if (productId < 1 || productId > products.size()) {
            System.out.println("Invalid product ID!");
            return;
        }
        
        Product selectedProduct = products.get(productId - 1);
        
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0!");
            return;
        }
        
        try {
            cart.add(selectedProduct, quantity);
            System.out.println("Successfully added " + quantity + "x " + selectedProduct.getName() + " to cart!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void viewCart(Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        
        System.out.println("\n=== Your Cart ===");
        System.out.printf("%-15s %-8s %-8s %-8s%n", "Product", "Price", "Qty", "Total");
        System.out.println("----------------------------------------");
        
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            System.out.printf("%-15s $%-7.0f %-8d $%-7.0f%n", 
                product.getName(), product.getPrice(), item.getQuantity(), item.getTotalPrice());
        }
        
        System.out.println("----------------------------------------");
        System.out.printf("Subtotal: $%.0f%n", cart.getSubtotal());
        System.out.printf("Shipping Weight: %.1f kg%n", cart.getShippingWeight());
        System.out.println();
    }
    
    private static void checkout(ECommerceSystem ecommerce, Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Please add some products first.");
            return;
        }
        
        try {
            System.out.println("Processing checkout...");
            ecommerce.checkout(customer, cart);
            System.out.println("Checkout completed successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Checkout failed: " + e.getMessage());
        }
    }
} 