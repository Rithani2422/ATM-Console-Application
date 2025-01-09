import ListOfNotes.Notes100;
import ListOfNotes.Notes200;
import ListOfNotes.Notes2000;
import ListOfNotes.Notes500;
import Notes.Notes;

import java.util.ArrayList;  // Importing ArrayList for collection handling
import java.util.Scanner;   // Importing Scanner for taking user input

public class ATM {
    // Declares the 'ATM' class, which contains the logic for the ATM system

    static ArrayList<User> users = new ArrayList<>();
    // Static list to store all registered users in the ATM system

    static ArrayList<Notes> notes = new ArrayList<>();
    // Static list to store all the denominations of notes and their counts

    static ArrayList<Admin> admins = new ArrayList<>();
    // Static list to store all the admins of the ATM system

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

        admins.add(new Admin("admin1", "password123"));  // Adds a default admin with username "admin1" and password "password123"
        ATM.getAccounts().add(admins.get(0));  // Adds the default admin to the accounts list

        initializeNotes();  // Calls the method to initialize the notes (ATM cash denominations)

        while (true) {
            // Infinite loop for the main menu

            System.out.println("\n1. Admin Login\n2. User Login\n3. Exit");
            // Displays the main menu options

            System.out.print("Who is logging in: ");
            // Prompt user to choose between Admin, User, or Exit

            int login = Integer.parseInt(scanner.nextLine());  // Reads the user's choice and converts it to an integer

            if (login == 1) {
                // If Admin login is chosen
                Admin loggedInAdmin = adminLogin(scanner, accounts);  // Calls the adminLogin method to authenticate the admin

                if (loggedInAdmin != null) {
                    // If login is successful, proceed to admin actions
                    String Username = loggedInAdmin.getUsername();
                    System.out.println("Logged in as " + Username);
                    AdminActions.adminActions(scanner, loggedInAdmin);  // Calls the admin actions with the logged-in admin
                } else {
                    System.out.println("Returning to main menu...");
                    // If login fails, return to the main menu
                }

            } else if (login == 2) {
                // If User login is chosen
                User loggedInUser = userLogin(scanner, accounts);  // Calls the userLogin method to authenticate the user

                if (loggedInUser != null) {
                    // If login is successful, proceed to user actions
                    UserActions.userActions(scanner, loggedInUser);
                } else {
                    System.out.println("Returning to main menu...");
                    // If login fails, return to the main menu
                }

            } else if (login == 3) {
                // If Exit option is chosen
                System.out.println("Exiting the program.");
                break;  // Breaks out of the infinite loop to terminate the program
            } else {
                System.out.println("Invalid choice. Please select 1, 2, or 3.");
                // Handles invalid menu choices
            }
        }
    }

    public static Admin adminLogin(Scanner scanner, ArrayList<Account> accounts) {
        // Method to handle admin login functionality

        System.out.print("Enter Admin Username: ");
        // Prompt the admin to enter their username

        String username = scanner.nextLine();
        // Reads the username input

        Admin matchedAdmin = null;
        // Initializes a variable to store the matched admin

        // Loop through all accounts to find if an admin exists with the given username
        for (Account ac : accounts) {
            if (ac instanceof Admin) {  // Check if the account is an instance of Admin
                Admin admin = (Admin) ac;  // Cast Account to Admin
                if (admin.getUsername().equals(username)) {
                    matchedAdmin = admin;  // Match found, store the admin
                    break;  // Exit the loop once a match is found
                }
            }
        }

        if (matchedAdmin == null) {
            System.out.println("Invalid username. Returning to login choice.");
            return null;  // Return null if no matching admin is found
        }

        int attempts = 3;  // Number of login attempts

        while (attempts > 0) {
            System.out.print("Enter Admin Password: ");
            // Prompt admin to enter their password

            String password = scanner.nextLine();
            // Reads the password input

            if (matchedAdmin.getPassword().equals(password)) {
                // If password matches, grant access
                System.out.println("Admin login successful!");
                return matchedAdmin;  // Returns the logged-in admin object
            }

            attempts--;  // Decrement attempts
            if (attempts > 0) {
                System.out.println("Incorrect password. Attempts remaining: " + attempts);
                // Shows remaining attempts
            } else {
                System.out.println("Too many failed attempts. Returning to login choice.");
                // After 3 failed attempts, show failure message
            }
        }

        return null;  // Returns null if login fails after all attempts
    }

    public static User userLogin(Scanner scanner, ArrayList<Account> accounts) {
        // Method to handle user login functionality

        System.out.print("Enter Username: ");
        // Prompt the user to enter their username

        String username = scanner.nextLine();
        // Reads the username input

        User matchedUser = null;
        // Initializes a variable to store the matched user

        // Loop through all accounts to find if a user exists with the given username
        for (Account ac : accounts) {
            if (ac instanceof User) {  // Check if the account is an instance of User
                User user = (User) ac;  // Cast Account to User
                if (user.getUsername().equals(username)) {
                    matchedUser = user;  // Match found, store the user
                    break;  // Exit the loop once a match is found
                }
            }
        }

        if (matchedUser == null) {
            System.out.println("Invalid username. Returning to login choice.");
            return null;  // Return null if no matching user is found
        }

        int attempts = 3;  // Number of login attempts

        while (attempts > 0) {
            System.out.print("Enter Password: ");
            // Prompt user to enter their password

            String password = scanner.nextLine();
            // Reads the password input

            if (matchedUser.getPassword().equals(password)) {
                // If password matches, grant access
                System.out.println("User login successful!");
                return matchedUser;  // Returns the logged-in user object
            }

            attempts--;  // Decrement attempts
            if (attempts > 0) {
                System.out.println("Incorrect password. Attempts remaining: " + attempts);
                // Shows remaining attempts
            } else {
                System.out.println("Too many failed attempts. Returning to login choice.");
                // After 3 failed attempts, show failure message
            }
        }

        return null;  // Returns null if login fails after all attempts
    }

    static User findUserByUsername(String username) {
        // Method to find a user by their username

        for (User user : users) {
            // Loop through the list of users

            if (user.getUsername().equals(username)) {
                // Checks if the username matches

                return user;  // Returns the matched user
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

    public static ArrayList<User> getUsers() {
        // Method to retrieve the list of users

        return users;  // Returns the list of users
    }

    // Getter method for accounts
    public static ArrayList<Account> getAccounts() {
        return accounts;  // Returns the list of accounts (both users and admins)
    }
}







