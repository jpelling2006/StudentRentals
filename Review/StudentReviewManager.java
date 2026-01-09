package Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import FrontEnd.Session;
import Helpers.Helpers;
import Properties.Property;
import Properties.PropertyManager;
import Room.Room;

public class StudentReviewManager {
    private final ReviewQueryService reviewQueryService;
    private final PropertyManager propertyManager;
    private final Session session;
    private final Scanner scanner;

    public StudentReviewManager(
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

    private boolean hasCompletedBooking(Property property, String username) {
        for (Room room : property.getRooms()) {
            if (room.bookingCompletedByUser(username)) { return true; }
        }
        return false;
    }

    public void createReview() {
        Property selectedProperty = Helpers.selectFromList(
            scanner, 
            propertyManager.getAllProperties(), 
            "Select property"
        );
        if (selectedProperty == null) { return; }

        String username = session.getCurrentUser().getUsername();

        if (!hasCompletedBooking(selectedProperty, username)) {
            System.out.println("You can only review properties you have stayed in.");
            return;
        }

        Review review = new Review();

        System.out.println("\nCreating new review");

        review.generateReviewID();
        review.setProperty(selectedProperty);
        review.setUsername(session.getCurrentUser().getUsername());
        review.setStars(
            Helpers.readIntInRange(
                scanner, 
                "Enter stars: ", 
                1, 
                5
            )
        );
        review.setTitle(
            Helpers.readString(
                scanner, 
                "Enter review title: ", 
                256
            )
        );
        review.setContent(
            Helpers.readString(
                scanner, 
                "Enter review content: ", 
                1024
            )
        );

        try {
            selectedProperty.addReview(review);
            System.out.println("Review created successfully.");
        } catch (IllegalStateException e) { System.out.println(e.getMessage()); }
    }

    public void listReviews() {
        List<Review> userReviews = reviewQueryService.getStudentReviews(session.getCurrentUser().getUsername());
        System.out.println("\nYour reviews:");
        Helpers.printIndexed(userReviews, Review::toString);
    }

    public void editReview() {
        List<Review> userReviews = getUserReviews();

        if (userReviews.isEmpty()) {
            System.out.println("You have no reviews to edit.");
            return;
        }

        listReviews(userReviews);

        Review choice = Helpers.selectFromList(
            scanner,
            userReviews,
            "Select a review to edit"
        );

        editReviewMenu(choice);
    }

    private void editReviewMenu(Review review) {
        while (true) {
            Property property = review.getProperty();
            String address = (property != null)
                ? property.getAddress() : "Unknown property";

            System.out.println(
                "\nEditing review: "
                + address + " - ("
                + review.getStars() + ")\n"
                + review.getTitle()
            );
            System.out.println("1. Star rating");
            System.out.println("2. Title");
            System.out.println("3. Content");
            System.out.println("4. Cancel");

            Integer choice = Helpers.readIntInRange(
                scanner, 
                "Choose a field to edit: ",
                1, 
                4
            );

            switch (choice) {
                case 1 -> review.setStars(
                    Helpers.readIntInRange(
                        scanner, 
                        "Enter stars: ",
                        1, 
                        5
                    )
                );
                case 2 -> review.setTitle(
                    Helpers.readString(
                        scanner, 
                        "Enter review title: ", 
                        256
                    )
                );
                case 3 -> review.setContent(Helpers.readString(scanner, "Enter review content: ", 1024));
                case 4 -> {
                    System.out.println("Edit cancelled.");
                    return;
                }
            }
        }
    }

    public void deleteReview() {
        List<Review> userReviews = getUserReviews();

        if (userReviews.isEmpty()) {
            System.out.println("You have no reviews to delete");
            return;
        }

        listReviews(userReviews);

        Review selectedReview = Helpers.selectFromList(
            scanner,
            userReviews,
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
