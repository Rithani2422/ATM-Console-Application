public class Transaction {
    // Defines the 'Transaction' class, representing a transaction performed in the system

    private String performedBy;
    // Declares a private field to store the name of the person or entity that performed the transaction

    private String type;
    // Declares a private field to store the type of transaction (e.g., Deposit, Withdrawal)

    private double amount;
    // Declares a private field to store the amount involved in the transaction

    public Transaction(String performedBy, String type, double amount) {
        // Constructor to initialize a Transaction object with the performer, type, and amount

        this.type = type;
        // Sets the 'type' field to the provided transaction type

        this.amount = amount;
        // Sets the 'amount' field to the provided transaction amount

        this.performedBy = performedBy;
        // Sets the 'performedBy' field to the provided performer of the transaction
    }

    public String getType() {
        // Getter method to retrieve the type of transaction

        return type;
        // Returns the value of the 'type' field
    }

    public double getAmount() {
        // Getter method to retrieve the amount involved in the transaction

        return amount;
        // Returns the value of the 'amount' field
    }

    public String getPerformedBy() {
        // Getter method to retrieve who performed the transaction

        return performedBy;
        // Returns the value of the 'performedBy' field
    }
}
