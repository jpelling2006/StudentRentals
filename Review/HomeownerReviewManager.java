package review;

import java.util.List;
import java.util.Scanner;

import helpers.Helpers;
import session.Session;

public class HomeownerReviewManager implements ReviewHandler {
    private final ReviewQueryService reviewQueryService;
    private final Scanner scanner;
    private final Session session;

    public HomeownerReviewManager(
        ReviewQueryService reviewQueryService,
        Session session,
        Scanner scanner
    ) {
        this.reviewQueryService = reviewQueryService;
        this.session = session;
        this.scanner = scanner;
    }

    public void listUserReviews() {
        List<Review> userReviews = reviewQueryService.getHomeownerReviews(
            session.getCurrentUser()
        );

        // prints list of all user reviews
        System.out.println("\nYour reviews:");
        Helpers.printIndexed(userReviews, Review::toString);
    }

    @Override
    public boolean handleOnce() {
        System.out.println("\nHomeowner Review Menu");
        System.out.println("1. View reviews");
        System.out.println("2. Back");

        return switch (
            Helpers.readIntInRange(
                scanner, 
                "Choose option: ", 
                1, 
                2
            )
        ) {
            case 1 -> {
                listUserReviews();
                yield false;
            }
            case 2 -> true;
            default -> false;
        };
    }
}
