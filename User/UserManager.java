package user;

import java.util.Scanner;

import helpers.Helpers;
import session.Session;

public class UserManager {
    private LoggedOutManager loggedOutManager;
    private NonAdminUserManager nonAdminUserManager;
    private AdminUserManager adminUserManager;
    private Scanner scanner;
    private Session session;

    public UserManager(Session session, Scanner scanner) {
        this.session = session;
        this.scanner = scanner;
    }

    private void nonAdminMenu() {
        while (session.isLoggedIn()) {
            System.out.println("\nUser Management System");
            System.out.println("1. View details");
            System.out.println("2. Edit details");
            System.out.println("3. Logout");

            Integer choice = Helpers.readIntInRange(
                scanner, 
                "Choose option: ", 
                1, 
                3
            );

            switch (choice) {
                case 1 -> nonAdminUserManager.viewDetails();
                case 2 -> nonAdminUserManager.editDetails();
                case 3 -> session.logout();
            }
        }
    }

    private void loggedOutMenu() {
        while (true) {
            System.out.println("\nUser Management System");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Forgot Password");
            System.out.println("4. Exit");

            Integer choice = Helpers.readIntInRange(
                scanner, 
                "Choose option: ", 
                1, 
                4
            );

            switch (choice) {
                case 1 -> loggedOutManager.register();
                case 2 -> loggedOutManager.login();
                case 3 -> loggedOutManager.forgetPassword();
                case 4 -> { return; }
            }
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\nAdmin User Management System");
            System.out.println("1. List all users");
            System.out.println("2. Delete user");
            System.out.println("3. Back");

            Integer choice = Helpers.readIntInRange(scanner, "Choose option: ", 1, 3);

            switch (choice) {
                case 1 -> adminUserManager.listAllUsers();
                case 2 -> adminUserManager.deleteUser();
                case 3 -> { return; }
            }
        }
    }

    public void start() {
        if (!session.isLoggedIn()) {
            loggedOutMenu();
        }

        User currentUser = session.getCurrentUser();

        if (currentUser instanceof StudentUser || currentUser instanceof HomeownerUser) {
            nonAdminMenu();
        } else if (currentUser instanceof AdministratorUser) {
            adminMenu();
        } else {
            System.out.println("Unknown user type. Cannot start session.");
        }
    }

}
