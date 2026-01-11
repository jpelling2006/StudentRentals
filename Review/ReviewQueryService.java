package review;

import java.util.List;

import properties.PropertyQueryService;
import user.User;

public class ReviewQueryService {
    private final PropertyQueryService propertyQueryService;
    
    public ReviewQueryService(
        PropertyQueryService propertyQueryService
    ) {
        this.propertyQueryService = propertyQueryService;
    }

    public List<Review> getAllReviews() {
        return propertyQueryService.getAllProperties().stream() // gets all properties
            .flatMap(property -> property.getReviews().stream()) // gets all reviews for properties
            .toList();
    }

    public List<Review> getStudentReviews(String username) {
        return propertyQueryService.getAllProperties().stream() // gets all properties
            .flatMap(property -> property.getReviews().stream()) // gets all reviews for properties
            .filter(review -> review.getUsername().equalsIgnoreCase(username)) // gets all reviews made by student
            .toList();
    }

    public List<Review> getHomeownerReviews(User user) {
        return propertyQueryService.getUserProperties(user).stream() // gets properties made by user
            .flatMap(property -> property.getReviews().stream()) // gets all reviews on a user's properties
            .toList();
    }
}
