package Review;

import java.util.List;

import Helpers.Helpers;
import Session.Session;

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

    public void listReviews() {
        List<Review> reviews = reviewQueryService.getHomeownerReviews(
            session.getCurrentUser().getUsername()
        );
        System.out.println("\nYour reviews:");
        Helpers.printIndexed(reviews, Review::toString);
    }

    
}
