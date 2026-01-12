package user;

import session.Session;

public final class UserManager {
    private static UserManager instance;

    public static UserManager getInstance() {
        if (instance == null) { instance = new UserManager(); }
        return instance;
    }

    private UserManager() {}

    public static boolean handleOnce() {
        if (!Session.isLoggedIn()) {
            return LoggedOutManager.handleOnce();
        }

        User user = Session.getCurrentUser();

        if (user instanceof StudentUser || user instanceof HomeownerUser) {
            return NonAdminUserManager.handleOnce();
        } else if (user instanceof AdministratorUser) {
            return AdminUserManager.handleOnce();
        }

        System.out.println("You do not have access to users.");
        return true;
    }
}
