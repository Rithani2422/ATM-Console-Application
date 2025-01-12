import Notes.Notes;
import ListOfNotes.Notes100;
import ListOfNotes.Notes200;
import ListOfNotes.Notes2000;
import ListOfNotes.Notes500;

import java.util.ArrayList;  // Importing ArrayList for collection handling
import java.util.Scanner;   // Importing Scanner for taking user input

public class ATM {
    // Declares the 'ATM' class, which contains the logic for the ATM system

    static ArrayList<Notes> notes = new ArrayList<>();
    // Static list to store all the denominations of notes and their counts

    static ArrayList<Account> accounts = new ArrayList<>();
    // Static list to store all the accounts (both users and admins) in the ATM system

    private static void initializeNotes() {
        // Method to initialize the list of notes with denominations and default counts

        notes.add(new Notes2000(2000, 0));  // Adds Rs. 2000 notes with a count of 0 to the notes list
        notes.add(new Notes500(500, 0));    // Adds Rs. 500 notes with a count of 0 to the notes list
        notes.add(new Notes200(200, 0));    // Adds Rs. 200 notes with a count of 0 to the notes list
        notes.add(new Notes100(100, 0));    // Adds Rs. 100 notes with a count of 0 to the notes list
    }

    public static void start() {
        // Entry point for the ATM functionality

        Scanner scanner = new Scanner(System.in);
        // Creates a Scanner object to read user input
        accounts.add(new Admin("admin1", "password123"));  // Adds a default admin with username "admin1" and password "password123"
        ATM.getAccounts().add(accounts.get(0));  // Adds the default admin to the accounts list

        initializeNotes();  // Calls the method to initialize the notes (ATM cash denominations)

        while (true) {
            // Infinite loop for the main menu

            System.out.println("\n1. Admin Login\n2. User Login\n3. Exit");
            // Displays the main menu options

            System.out.print("Who is logging in: ");
            // Prompt user to choose between Admin, User, or Exit

            int login = Integer.parseInt(scanner.nextLine());  // Reads the user's choice and converts it to an integer

            if (login == 1) {
                Account adminAccount = adminLogin(scanner, accounts);
                if (adminAccount != null) {
                    Admin loggedInAdmin = (Admin) adminAccount;
                    System.out.println("Logged in as " + loggedInAdmin.getUsername());
                    AdminActions.adminActions(scanner, loggedInAdmin);
                } else {
                    System.out.println("Admin login failed. Returning to main menu...");
                }
            } else if (login == 2) {
                Account userAccount = userLogin(scanner, accounts);
                if (userAccount != null) {
                    User loggedInUser = (User) userAccount;
                    UserActions.userActions(scanner, loggedInUser);
                } else {
                    System.out.println("User login failed. Returning to main menu...");
                }
            }

         else if (login == 3) {
                // If Exit option is chosen
                System.out.println("Exiting the program.");
                break;  // Breaks out of the infinite loop to terminate the program
            } else {
                System.out.println("Invalid choice. Please select 1, 2, or 3.");
                // Handles invalid menu choices
            }
        }
    }

    public static Account adminLogin(Scanner scanner, ArrayList<Account> accounts) {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();

        // Find the matching admin account
        Admin matchedAdmin = null;
        for (Account ac : accounts) {
            if (ac instanceof Admin) {
                Admin admin = (Admin) ac;
                if (admin.getUsername().equals(username)) {
                    matchedAdmin = admin;
                    break;
                }
            }
        }

        if (matchedAdmin == null) {
            System.out.println("Invalid username. Returning to login choice.");
            return null; // Login failed due to invalid username
        }

        int attempts = 3; // Allow up to 3 password attempts
        while (attempts > 0) {
            System.out.print("Enter Admin Password: ");
            String password = scanner.nextLine();

            if (matchedAdmin.getPassword().equals(password)) {
                System.out.println("Admin login successful!");
                return matchedAdmin; // Login successful
            }

            attempts--;
            if (attempts > 0) {
                System.out.println("Incorrect password. Attempts remaining: " + attempts);
            } else {
                System.out.println("Too many failed attempts. Returning to login choice.");
            }
        }

        return null; // Login failed due to incorrect password
    }


    public static Account userLogin(Scanner scanner, ArrayList<Account> accounts) {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        // Find the matching user account
        User matchedUser = null;
        for (Account ac : accounts) {
            if (ac instanceof User) {
                User user = (User) ac;
                if (user.getUsername().equals(username)) {
                    matchedUser = user;
                    break;
                }
            }
        }

        if (matchedUser == null) {
            System.out.println("Invalid username. Returning to login choice.");
            return null; // Login failed due to invalid username
        }

        int attempts = 3; // Allow up to 3 password attempts
        while (attempts > 0) {
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            if (matchedUser.getPassword().equals(password)) {
                System.out.println("User login successful!");
                return matchedUser; // Login successful
            }

            attempts--;
            if (attempts > 0) {
                System.out.println("Incorrect password. Attempts remaining: " + attempts);
            } else {
                System.out.println("Too many failed attempts. Returning to login choice.");
            }
        }

        return null; // Login failed due to incorrect password
    }

    static Account findUserByUsername(String username) {
        // Method to find a user by their username

        for (Account account : accounts) {
            // Loop through the list of users

            if (account.getUsername().equals(username)) {
                // Checks if the username matches

                return account;  // Returns the matched user
            }
        }

        return null;  // Returns null if no matching user is found
    }

    public static int getTotalATMBalance() {
        // Method to calculate the total balance available in the ATM

        int totalBalance = 0;  // Initialize the total balance to 0

        for (Notes note : notes) {
            // Loop through the list of notes

            totalBalance += note.getNote() * note.getCount();  // Adds the value of each note multiplied by its count
        }

        return totalBalance;  // Returns the total balance
    }

    public static boolean withdrawFromATM(int amount) {
        // Method to handle ATM withdrawal functionality

        int totalAvailableAmount = getTotalATMBalance();  // Retrieves the total balance in the ATM

        if (amount > totalAvailableAmount) {
            System.out.println("Insufficient funds in the ATM.");
            return false;  // If requested amount exceeds available funds
        }

        int remainingAmount = amount;  // Initialize the remaining amount to the requested amount

        for (Notes note : notes) {
            // Loop through the notes list to dispense the required amount

            int numNotesToWithdraw = Math.min(remainingAmount / note.getNote(), note.getCount());
            // Calculate the maximum number of notes to withdraw for the current denomination

            remainingAmount -= numNotesToWithdraw * note.getNote();  // Reduces the remaining amount
            note.setCount(note.getCount() - numNotesToWithdraw);  // Updates the count of notes in the ATM

            if (numNotesToWithdraw > 0) {
                System.out.println("Rs. " + note.getNote() + " x " + numNotesToWithdraw);
                // Displays the withdrawn notes
            }
        }

        if (remainingAmount > 0) {
            System.out.println("Cannot dispense the exact amount requested. Please try a different amount.");
            return false;  // If the exact amount can't be dispensed
        }

        return true;  // Successful withdrawal
    }

    public static ArrayList<Account> getUsers() {
        // Method to retrieve the list of users

        return accounts;  // Returns the list of users
    }

    // Getter method for accounts
    public static ArrayList<Account> getAccounts() {
        return accounts;  // Returns the list of accounts (both users and admins)
    }
}










