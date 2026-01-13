package user;

import java.util.List;
import java.util.Scanner;

import helpers.Helpers;
import session.Session;

public final class AdminUserManager implements UserHandler {
    private static Scanner scanner = new Scanner(System.in);
    private static AdminUserManager instance;

    public static AdminUserManager getInstance() {
        if (instance == null) { instance = new AdminUserManager(); }
        return instance;
    }

    private AdminUserManager() {}

    private static void listAllUsers() {
        List<User> users = UserQueryService.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("There are no users. How are you here?");
            return;
        }

        // prints a list of all users
        System.out.println("\nAll users:");
        Helpers.printIndexed(users, User::toString);
    }

    private static void deleteUser() {
        List<User> users = UserQueryService.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("There are no users. How are you here?");
            return;
        }

        User selectedUser = Helpers.selectFromList(
            scanner, 
            users, 
            "Select a user to delete", 
            User::toString
        );

        if (selectedUser == null) {
            System.out.println("User doesn't exist.");
            return;
        }

        if (selectedUser == Session.getCurrentUser()) {
            System.out.println("Can't delete self.");
            return;
        }

        System.out.println("Are you sure you want to delete this user?");
        System.out.println(selectedUser.toString());

        if (!Helpers.confirm(scanner)) {
            System.out.println("Deletion cancelled.");
            return;
        }

        User.removeUser(selectedUser.getUsername());
        System.out.println("User deleted successfully");
    }

    public static boolean handleOnce() {
        System.out.println("\nAdmin User Management System");
        System.out.println("1. List all users");
        System.out.println("2. Delete user");
        System.out.println("3. Back");

        return switch (
            Helpers.readIntInRange(scanner, "Choose option: ", 1, 3)
        ) {
            case 1 -> {
                listAllUsers();
                yield false;
            }
            case 2 -> {
                deleteUser();
                yield false;
            }
            case 3 -> true;
            default -> false;
        };
    }
}
