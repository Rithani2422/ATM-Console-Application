import java.util.ArrayList;

public class Account {
    // Instance variables to store account information
    private String username;  // The account's username
    private String password;  // The account's password
    private static ArrayList<Transaction> transactionHistory;  // Static list to store transaction history for all accounts

    // Constructor to initialize account with username, password, and balance
    public Account(String username, String password) {
        this.username = username;   // Set the account username
        this.password = password;   // Set the account password
        this.transactionHistory = new ArrayList<>();  // Initialize an empty transaction history list
    }

    // Getter method to retrieve the account's username
    public String getUsername() {
        return username;  // Return the username
    }

    // Getter method to retrieve the account's password
    public String getPassword() {
        return password;  // Return the password
    }

    // Setter method to update the account's password
    public void setPassword(String password) {
        this.password = password;  // Set the new password
    }

    // Getter method to retrieve the account's transaction history
    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;  // Return the list of transactions
    }
}

