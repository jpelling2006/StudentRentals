package review;

import session.Session;
import user.AdministratorUser;
import user.HomeownerUser;
import user.StudentUser;
import user.User;

public final class ReviewManager {
    private static ReviewManager instance;

    public static ReviewManager getInstance() {
        if (instance == null) { instance = new ReviewManager(); }
        return new ReviewManager();
    }

    private ReviewManager() {}

    public static boolean handleOnce() {
        if (!Session.isLoggedIn()) {
            System.out.println("Please log in first.");
            return true;
        }

        User user = Session.getCurrentUser();

        // automatic routing based on user type
        if (user instanceof AdministratorUser) {
            return AdminReviewManager.handleOnce();
        } else if (user instanceof HomeownerUser) {
            return HomeownerReviewManager.handleOnce();
        } else if (user instanceof StudentUser) {
            return StudentReviewManager.handleOnce();
        }

        System.out.println("You do not have access to reviews.");
        return true;
    }
}