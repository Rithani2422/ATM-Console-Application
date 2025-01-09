public class User extends Account {
    // The 'User' class extends the 'Account' class, inheriting all its properties and methods
    private double balance;
    // Constructor to initialize a User object with a username, password, and initial balance
    public User(String username, String password ,double balance) {
        // Call the superclass (Account) constructor to initialize username, password, and balance
        super(username, password);
        // The 'super' keyword refers to the parent class (Account) and initializes the inherited fields
        this.balance = balance;
    }
    public double getBalance() {
        return balance;  // Return the current balance
    }

    // Setter method to update the account's balance
    public void setBalance(double balance) {
        this.balance = balance;  // Update the balance with the new value
    }
}




