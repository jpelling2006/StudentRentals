package review;

import java.util.List;

import properties.PropertyQueryService;
import user.User;

public final class ReviewQueryService {
    private static ReviewQueryService instance;

    public static ReviewQueryService getInstance() {
        if (instance == null) { instance = new ReviewQueryService(); }
        return new ReviewQueryService();
    }
    
    private ReviewQueryService() {}

    public static List<Review> getAllReviews() {
        return PropertyQueryService.getAllProperties().stream() // gets all properties
            .flatMap(property -> property.getReviews().stream()) // gets all reviews for properties
            .toList();
    }

    public static List<Review> getStudentReviews(String username) {
        return PropertyQueryService.getAllProperties().stream() // gets all properties
            .flatMap(property -> property.getReviews().stream()) // gets all reviews for properties
            .filter(review -> review.getUsername().equalsIgnoreCase(username)) // gets all reviews made by student
            .toList();
    }

    public static List<Review> getHomeownerReviews(User user) {
        return PropertyQueryService.getUserProperties(user).stream() // gets properties made by user
            .flatMap(property -> property.getReviews().stream()) // gets all reviews on a user's properties
            .toList();
    }
}
