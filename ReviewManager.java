import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReviewManager {
    private List<Review> reviews = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private Session session;
    private PropertyManager propertyManager;

    public ReviewManager(PropertyManager propertyManager, Session session, Scanner scanner) {
        this.propertyManager = propertyManager;
        this.session = session;
        this.scanner = scanner;
    }

    // make a booking manager, but this works for now
    public void inputProperty(Review review) {
        List<Property> properties = propertyManager.getAllProperties();

        if (properties.isEmpty()) {
            System.out.println("No properties available to review.");
            return;
        }

        for (int i = 0; i < properties.size(); i++) {
            System.out.println(
                (i + 1) + ". "
                + properties.get(i).getAddress()
                + " ("
                + properties.get(i).getPropertyType()
                + ")"
            );
        }

        Integer choice;
        while (true) {
            System.out.print("Select property: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > properties.size()) {
                    System.out.println("Invalid selection.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }

        Property selectProperty = properties.get(choice - 1);
        review.setPropertyID(selectProperty.getPropertyID());
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

        Review review = new Review();

        System.out.println("\nCreating new review");

        review.generateReviewID();
        inputProperty(review);
        review.setUsername(session.getCurrentUser().getUsername());
        inputStars(review);
        inputTitle(review);
        inputContent(review);

        reviews.add(review);
        System.out.println("Review created succesfully.");
    }

    public List<Review> getUserReviews() {
        String username = session.getCurrentUser().getUsername();
        List<Review> userReviews = new ArrayList<>();

        for(Review review : reviews) {
            if (review.getUsername().equalsIgnoreCase(username)) {
                userReviews.add(review);
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
            Property property = propertyManager.getPropertyByID(review.getPropertyID());
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
            Property property = propertyManager.getPropertyByID(review.getPropertyID());
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

        reviews.remove(selectedReview);
        System.out.println("Room deleted successfully.");
    }

    private boolean confirmDeletion(Review review) {
        while (true) {
            System.out.println("\nAre you sure you want to delete this review?");

            Property property = propertyManager.getPropertyByID(review.getPropertyID());
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
