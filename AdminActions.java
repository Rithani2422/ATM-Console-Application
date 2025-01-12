import Notes.Notes;
import ListOfNotes.Notes100;
import ListOfNotes.Notes200;
import ListOfNotes.Notes2000;
import ListOfNotes.Notes500;

import java.util.Scanner;
import java.util.ArrayList;
public class AdminActions {

    // Method to display the admin menu and handle admin actions
    public static void adminActions(Scanner scanner, Admin admin) {
        while (true) {
            // Display the admin menu
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add User Account");
            System.out.println("2. Delete User Account");
            System.out.println("3. View All Users' Transaction Histories");
            System.out.println("4. View Specific User's Transaction History");
            System.out.println("5. View Admin Transactions");
            System.out.println("6. Deposit to ATM");
            System.out.println("7. Logout");


            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());  // Read and parse user choice

            switch (choice) {
                case 1:
                    addUserAccount(scanner, admin);
                    break;
                case 2:
                    deleteUserAccount(scanner);
                    break;
                case 3:
                    viewAllTransactionHistory();
                    break;
                case 4:
                    viewSpecificUserTransactionHistory(scanner);
                    break;
                case 5:
                    viewAdminTransactionHistory(admin);
                    break;
                case 6:
                    depositToATM(scanner, admin);
                    break;
                case 7:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

            // Method to add a user account
    private static void addUserAccount(Scanner scanner, Admin admin) {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();  // Prompt the admin to enter a username

        // Check if a user with the same username already exists
        Account userToAdd = ATM.findUserByUsername(username);

        if (userToAdd == null) {
            // Create a new user account if the username doesn't exist
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();  // Prompt for the password

            System.out.print("Enter Initial Balance: ");
            double balance = Double.parseDouble(scanner.nextLine());  // Prompt for the initial balance

            // Log the transaction for the admin: User account creation with initial balance
            addAdminTransaction(admin.getUsername(), "User Account Creation for " + username, balance, admin);

            // Create a new user and add to the system
            User newUser = new User(username, password, balance);
            ATM.getUsers().add(newUser);  // Add user to the list of users
            ATM.getAccounts().add(newUser);  // Add user to the list of all accounts

            System.out.println("User account created successfully.");
        } else {
            System.out.println("User account already exists.");  // Inform if the username already exists
        }
    }

    // Method to delete a user account
    private static void deleteUserAccount(Scanner scanner) {
        System.out.print("Enter Username to delete: ");
        String username = scanner.nextLine();  // Prompt for the username of the user to delete

        Account userToRemove = ATM.findUserByUsername(username);  // Find the user by username

        if (userToRemove != null) {
            ATM.getUsers().remove(userToRemove);  // Remove user from the list of users
            System.out.println("User account deleted successfully.");
        } else {
            System.out.println("User not found.");  // Inform if the user does not exist
        }
    }

    // Method to deposit money into the ATM
    private static void depositToATM(Scanner scanner, Admin admin) {
        System.out.print("Enter the total deposit amount: Rs. ");
        double totalDepositAmount = Double.parseDouble(scanner.nextLine());  // Prompt for the total deposit amount

        System.out.println("Enter the number of notes for each denomination:");

        // Prompt the admin to input the number of notes for each denomination
        System.out.print("2000: ");
        int notes2000Count = Integer.parseInt(scanner.nextLine());

        System.out.print("500: ");
        int notes500Count = Integer.parseInt(scanner.nextLine());

        System.out.print("200: ");
        int notes200Count = Integer.parseInt(scanner.nextLine());

        System.out.print("100: ");
        int notes100Count = Integer.parseInt(scanner.nextLine());

        // Calculate the total value based on the notes entered
        double calculatedTotal = (notes2000Count * 2000) + (notes500Count * 500) +
                (notes200Count * 200) + (notes100Count * 100);

        // Check if the calculated total matches the entered deposit amount
        if (calculatedTotal == totalDepositAmount) {
            // Update the ATM's note counts
            updateATMNotes(2000, notes2000Count);
            updateATMNotes(500, notes500Count);
            updateATMNotes(200, notes200Count);
            updateATMNotes(100, notes100Count);

            System.out.println("Successfully deposited Rs." + totalDepositAmount);
            System.out.println("New ATM Balance: Rs." + ATM.getTotalATMBalance());

            // Log the deposit transaction for the admin
            if (admin != null) {
                addAdminTransaction(admin.getUsername(), "ATM Deposit", totalDepositAmount, admin);
            }
        } else {
            System.out.println("The total of denominations does not match the deposit amount. Please try again.");
        }
    }

    // Helper method to update the ATM's notes
    private static void updateATMNotes(int denomination, int count) {
        for (Notes note : ATM.notes) {
            if (note.getNote() == denomination) {
                note.setCount(note.getCount() + count);  // Update the count of the specified denomination
                return;
            }
        }

        // If the denomination is not found, create a new note entry and add it to the list
        ATM.notes.add(new Notes2000(denomination, count));
        ATM.notes.add(new Notes500(denomination, count));
        ATM.notes.add(new Notes200(denomination, count));
        ATM.notes.add(new Notes100(denomination, count));
    }

    // Method to view the transaction history of all users
    public static void viewAllTransactionHistory() {
        boolean foundTransactions = false;

        // Iterate over all accounts to process transaction histories for user accounts
        for (Account account : ATM.getAccounts()) {
            if (account instanceof User) { // Process only user accounts
                User user = (User) account;
                System.out.println("Transaction History for User: " + user.getUsername());

                // Retrieve the user's transaction history
                ArrayList<Transaction> userTransactions = user.getTransactionHistory();

                if (userTransactions.isEmpty()) {
                    System.out.println("No transactions found for this user.");
                } else {
                    boolean hasUserTransactions = false;

                    // Use a simple counter to ensure each transaction is printed once
                    for (Transaction transaction : userTransactions) {
                        if (transaction.getPerformedBy().equals(user.getUsername())) {
                            System.out.println("Performed By: " + transaction.getPerformedBy());
                            System.out.println("Type: " + transaction.getType());
                            System.out.println("Amount: " + transaction.getAmount());
                            hasUserTransactions = true;
                        }
                    }

                    if (!hasUserTransactions) {
                        System.out.println("No transactions performed by this user.");
                    }
                }
                foundTransactions = true;
            }
        }

        if (!foundTransactions) {
            System.out.println("No transactions found for users.");
        }
    }
    // Method to view the transaction history of a specific user
    public static void viewSpecificUserTransactionHistory(Scanner scanner) {
        System.out.print("Enter Username to view transactions: ");
        String username = scanner.nextLine(); // Get the username from the admin

        // Find the user account with the given username
        Account user = ATM.findUserByUsername(username);

        // Check if the user exists and is an instance of User
        if (user != null && user instanceof User) {
            User specificUser = (User) user; // Cast the account to User
            System.out.println("Transaction History for User: " + specificUser.getUsername());

            // Get the user's transaction history
            ArrayList<Transaction> userTransactions = specificUser.getTransactionHistory();

            // Display transactions if any exist
            if (userTransactions.isEmpty()) {
                System.out.println("No transactions found for this user.");
            } else {
                boolean hasUserTransactions = false; // Flag to check if there are any transactions by the user
                for (Transaction transaction : userTransactions) {
                    // Display only transactions performed by this user
                    if (transaction.getPerformedBy().equals(specificUser.getUsername())) {
                        System.out.println("Performed By: " + transaction.getPerformedBy());
                        System.out.println("Type: " + transaction.getType());
                        System.out.println("Amount: " + transaction.getAmount());
                        System.out.println("-------------------------------");
                        hasUserTransactions = true;
                    }
                }

                // If no transactions were performed by the user, print a message
                if (!hasUserTransactions) {
                    System.out.println("No transactions performed by this user.");
                }
            }
        } else {
            // User not found or invalid type
            System.out.println("User not found.");
        }
    }





    // Method to view the admin's own transaction history
    public static void viewAdminTransactionHistory(Admin admin) {
        boolean adminFound = false;

        // Iterate over all accounts to find the specified admin
        for (Account account : ATM.getAccounts()) {
            if (account instanceof Admin) {
                Admin adminToView = (Admin) account;

                // Check if the username matches the admin we're looking for
                if (adminToView.getUsername().equals(admin.getUsername())) {
                    adminFound = true;
                    System.out.println("Admin Transaction History for: " + adminToView.getUsername());

                    // Filter the transactions to show only those performed by the admin
                    if (adminToView.getTransactionHistory().isEmpty()) {
                        System.out.println("No transactions found.");
                    } else {
                        boolean hasAdminTransactions = false;

                        // Iterate over transactions and print only those performed by the admin
                        for (Transaction transaction : adminToView.getTransactionHistory()) {
                            if (transaction.getPerformedBy().equals(adminToView.getUsername())) {
                                System.out.println("Performed By: " + transaction.getPerformedBy());
                                System.out.println("Type: " + transaction.getType());
                                System.out.println("Amount: " + transaction.getAmount());
                                hasAdminTransactions = true;
                            }
                        }

                        // If there were no transactions by the admin, print a message
                        if (!hasAdminTransactions) {
                            System.out.println("No transactions performed by this admin.");
                        }
                    }
                    return;
                }
            }
        }

        // If the admin was not found in the system
        if (!adminFound) {
            System.out.println("Admin not found in the system.");
        }
    }

    // Method to log admin transactions
    public static void addAdminTransaction(String performedBy, String type, double amount, Admin admin) {
        // Record the transaction for the admin

        Transaction transaction = new Transaction(admin.getUsername(), type, amount);
        admin.getTransactionHistory().add(transaction);  // Add to admin’s history
    }
}







