package review;

import java.util.List;
import java.util.Scanner;

import helpers.Helpers;
import properties.Property;

public class AdminReviewManager {
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

        System.out.println("\nAll reviews:");

        Helpers.printIndexed(reviews, Review::toString);
    }

    public void deleteAnyReview() {
        List<Review> reviews = reviewQueryService.getAllReviews();

        if (reviews.isEmpty()) {
            System.out.println("You have no reviews to delete");
            return;
        }

        listAllReviews();

        Review selectedReview = Helpers.selectFromList(
            scanner,
            reviews,
            "Select a review to delete"
        );

        if (!confirmDeletion(selectedReview)) {
            System.out.println("Deletion cancelled");
            return;
        }

        selectedReview.getProperty().removeReview(selectedReview.getUsername());
        System.out.println("Room deleted successfully.");
    }

    private boolean confirmDeletion(Review review) {
        while (true) {
            System.out.println("\nAre you sure you want to delete this review?");

            Property property = review.getProperty();
            String address = (property != null)
                ? property.getAddress() : "Unknown property";

            System.out.println(
                address + " - ("
                + review.getStars() + ")\n"
                + review.getTitle()
            );

            return Helpers.confirm(scanner);
        }
    }
}
