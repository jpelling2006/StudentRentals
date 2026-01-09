package Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import FrontEnd.Session;
import Helpers.Helpers;
import Properties.Property;
import Properties.PropertyManager;

public class HomeownerReviewManager {
    private final ReviewQueryService reviewQueryService;
    private final PropertyManager propertyManager;
    private final Session session;
    private final Scanner scanner;

    public HomeownerReviewManager(
        ReviewQueryService reviewQueryService,
        PropertyManager propertyManager,
        Session session,
        Scanner scanner
    ) {
        this.reviewQueryService = reviewQueryService;
        this.propertyManager = propertyManager;
        this.session = session;
        this.scanner = scanner;
    }

    public void listReviews() {
        List<Review> reviews = reviewQueryService.getHomeownerReviews(
            session.getCurrentUser().getUsername()
        );
        System.out.println("\nYour reviews:");
        Helpers.printIndexed(reviews, Review::toString);
    }

    
}
