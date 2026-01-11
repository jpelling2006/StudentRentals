package review;

import java.util.List;

import helpers.Helpers;
import session.Session;

public class HomeownerReviewManager {
    private final ReviewQueryService reviewQueryService;
    private final Session session;

    public HomeownerReviewManager(
        ReviewQueryService reviewQueryService,
        Session session
    ) {
        this.reviewQueryService = reviewQueryService;
        this.session = session;
    }

    public void listUserReviews() {
        List<Review> userReviews = reviewQueryService.getHomeownerReviews(
            session.getCurrentUser()
        );

        // prints list of all user reviews
        System.out.println("\nYour reviews:");
        Helpers.printIndexed(userReviews, Review::toString);
    }
}
