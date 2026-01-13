package review;

import java.util.List;
import java.util.Scanner;

import helpers.Helpers;

public final class AdminReviewManager implements ReviewHandler {
    private final static Scanner scanner = new Scanner(System.in);
    private static AdminReviewManager instance;

    public static AdminReviewManager getInstance() {
        if (instance == null) { instance = new AdminReviewManager(); }
        return instance;
    }

    private AdminReviewManager() {}

    private static void listAllReviews() {
        List<Review> reviews = ReviewQueryService.getAllReviews();

        if (reviews.isEmpty()) {
            System.out.println("There are no reviews.");
            return;
        }

        // prints list of all reviews
        System.out.println("\nAll reviews:");
        Helpers.printIndexed(reviews, Review::toString);
    }

    private static void deleteAnyReview() {
        List<Review> reviews = ReviewQueryService.getAllReviews();

        if (reviews.isEmpty()) {
            System.out.println("You have no reviews to delete");
            return;
        }

        Review selectedReview = Helpers.selectFromList(
            scanner,
            reviews,
            "Select a review to delete",
            Review::toString
        );

        if (selectedReview == null) { return; }

        System.out.println("Are you sure you want to delete this review?");
        selectedReview.toString();

        if (!Helpers.confirm(scanner)) {
            System.out.println("Deletion cancelled");
            return;
        }

        selectedReview.getProperty().removeReview(selectedReview.getUsername());
        System.out.println("Room deleted successfully.");
    }

    public static boolean handleOnce() {
        System.out.println("\nAdmin Review Menu");
        System.out.println("1. View reviews");
        System.out.println("2. Delete review");
        System.out.println("2. Back");

        return switch (
            Helpers.readIntInRange(scanner, "Choose option: ", 1, 3)
        ) {
            case 1 -> {
                listAllReviews();
                yield false;
            }
            case 2 -> {
                deleteAnyReview();
                yield false;
            }
            case 3 -> true;
            default -> false;
        };
    }
}
