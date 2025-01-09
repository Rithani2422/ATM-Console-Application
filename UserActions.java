import Notes.Notes; // Import the Notes class (presumably from a different package) for working with notes
import java.util.Scanner; // Import the Scanner class to handle user input

public class UserActions {
    // Defines the 'UserActions' class, which contains methods to handle various user operations

    // Method to handle user actions like balance check, deposit, withdraw, etc.
    public static void userActions(Scanner scanner, User currentUser) {
        // Infinite loop to keep showing the user menu until the user chooses to logout
        while (true) {
            // Display the menu of user actions
            System.out.println("\nUser Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View Transaction History");
            System.out.println("5. Change PIN");
            System.out.println("6. Logout");

            // Prompt the user to choose an action
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine()); // Read and parse the user's input

            // Check which option the user has selected
            if (choice == 1) {
                // Option to check balance
                System.out.println("Your balance is: Rs." + currentUser.getBalance());
            } else if (choice == 2) {
                // Option to deposit money
                depositAmount(scanner, currentUser);
            } else if (choice == 3) {
                // Option to withdraw money
                withdrawAmount(scanner, currentUser);
            } else if (choice == 4) {
                // Option to view transaction history
                viewCurrentUserTransactionHistory(currentUser);
            } else if (choice == 5) {
                // Option to change password
                changePassword(scanner, currentUser);
            } else if (choice == 6) {
                // Logout option
                System.out.println("Logging out");
                break; // Exit the menu loop and logout
            } else {
                // Handle invalid choices
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to handle deposit actions
    private static void depositAmount(Scanner scanner, User currentUser) {
        // Prompt the user to enter the total deposit amount
        System.out.print("Enter the total deposit amount: ");
        double depositAmount = Double.parseDouble(scanner.nextLine()); // Read and parse the deposit amount

        // Ensure that the deposit amount is a multiple of 100
        if (depositAmount % 100 != 0) {
            System.out.println("Deposit amount must be a multiple of 100.");
            return; // Exit the method if the deposit is invalid
        }

        // Prompt the user to enter the number of notes for each denomination
        System.out.println("Enter the number of notes for each denomination:");
        System.out.print("2000: ");
        int notes2000Count = Integer.parseInt(scanner.nextLine()); // Read number of Rs. 2000 notes
        System.out.print("500: ");
        int notes500Count = Integer.parseInt(scanner.nextLine());  // Read number of Rs. 500 notes
        System.out.print("200: ");
        int notes200Count = Integer.parseInt(scanner.nextLine());  // Read number of Rs. 200 notes
        System.out.print("100: ");
        int notes100Count = Integer.parseInt(scanner.nextLine());  // Read number of Rs. 100 notes

        // Calculate the total deposit based on the entered note denominations
        double calculatedDeposit = (2000 * notes2000Count) +
                (500 * notes500Count) +
                (200 * notes200Count) +
                (100 * notes100Count);

        // Ensure that the total calculated deposit matches the amount entered by the user
        if (calculatedDeposit != depositAmount) {
            System.out.println("The total of denominations does not match the deposit amount. Please try again.");
            return; // Exit the method if the amounts don't match
        }

        // Update the ATM's available notes with the entered denominations
        for (Notes note : ATM.notes) {
            if (note.getNote() == 2000) {
                note.setCount(note.getCount() + notes2000Count);
            } else if (note.getNote() == 500) {
                note.setCount(note.getCount() + notes500Count);
            } else if (note.getNote() == 200) {
                note.setCount(note.getCount() + notes200Count);
            } else if (note.getNote() == 100) {
                note.setCount(note.getCount() + notes100Count);
            }
        }

        // Update the user's balance with the deposited amount
        currentUser.setBalance(currentUser.getBalance() + depositAmount);

        // Record the deposit transaction in the user's transaction history
        addUserTransaction(currentUser.getUsername(), "Deposit", depositAmount, currentUser);

        // Display success message and new balance
        System.out.println("Successfully Deposited Rs." + depositAmount);
        System.out.println("New Balance: Rs." + currentUser.getBalance());
    }

    // Method to handle withdrawal actions
    private static void withdrawAmount(Scanner scanner, User currentUser) {
        System.out.print("Enter the amount to withdraw: ");
        int amount = Integer.parseInt(scanner.nextLine()); // Read and parse the withdrawal amount

        // Check if the withdrawal amount is valid (multiple of 100)
        if (amount % 100 != 0) {
            System.out.println("Withdrawal amount must be a multiple of 100.");
            return; // Exit if invalid amount
        }

        // Check if the user has enough balance
        if (amount > currentUser.getBalance()) {
            System.out.println("Insufficient balance in your account.");
            return; // Exit if insufficient balance
        }

        // Attempt to withdraw the amount from the ATM
        boolean successfulWithdrawal = ATM.withdrawFromATM(amount); // Call ATM to process withdrawal

        // If the ATM can't provide the requested amount, inform the user
        if (!successfulWithdrawal) {
            System.out.println("ATM cannot provide the requested amount due to insufficient denominations or funds.");
            return; // Exit if ATM can't process the withdrawal
        }

        // Deduct the withdrawn amount from the user's balance
        currentUser.setBalance(currentUser.getBalance() - amount);

        // Record the withdrawal transaction in the user's transaction history
        addUserTransaction(currentUser.getUsername(), "Withdraw", amount, currentUser);

        // Inform the user of the successful withdrawal and show the updated balance
        System.out.println("Withdrawal successful.");
        System.out.println("New balance: Rs." + currentUser.getBalance());
    }

    // Method to view the current user's transaction history
    private static void viewCurrentUserTransactionHistory(Account currentUser) {
        // Check if the current account is a User
        if (currentUser instanceof User) {
            User user = (User) currentUser; // Cast to User

            // Display the transaction history header
            System.out.println("Transaction History for User: " + user.getUsername());

            // Check if the user has no transactions
            if (user.getTransactionHistory().isEmpty()) {
                System.out.println("No transactions found for User: " + user.getUsername());
            } else {
                // Display all transactions for the user
                for (Transaction transaction : user.getTransactionHistory()) {
                    // Only show transactions where the current user performed the action
                    if (transaction.getPerformedBy().equals(user.getUsername())) {
                        System.out.println("Performed By: " + transaction.getPerformedBy());
                        System.out.println("Type: " + transaction.getType());
                        System.out.println("Amount: " + transaction.getAmount());
                    }
                }
            }
        } else {
            // If the current account is not a User, display an error
            System.out.println("Account type not recognized or transaction history not available for this account.");
        }
        System.out.println("End of Transaction History.");
    }

    // Method to change the user's password
    private static void changePassword(Scanner scanner, User currentUser) {
        // Prompt the user to enter their current password
        System.out.print("Enter your current password: ");
        String currentPassword = scanner.nextLine();

        // Check if the entered current password matches the stored password
        if (currentPassword.equals(currentUser.getPassword())) {
            // Prompt for a new password
            System.out.print("Enter your new password: ");
            String newPassword = scanner.nextLine();

            // Ensure the new password is not the same as the current password
            if (newPassword.equals(currentPassword)) {
                System.out.println("New password cannot be the same as the current password. Try again.");
            } else {
                // Update the user's password
                currentUser.setPassword(newPassword);
                System.out.println("Password changed successfully.");
            }
        } else {
            // If the current password is incorrect, show an error message
            System.out.println("Incorrect current password. Password change failed.");
        }
    }

    // Method to add a user transaction to their history
    public static void addUserTransaction(String performedBy, String type, double amount, User user) {
        // Create a new transaction for the user
        Transaction transaction = new Transaction(user.getUsername(), type, amount);

        // Add the transaction to the user's transaction history
        user.getTransactionHistory().add(transaction);
    }
}







