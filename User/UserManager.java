package user;

import session.Session;
import ui.AdminState;
import ui.HomeownerState;
import ui.LoggedOutState;
import ui.StudentState;
import ui.UIContext;

public final class UserManager {
    private static UserManager instance;

    public static UserManager getInstance() {
        if (instance == null) { instance = new UserManager(); }
        return instance;
    }

    private UserManager() {}

    public static boolean handleOnce(UIContext context) {
        if (!Session.isLoggedIn()) {
            return LoggedOutManager.handleOnce(context);
        }

        User user = Session.getCurrentUser();

        if (user instanceof StudentUser) {
            context.setState(StudentState.getInstance());
        } else if (user instanceof HomeownerUser) {
            context.setState(HomeownerState.getInstance());
        } else if (user instanceof AdministratorUser) {
            context.setState(AdminState.getInstance());
        } else {
            System.out.println("You do not have access to users.");
            context.setState(LoggedOutState.getInstance());
        }

        return false;
    }

    public static boolean handleManager(UIContext context) {
        User user = Session.getCurrentUser();

        if (user instanceof StudentUser || user instanceof HomeownerUser) {
            return NonAdminUserManager.handleOnce(context);
        } else if (user instanceof AdministratorUser) {
            return AdminUserManager.handleOnce();
        } else {
            System.out.println("You do not have access to users.");
            context.setState(LoggedOutState.getInstance());
        }

        return false;
    }
}