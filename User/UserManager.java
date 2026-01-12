package user;

import session.Session;

public class UserManager {
    private final LoggedOutManager loggedOutManager;
    private final NonAdminUserManager nonAdminUserManager;
    private final AdminUserManager adminUserManager;
    private final Session session;

    public UserManager(
        LoggedOutManager loggedOutManager,
        NonAdminUserManager nonAdminUserManager,
        AdminUserManager adminUserManager,
        Session session
    ) {
        this.loggedOutManager = loggedOutManager;
        this.nonAdminUserManager = nonAdminUserManager;
        this.adminUserManager = adminUserManager;
        this.session = session;
    }

    public boolean handleOnce() {
        if (!session.isLoggedIn()) {
            return loggedOutManager.handleOnce();
        }

        User user = session.getCurrentUser();

        if (user instanceof StudentUser || user instanceof HomeownerUser) {
            return nonAdminUserManager.handleOnce();
        } else if (user instanceof AdministratorUser) {
            return adminUserManager.handleOnce();
        }

        System.out.println("You do not have access to users.");
        return true;
    }
}
