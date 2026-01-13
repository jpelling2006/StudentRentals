package review;

import java.util.List;
import java.util.Scanner;

import helpers.Helpers;
import properties.Property;
import properties.PropertyQueryService;
import room.Room;
import session.Session;

public final class StudentReviewManager implements ReviewHandler {
    private final static Scanner scanner = new Scanner(System.in);
    private static StudentReviewManager instance;

    public static StudentReviewManager getInstance() {
        if (instance == null) { instance = new StudentReviewManager(); }
        return new StudentReviewManager();
    }

    private StudentReviewManager() {}

    private static boolean hasCompletedBooking(Property property, String username) {
        for (Room room : property.getRooms()) {
            if (room.bookingCompletedByUser(username)) { return true; }
        }
        return false;
    }

    private static void createReview() {
        Property selectedProperty = Helpers.selectFromList(
            scanner, 
            PropertyQueryService.getAllProperties(), 
            "Select property",
            Property::toString
        );

        if (selectedProperty == null) { return; }

        String username = Session.getCurrentUser().getUsername();

        if (!hasCompletedBooking(selectedProperty, username)) {
            System.out.println(
                "You can only review properties you have stayed in."
            );
            return;
        }

        try {
            Integer stars = Helpers.readIntInRange(
                scanner, "Enter stars: ", 1, 5
            );
            String title = Helpers.readString(
                scanner, "Enter review title: ", 256
            );
            String content = Helpers.readString(
                scanner, "Enter review content: ", 1024
            );

            Review review = new Review(
                selectedProperty, username, stars, title, content
            );

            // add review to property
            selectedProperty.addReview(review);

            System.out.println("Review created successfully.");
        } catch (Exception e) {
            System.out.println("Failed to create review: " + e.getMessage());
        }
    }


    private static void listReviews() {
        List<Review> userReviews = ReviewQueryService.getStudentReviews(
            Session.getCurrentUser().getUsername()
        );

        if (userReviews.isEmpty()) {
            System.out.println("You have no reviews.");
            return;
        }

        // prints list of reviews made by user
        System.out.println("\nYour reviews:");
        Helpers.printIndexed(userReviews, Review::toString);
    }

    private static void editReview() {
        List<Review> userReviews = ReviewQueryService.getStudentReviews(
            Session.getCurrentUser().getUsername()
        );

        if (userReviews.isEmpty()) {
            System.out.println("You have no reviews to edit.");
            return;
        }

        listReviews();

        editReviewMenu(
            Helpers.selectFromList(
                scanner,
                userReviews,
                "Select a review to edit",
                Review::toString
            )
        );
    }

    private static void editReviewMenu(Review review) {
        while (true) {
            System.out.println( "\nEditing review: ");
            System.out.println(review.toString());
            System.out.println("1. Star rating");
            System.out.println("2. Title");
            System.out.println("3. Content");
            System.out.println("4. Cancel");

            switch (
                Helpers.readIntInRange(
                    scanner, 
                    "Choose a field to edit: ",
                    1, 
                    4
                )
            ) {
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
                case 3 -> review.setContent(
                    Helpers.readString(
                        scanner, 
                        "Enter review content: ", 
                        1024
                    )
                );
                case 4 -> {
                    System.out.println("Edit cancelled.");
                    return;
                }
            }
        }
    }

    public static void deleteReview() {
        List<Review> userReviews = ReviewQueryService.getStudentReviews(
            Session.getCurrentUser().getUsername()
        );

        if (userReviews.isEmpty()) {
            System.out.println("You have no reviews to delete");
            return;
        }

        listReviews();

        // user selects review from list
        Review selectedReview = Helpers.selectFromList(
            scanner,
            userReviews,
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
        boolean running = true;
        while (running) {
            System.out.println("\nStudent Review Menu");
            System.out.println("1. Create review");
            System.out.println("2. View reviews");
            System.out.println("3. Edit review");
            System.out.println("4. Delete review");
            System.out.println("5. Back");

            switch (
                Helpers.readIntInRange(
                    scanner, 
                    "Choose option: ", 
                    1, 
                    5
                )
            ) {
                case 1 -> createReview();
                case 2 -> listReviews();
                case 3 -> editReview();
                case 4 -> deleteReview();
                case 5 -> running = false;
            }
        }
        return true;
    }
}