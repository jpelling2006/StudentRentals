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
        return propertyQueryService.getAllProperties().stream()
            .flatMap(property -> property.getReviews().stream())
            .toList();
    }

    public List<Review> getStudentReviews(String username) {
        return propertyQueryService.getAllProperties().stream()
            .flatMap(property -> property.getReviews().stream())
            .filter(review -> review.getUsername().equalsIgnoreCase(username))
            .toList();
    }

    public List<Review> getHomeownerReviews(User user) {
        return propertyQueryService.getUserProperties(user).stream()
            .flatMap(property -> property.getReviews().stream())
            .toList();
    }
}
