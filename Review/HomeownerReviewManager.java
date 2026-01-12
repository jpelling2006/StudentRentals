package review;

import java.util.List;
import java.util.Scanner;

import helpers.Helpers;
import session.Session;

public final class HomeownerReviewManager implements ReviewHandler {
    private final static Scanner scanner = new Scanner(System.in);
    private static HomeownerReviewManager instance;

    public static HomeownerReviewManager getInstance() {
        if (instance == null) { instance = new HomeownerReviewManager(); }
        return instance;
    }

    private HomeownerReviewManager() {}

    public static void listUserReviews() {
        List<Review> userReviews = ReviewQueryService.getHomeownerReviews(
            Session.getCurrentUser()
        );

        // prints list of all user reviews
        System.out.println("\nYour reviews:");
        Helpers.printIndexed(userReviews, Review::toString);
    }

    public static boolean handleOnce() {
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
