package User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import FrontEnd.Session;
import Helpers.Helpers;

public class UserManager {
    private Map<String, User> users = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);
    private Session session;

    public UserManager(Session session, Scanner scanner) {
        this.session = session;
        this.scanner = scanner;
    }

    // helpers

    private boolean usernameExists(String username) {
        return users.containsKey(username);
    }

    private boolean isStudent(User user) {
        return user.getUserType().equals("student");
    }

    // inputs

    public void inputUsername(User user) {
        while (true) {
            System.out.print("\nEnter username: ");
            String username = scanner.nextLine();

            if (usernameExists(username)) {
                System.out.println("Username already exists.");
                continue;
            }

            try {
                user.setUsername(username);
                System.out.println("Username set.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void inputUserType(User user) {
        while (true) {
            Integer choice; 

            System.out.println("Please pick from the following user types:");
            System.out.println("1. Student");
            System.out.println("2. Homeowner");
            System.out.print("Enter choice (1/2): ");

            String input = scanner.nextLine();

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
                continue;
            }

            if (choice == 1) {
                user.setUserType("student");
                System.out.println("User type set.");
                break;
            } else if (choice == 2) {
                user.setUserType("homeowner");
                System.out.println("User type set.");
                break;
            } else {
                System.out.println("Please enter either 1 or 2.");
            }
        }
    }

    public void inputPassword(User user) {
        while (true) {
            System.out.print("Enter password: ");
            String rawPassword = scanner.nextLine();

            if (rawPassword.isBlank()) {
                System.out.println("Password is required.");
                continue;
            }

            if (rawPassword.length() < 8) {
                System.out.println("Password must be at least 8 characters long.");
                continue;
            }

            try {
                user.setPasswordHash(rawPassword);
                System.out.println("Password set.");
                return;
            } catch (Exception e) {
                System.out.println("Failed to set password.");
            }
        }
    }

    public void inputEmail(User user) {
        while (true) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            try {
                user.setEmail(email);
                System.out.println("Email set.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void inputPhone(User user) {
        while (true) {
            System.out.print("Enter phone number: ");
            String phone = scanner.nextLine();

            try {
                user.setPhone(phone);
                System.out.println("Phone number set.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void inputUniversity(User user) {
        while (true) {
            System.out.print("Enter university name: ");
            String university = scanner.nextLine();

            try {
                user.setUniversity(university);
                System.out.println("University name set.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void inputStudentNumber(User user) {
        while (true) {
            System.out.print("Enter student number: ");
            String studentNumber = scanner.nextLine();

            try {
                user.setStudentNumber(studentNumber);
                System.out.println("Student number set.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // register

    public void register() {
        User user = new User();

        System.out.println("\nRegistering new user");

        inputUsername(user);
        inputUserType(user);
        inputEmail(user);
        inputPhone(user);
        inputPassword(user);
        if (isStudent(user)) {
            inputUniversity(user);
            inputStudentNumber(user);
        }

        // if this doesnt work put .tolowercase() back
        users.put(user.getUsername(), user);
        System.out.println("Registration successful");
    }

    // login

    public void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String rawPassword = scanner.nextLine();

        User user = users.get(username);

        if (user == null) {
            System.out.println("Login failed.");
            return;
        }

        try {
            if (user.verifyPassword(rawPassword)) {
                session.login(user);
                System.out.println("Login successful.");
                userMenu();
            } else { System.out.println("Login failed."); }
        } catch (Exception e) { System.out.println("Login error"); }
    }

    // account

    private void viewMyDetails() {
        if (!session.isLoggedIn()) {
            System.out.println("No user logged in.");
            return;
        }

        User user = session.getCurrentUser();

        System.out.println("\nYour Details:");
        System.out.println("Username: " + user.getUsername());
        System.out.println("User type: " + user.getUserType());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Phone: " + user.getPhone());

        if (isStudent(user)) {
            System.out.println("University: " + user.getUniversity());
            System.out.println("Student number: " + user.getStudentNumber());
        }
    }

    public void editMyDetails() {
        if (!session.isLoggedIn()) {
            System.out.println("No user logged in.");
            return;
        }

        User user = session.getCurrentUser();

        while (true) {
            System.out.println("\nEdit My Details");
            System.out.println("1. Email");
            System.out.println("2. Phone");
            System.out.println("3. Password");

            if (isStudent(user)) {
                System.out.println("4. Campus ID");
                System.out.println("5. Student Number");
                System.out.println("6. Cancel");
            } else {
                System.out.println("4. Cancel");
            }
            Integer choice = Helpers.readInt(scanner, "Enter choice: ");

            if (user.getUserType().equals("student")) {
                switch (choice) {
                    case 1 -> inputEmail(user);
                    case 2 -> inputPhone(user);
                    case 3 -> inputPassword(user);
                    case 4 -> inputUniversity(user);
                    case 5 -> inputStudentNumber(user);
                    case 6 -> { return; }
                    default -> System.out.println("Invalid option.");
                }
            } else {
                switch (choice) {
                    case 1 -> inputEmail(user);
                    case 2 -> inputPhone(user);
                    case 3 -> inputPassword(user);
                    case 4 -> { return; }
                    default -> System.out.println("Invalid option.");
                }
            }
        }
    }

    private void userMenu() {
        while (true) {
            User user = session.getCurrentUser();

            System.out.println("\nWelcome, " + user.getUsername());
            System.out.println("1. View my details");
            System.out.println("2. Edit my details");
            System.out.println("3. Logout");

            Integer choice = Helpers.readInt(scanner, "Choose option: ");

            switch (choice) {
                case 1 -> viewMyDetails();
                case 2 -> editMyDetails();
                case 3 -> {
                    session.logout();
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    public void forgetPassword() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        User user = users.get(username);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        inputPassword(user);
        System.out.println("Password reset.");
    }

    public void start() {
        while (true) {
            System.out.println("\nUser Management System");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Forget Password");
            System.out.println("4. Exit");

            Integer choice = Helpers.readInt(scanner, "Choose option: ");

            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> forgetPassword();
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
