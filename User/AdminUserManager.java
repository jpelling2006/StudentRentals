package user;

import java.util.List;
import java.util.Scanner;

import helpers.Helpers;

public class AdminUserManager {
    private final UserQueryService userQueryService;
    private final Scanner scanner;

    public AdminUserManager(
        UserQueryService userQueryService,
        Scanner scanner
    ) {
        this.userQueryService = userQueryService;
        this.scanner = scanner;
    }

    public void listAllUsers() {
        List<User> users = userQueryService.getAllUsers();

        // prints a list of all users
        System.out.println("\nAll users:");
        Helpers.printIndexed(users, User::toString);
    }

    // maybe also delete all info linked to this user?
    public void deleteUser() {
        List<User> users = userQueryService.getAllUsers();

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

        users.remove(selectedUser);
        System.out.println("User deleted successfully");
    }
}
