package User;

import java.util.List;

import Helpers.Helpers;

public class AdminUserManager {
    private final UserQueryService userQueryService;

    public AdminUserManager(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    public void listAllUsers() {
        List<User> users = userQueryService.getAllUsers();

        System.out.println("\nAll users:");
        Helpers.printIndexed(users, User::toString);
    }
}
