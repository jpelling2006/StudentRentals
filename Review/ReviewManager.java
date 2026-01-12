package review;

import session.Session;
import user.AdministratorUser;
import user.HomeownerUser;
import user.StudentUser;
import user.User;

public class ReviewManager {
    private final Session session;

    private final AdminReviewManager adminReviewManager;
    private final HomeownerReviewManager homeownerReviewManager;
    private final StudentReviewManager studentReviewManager;

    public ReviewManager(
        Session session,
        AdminReviewManager adminReviewManager,
        HomeownerReviewManager homeownerReviewManager,
        StudentReviewManager studentReviewManager
    ) {
        this.session = session;
        this.adminReviewManager = adminReviewManager;
        this.homeownerReviewManager = homeownerReviewManager;
        this.studentReviewManager = studentReviewManager;
    }

    public boolean handleOnce() {
        if (!session.isLoggedIn()) {
            System.out.println("Please log in first.");
            return true;
        }

        User user = session.getCurrentUser();

        // automatic routing based on user type
        if (user instanceof AdministratorUser) {
            return adminReviewManager.handleOnce();
        } else if (user instanceof HomeownerUser) {
            return homeownerReviewManager.handleOnce();
        } else if (user instanceof StudentUser) {
            return studentReviewManager.handleOnce();
        }

        System.out.println("You do not have access to reviews.");
        return true;
    }
}