package properties;

import session.Session;
import user.AdministratorUser;
import user.HomeownerUser;
import user.User;

public final class PropertyManager {
    private static PropertyManager instance;

    public static PropertyManager getInstance() {
        if (instance == null) { instance = new PropertyManager(); }
        return instance;
    }

    private PropertyManager() {}

    public static boolean handleOnce() {
        if (!Session.isLoggedIn()) {
            System.out.println("Please log in first.");
            return true;
        }

        User user = Session.getCurrentUser();

        // automatic routing based on user type
        if (user instanceof AdministratorUser) {
            return AdminPropertyManager.handleOnce();
        } else if (user instanceof HomeownerUser) {
            return HomeownerPropertyManager.handleOnce();
        }

        System.out.println("You do not have access to properties.");
        return true;
    }
}
