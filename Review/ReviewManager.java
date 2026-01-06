package Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Helpers.*;
import Properties.*;
import Room.Room;
import FrontEnd.Session;

public class ReviewManager {
    private Scanner scanner = new Scanner(System.in);
    private Session session;
    private PropertyManager propertyManager;

    public ReviewManager(PropertyManager propertyManager, Session session, Scanner scanner) {
        this.propertyManager = propertyManager;
        this.session = session;
        this.scanner = scanner;
    }

    public Property inputProperty() {
        List<Property> properties = propertyManager.getAllProperties();
        if (properties.isEmpty()) { return null; }

        for (int i = 0; i < properties.size(); i++) {
            System.out.println((i + 1) + ". " + properties.get(i).getAddress());
        }

        int choice = Helpers.selectFromList(scanner, properties.size(), "Select property");
        return properties.get(choice - 1);
    }

    public void inputStars(Review review) {
        while (true) {
            Integer stars = Helpers.readInt(scanner, "Star rating (1-5): ");

            try {
                review.setStars(stars);
                System.out.println("Star rating set.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void inputTitle(Review review) {
        while (true) {
            System.out.print("Enter review title: ");
            String title = scanner.nextLine();

            try {
                review.setTitle(title);
                System.out.println("Review title set.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void inputContent(Review review) {
        while (true) {
            System.out.print("Enter review content: ");
            String content = scanner.nextLine();

            try {
                review.setContent(content);
                System.out.println("Review content set.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void newReview() {
        if (!session.getCurrentUser().getUserType().equals("student")) {
            System.out.println("Only students can post reviews");
            return;
        }

        Property property = inputProperty();
        if (property == null) { return; }

        boolean eligible = false;

        for (Room room : property.getRooms()) {
            if (room.completedBookingUser(session.getCurrentUser().getUsername())) {
                eligible = true;
                break;
            }
        }

        if (!eligible) {
            System.out.println("You can only review properties you have stayed in.");
            return;
        }

        Review review = new Review();

        System.out.println("\nCreating new review");

        review.generateReviewID();
        review.setProperty(property);
        review.setUsername(session.getCurrentUser().getUsername());
        inputStars(review);
        inputTitle(review);
        inputContent(review);

        property.addReview(review);
        System.out.println("Review created succesfully.");
    }

    public List<Review> getUserReviews() {
        String username = session.getCurrentUser().getUsername();
        List<Review> userReviews = new ArrayList<>();

        for(Property property : propertyManager.getAllProperties()) {
            for (Review review : property.getReviews()) {
                if (review.getUsername().equalsIgnoreCase(username)) { userReviews.add(review); }
            }
        }

        return userReviews;
    }

    public void listReviews(List<Review> userReviews) {
        if (userReviews.isEmpty()) {
            System.out.println("You have no reviews.");
            return;
        }

        System.out.println("\nYour reviews:");
        for (int i = 0; i < userReviews.size(); i++) {
            Review review = userReviews.get(i);
            Property property = review.getProperty();
            String address = (property != null) ? property.getAddress() : "Unknown property";
            System.out.println(
                (i + 1) + ". "
                + address + " - ("
                + review.getStars() + ")\n"
                + review.getTitle()
            );
        }
    }

    public void editReview() {
        List<Review> userReviews = getUserReviews();

        if (userReviews.isEmpty()) {
            System.out.println("You have no reviews to edit.");
            return;
        }

        listReviews(userReviews);

        int choice = Helpers.selectFromList(scanner, userReviews.size(), "Select a review to edit");

        editReviewMenu(userReviews.get(choice - 1));
    }

    private void editReviewMenu(Review review) {
        while (true) {
            Property property = review.getProperty();
            String address = (property != null) ? property.getAddress() : "Unknown property";

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

            Integer choice = Helpers.readInt(scanner, "Choose a field to edit: ");

            switch (choice) {
                case 1 -> inputStars(review);
                case 2 -> inputTitle(review);
                case 3 -> inputContent(review);
                case 4 -> {
                    System.out.println("Edit cancelled.");
                    return;
                }
                default -> System.out.println("Invalid option.");
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

        int choice = Helpers.selectFromList(scanner, userReviews.size(), "Select a review to delete");

        Review selectedReview = userReviews.get(choice - 1);

        if (!confirmDeletion(selectedReview)) {
            System.out.println("Deletion cancelled");
            return;
        }

        reviews.remove(selectedReview); // get to this
        System.out.println("Room deleted successfully.");
    }

    private boolean confirmDeletion(Review review) {
        while (true) {
            System.out.println("\nAre you sure you want to delete this review?");

            Property property = review.getProperty();
            String address = (property != null) ? property.getAddress() : "Unknown property";

            System.out.println(
                address + " - ("
                + review.getStars() + ")\n"
                + review.getTitle()
            );

            return Helpers.confirm(scanner);
        }
    }

    public void start() {
        while (true) {
            System.out.println("\n Review Management System");
            System.out.println("1. Create review");
            System.out.println("2. List reviews");
            System.out.println("3. Edit reviews");
            System.out.println("4. Delete reviews");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    newReview();
                    break;
                case 2:
                    listReviews(reviews);
                    break;
                case 3:
                    editReview();
                    break;
                case 4:
                    deleteReview();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please select an integer between 1-5.");
                    break;
            }
        }
    }
}
