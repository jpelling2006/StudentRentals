package Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import FrontEnd.Session;
import Helpers.Helpers;
import Properties.Property;
import Properties.PropertyManager;

public class AdminReviewManager {
    private final PropertyManager propertyManager;
    private final ReviewQueryService reviewQueryService;
    private final Session session;
    private final Scanner scanner;

    public AdminReviewManager(
        PropertyManager propertyManager,
        ReviewQueryService reviewQueryService,
        Session session,
        Scanner scanner
    ) {
        this.propertyManager = propertyManager;
        this.reviewQueryService = reviewQueryService;
        this.session = session;
        this.scanner = scanner;
    }

    public void listAllReviews(List<Review> reviews) {
        if (reviews.isEmpty()) {
            System.out.println("You have no reviews.");
            return;
        }

        System.out.println("\nYour reviews:");
        for (int i = 0; i < reviews.size(); i++) {
            Review review = reviews.get(i);
            Property property = review.getProperty();
            String address = (property != null)
                ? property.getAddress() : "Unknown property";
                
            System.out.println(
                (i + 1) + ". "
                + address + " - ("
                + review.getStars() + ")\n"
                + review.getTitle()
            );
        }
    }

    public void deleteAnyReview() {
        List<Review> reviews = reviewQueryService.getAllReviews();

        if (reviews.isEmpty()) {
            System.out.println("You have no reviews to delete");
            return;
        }

        listAllReviews(reviews);

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
