package review;

import java.util.List;
import java.util.Scanner;

import helpers.Helpers;

public class AdminReviewManager implements ReviewHandler {
    private final ReviewQueryService reviewQueryService;
    private final Scanner scanner;

    public AdminReviewManager(
        ReviewQueryService reviewQueryService,
        Scanner scanner
    ) {
        this.reviewQueryService = reviewQueryService;
        this.scanner = scanner;
    }

    public void listAllReviews() {
        List<Review> reviews = reviewQueryService.getAllReviews();

        if (reviews.isEmpty()) {
            System.out.println("There are no reviews.");
            return;
        }

        // prints list of all reviews
        System.out.println("\nAll reviews:");
        Helpers.printIndexed(reviews, Review::toString);
    }

    public void deleteAnyReview() {
        List<Review> reviews = reviewQueryService.getAllReviews();

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

    @Override
    public boolean handleOnce() {
        System.out.println("\nAdmin Review Menu");
        System.out.println("1. View reviews");
        System.out.println("1. Delete review");
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
