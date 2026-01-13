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

    private static void listUserReviews() {
        List<Review> userReviews = ReviewQueryService.getHomeownerReviews(
            Session.getCurrentUser()
        );

        if (userReviews.isEmpty()) {
            System.out.println("No bookings exist.");
            return;
        }

        // prints list of all user reviews
        System.out.println("\nYour reviews:");
        Helpers.printIndexed(userReviews, Review::toString);
    }

    protected static boolean handleOnce() {
        boolean running = true;
        while (running) {
            System.out.println("\nHomeowner Review Menu");
            System.out.println("1. View reviews");
            System.out.println("2. Back");

            switch (
                Helpers.readIntInRange(
                    scanner, 
                    "Choose option: ", 
                    1, 
                    2
                )
            ) {
                case 1 -> listUserReviews();
                case 2 -> running = false;
            }
        }
        return true;
    }
}
