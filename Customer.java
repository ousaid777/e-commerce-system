public class Customer {
    private String name;
    private double balance;
    
    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }
    
    public String getName() {
        return name;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void deductAmount(double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance. Required: " + amount + ", Available: " + balance);
        }
        this.balance -= amount;
    }
    
    public void addAmount(double amount) {
        this.balance += amount;
    }
} 