package review;

import access.ReviewAccess;
import session.Session;
import user.User;

public class ReviewManager {
    private Session session;

    public ReviewManager(Session session) { this.session = session; }
    

    public boolean handleOnce() {
        if (!session.isLoggedIn()) {
            System.out.println("Please log in first.");
            return true;
        }

        User user = session.getCurrentUser();

        if (user instanceof ReviewAccess reviewAccess) {
            return reviewAccess.getReviewHandler().handleOnce();
        }

        System.out.println("You do not have access to reviews.");
        return true;
    }
}
