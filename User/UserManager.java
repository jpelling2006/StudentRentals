package user;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import helpers.Helpers;
import session.Session;

public class UserManager {
    private LoggedOutManager loggedOutManager;
    private Scanner scanner;
    private Session session;

    public UserManager(Session session, Scanner scanner) {
        this.session = session;
        this.scanner = scanner;
    }
    // account

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

    public void loggedOutMenu() {
        while (true) {
            System.out.println("\nUser Management System");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Forget Password");
            System.out.println("4. Exit");

            Integer choice = Helpers.readInt(scanner, "Choose option: ");

            switch (choice) {
                case 1 -> loggedOutManager.register();
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
