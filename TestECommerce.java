import java.time.LocalDate;

public class TestECommerce {
    public static void main(String[] args) {
        System.out.println("=== E-Commerce System Test Suite ===\n");
        
        ECommerceSystem ecommerce = new ECommerceSystem();
        
        testCase1(ecommerce);
        testCase2(ecommerce);
        testCase3(ecommerce);
        testCase4(ecommerce);
        testCase5(ecommerce);
        testCase6(ecommerce);
        testCase7(ecommerce);
        
        System.out.println("=== All Tests Completed ===");
    }
    
    private static void testCase1(ECommerceSystem ecommerce) {
        System.out.println("Test Case 1: Normal checkout with mixed products");
        
        Product cheese = new ExpirableProduct("Cheese", 100, 10, LocalDate.now().plusDays(30), 0.2, true);
        Product biscuits = new ExpirableProduct("Biscuits", 150, 5, LocalDate.now().plusDays(15), 0.7, true);
        Product scratchCard = new NonExpirableProduct("Scratch Card", 50, 20, 0.0, false);
        
        Customer customer = new Customer("Test Customer", 1000);
        Cart cart = new Cart();
        
        try {
            cart.add(cheese, 2);
            cart.add(biscuits, 1);
            cart.add(scratchCard, 1);
            
            ecommerce.checkout(customer, cart);
            System.out.println("✓ Test Case 1 PASSED\n");
        } catch (Exception e) {
            System.out.println("✗ Test Case 1 FAILED: " + e.getMessage() + "\n");
        }
    }
    
    private static void testCase2(ECommerceSystem ecommerce) {
        System.out.println("Test Case 2: Checkout with insufficient balance");
        
        Product tv = new NonExpirableProduct("TV", 500, 3, 15.0, true);
        Customer poorCustomer = new Customer("Poor Customer", 50);
        Cart cart = new Cart();
        
        try {
            cart.add(tv, 1);
            ecommerce.checkout(poorCustomer, cart);
            System.out.println("✗ Test Case 2 FAILED: Should have thrown insufficient balance error\n");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Test Case 2 PASSED: " + e.getMessage() + "\n");
        }
    }
    
    private static void testCase3(ECommerceSystem ecommerce) {
        System.out.println("Test Case 3: Checkout with empty cart");
        
        Customer customer = new Customer("Test Customer", 1000);
        Cart emptyCart = new Cart();
        
        try {
            ecommerce.checkout(customer, emptyCart);
            System.out.println("✗ Test Case 3 FAILED: Should have thrown empty cart error\n");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Test Case 3 PASSED: " + e.getMessage() + "\n");
        }
    }
    
    private static void testCase4(ECommerceSystem ecommerce) {
        System.out.println("Test Case 4: Checkout with out of stock product");
        
        Product mobile = new NonExpirableProduct("Mobile", 800, 8, 0.3, true);
        Customer customer = new Customer("Test Customer", 1000);
        Cart cart = new Cart();
        
        try {
            cart.add(mobile, 10);
            ecommerce.checkout(customer, cart);
            System.out.println("✗ Test Case 4 FAILED: Should have thrown out of stock error\n");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Test Case 4 PASSED: " + e.getMessage() + "\n");
        }
    }
    
    private static void testCase5(ECommerceSystem ecommerce) {
        System.out.println("Test Case 5: Checkout with expired product");
        
        Product expiredCheese = new ExpirableProduct("Expired Cheese", 50, 5, LocalDate.now().minusDays(1), 0.2, true);
        Customer customer = new Customer("Test Customer", 1000);
        Cart cart = new Cart();
        
        try {
            cart.add(expiredCheese, 1);
            ecommerce.checkout(customer, cart);
            System.out.println("✗ Test Case 5 FAILED: Should have thrown expired product error\n");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Test Case 5 PASSED: " + e.getMessage() + "\n");
        }
    }
    
    private static void testCase6(ECommerceSystem ecommerce) {
        System.out.println("Test Case 6: Multiple items with shipping");
        
        Product cheese = new ExpirableProduct("Cheese", 100, 10, LocalDate.now().plusDays(30), 0.2, true);
        Product biscuits = new ExpirableProduct("Biscuits", 150, 5, LocalDate.now().plusDays(15), 0.7, true);
        Product tv = new NonExpirableProduct("TV", 500, 3, 15.0, true);
        
        Customer customer = new Customer("Test Customer", 2000);
        Cart cart = new Cart();
        
        try {
            cart.add(cheese, 2);
            cart.add(biscuits, 1);
            cart.add(tv, 1);
            
            ecommerce.checkout(customer, cart);
            System.out.println("✓ Test Case 6 PASSED\n");
        } catch (Exception e) {
            System.out.println("✗ Test Case 6 FAILED: " + e.getMessage() + "\n");
        }
    }
    
    private static void testCase7(ECommerceSystem ecommerce) {
        System.out.println("Test Case 7: Non-shippable items only");
        
        Product scratchCard = new NonExpirableProduct("Scratch Card", 50, 20, 0.0, false);
        Product giftCard = new NonExpirableProduct("Gift Card", 100, 15, 0.0, false);
        
        Customer customer = new Customer("Test Customer", 1000);
        Cart cart = new Cart();
        
        try {
            cart.add(scratchCard, 3);
            cart.add(giftCard, 2);
            
            ecommerce.checkout(customer, cart);
            System.out.println("✓ Test Case 7 PASSED\n");
        } catch (Exception e) {
            System.out.println("✗ Test Case 7 FAILED: " + e.getMessage() + "\n");
        }
    }
} 