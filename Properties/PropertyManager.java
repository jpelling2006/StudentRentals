package properties;

import session.Session;
import user.AdministratorUser;
import user.HomeownerUser;
import user.User;

public class PropertyManager {
    private final Session session;
    private final AdminPropertyManager adminPropertyManager;
    private final HomeownerPropertyManager homeownerPropertyManager;

    public PropertyManager(
        Session session,
        AdminPropertyManager adminPropertyManager,
        HomeownerPropertyManager homeownerPropertyManager
    ) {
        this.session = session;
        this.adminPropertyManager = adminPropertyManager;
        this.homeownerPropertyManager = homeownerPropertyManager;
    }

    public boolean handleOnce() {
        if (!session.isLoggedIn()) {
            System.out.println("Please log in first.");
            return true;
        }

        User user = session.getCurrentUser();

        // automatic routing based on user type
        if (user instanceof AdministratorUser) {
            return adminPropertyManager.handleOnce();
        } else if (user instanceof HomeownerUser) {
            return homeownerPropertyManager.handleOnce();
        }

        System.out.println("You do not have access to properties.");
        return true;
    }
}
