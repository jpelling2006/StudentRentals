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

        if ( // if user hasn't completed booking on selected property
            !hasCompletedBooking(
                selectedProperty, 
                Session.getCurrentUser().getUsername()
            )
        ) {
            System.out.println(
                "You can only review properties you have stayed in."
            );
            return;
        }

        Review review = new Review();

        System.out.println("\nCreating new review");

        // automatically filled fields
        review.generateReviewID();
        review.setProperty(selectedProperty);
        review.setUsername(Session.getCurrentUser().getUsername());

        // user inputted fields
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
        System.out.println("\nStudent Review Menu");
        System.out.println("1. Create review");
        System.out.println("2. View reviews");
        System.out.println("3. Edit review");
        System.out.println("4. Delete review");
        System.out.println("5. Back");

        return switch (
            Helpers.readIntInRange(
                scanner, 
                "Choose option: ", 
                1, 
                5
            )
        ) {
            case 1 -> {
                createReview();
                yield false;
            }
            case 2 -> {
                listReviews();
                yield false;
            }
            case 3 -> {
                editReview();
                yield false;
            }
            case 4 -> {
                deleteReview();
                yield false;
            }
            case 5 -> true;
            default -> false;
        };
    }
}