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
    // inputs

    public void inputUsername(User user) {
        while (true) {
            String username = Helpers.readString(scanner, "Username: ", 32).toLowerCase();

            if (users.containsKey(username.toLowerCase())) {
                System.out.println("Username already exists.");
                continue;
            }

            user.setUsername(username);
            return;
        }
    }

    private void inputCommonDetails(User user) {
        user.setEmail(Helpers.readString(scanner, "Enter email: ", 64));
        user.setPhone(Helpers.readString(scanner, "Enter phone number: ", 10));

        while (true) {
            try {
                user.setPasswordHash(Helpers.readString(scanner, "Enter password (minimum 8 characters): ", 128));
                return;
            } catch (Exception e) { System.out.println("Password error."); }
        }
    }

    private void inputStudentDetails(StudentUser student) {
        student.setUniversity(Helpers.readString(scanner, "Enter university: ", 128));
        student.setStudentNumber(Helpers.readString(scanner, "Enter student number: ", 32));
    }

    // register

    public void register() {
        System.out.println("\nRegister new user");
        System.out.println("1. Student");
        System.out.println("2. Homeowner");

        Integer choice = Helpers.readIntInRange(scanner, "Choose user type: ", 1, 2);

        UserType type = (choice == 1) ? UserType.STUDENT : UserType.HOMEOWNER;

        UserFactory factory = UserFactoryProvider.getFactory(type);

        User user = factory.createUser();

        inputUsername(user);
        inputCommonDetails(user);

        if (user instanceof StudentUser student) { inputStudentDetails(student); }

        users.put(user.getUsername().toLowerCase(), user);
        System.out.println("Registration successful.");
    }

    // login

    public void login() {
        String username = Helpers.readString(scanner, "Username: ", 32).toLowerCase();
        String password = Helpers.readString(scanner, "Password: ", 128);

        User user = users.get(username);

        if (user == null) {
            System.out.println("Login failed.");
            return;
        }

        try {
            if (user.verifyPassword(password)) {
                session.login(user);
                System.out.println("Login successful.");
                userMenu();
            } else {
                System.out.println("Login failed.");
            }
        } catch (Exception e) { System.out.println("Login error."); }
    }

    // account

    private void viewDetails() {
        User user = session.getCurrentUser();

        System.out.println("\nYour details");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Type: " + user.getUserType());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Phone: " + user.getPhone());

        if (user instanceof StudentUser student) {
            System.out.println("University: " + student.getUniversity());
            System.out.println("Student number: " + student.getStudentNumber());
        }
    }

    public void editDetails() {
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

            if (user instanceof StudentUser) {
                System.out.println("4. University");
                System.out.println("5. Student Number");
                System.out.println("6. Cancel");
            } else {
                System.out.println("4. Cancel");
            }

            Integer choice = Helpers.readInt(scanner, "Choose option: ");

            switch (choice) {
                case 1 -> user.setEmail(Helpers.readString(scanner, "Enter new email: ", 64));
                case 2 -> user.setPhone(Helpers.readString(scanner, "Enter new phone number: ", 10));
                case 3 -> {
                    try {
                        user.setPasswordHash(Helpers.readString(scanner, "Enter new password:", 128));
                        System.out.println("Password updated.");
                    } catch (Exception e) { System.out.println("Password update failed."); }
                }
                case 4 -> {
                    if (user instanceof StudentUser student) { student.setUniversity(Helpers.readString(scanner, "Enter new university name: ", 128));}
                    else { return; }
                }
                case 5 -> {
                    if (user instanceof StudentUser student) { student.setStudentNumber(Helpers.readString(scanner, "Enter new student number: ", choice));}
                }
                case 6 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void userMenu() {
        while (session.isLoggedIn()) {
            System.out.println("\n1. View details");
            System.out.println("2. Edit details");
            System.out.println("3. Logout");

            int choice = Helpers.readIntInRange(scanner, "Choose option: ", 1, 3);

            switch (choice) {
                case 1 -> viewDetails();
                case 2 -> editDetails();
                case 3 -> session.logout();
            }
        }
    }

    public void forgetPassword() {
        String username = Helpers.readString(scanner, "Username: ", 32).toLowerCase();
        User user = users.get(username);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        while (true) {
            try {
                user.setPasswordHash(Helpers.readString(scanner, "New password: ", 128));
                System.out.println("Password reset.");
                return;
            } catch (Exception e) { System.out.println("Password error."); }
        }
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
