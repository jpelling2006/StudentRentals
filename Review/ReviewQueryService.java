package review;

import java.util.ArrayList;
import java.util.List;

import properties.Property;
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
        List<Review> reviews = new ArrayList<>();

        for(Property property : propertyQueryService.getAllProperties()) {
            for (Review review : property.getReviews()) { reviews.add(review); }
        }

        return reviews;
    }

    public List<Review> getStudentReviews(String username) {
        List<Review> userReviews = new ArrayList<>();

        for(Property property : propertyQueryService.getAllProperties()) {
            for (Review review : property.getReviews()) {
                if (review.getUsername().equalsIgnoreCase(username)) {
                    userReviews.add(review);
                }
            }
        }
        return userReviews;
    }

    public List<Review> getHomeownerReviews(User user) {
        List<Review> propertiesReviews = new ArrayList<>();
        
        for (Property property : propertyQueryService.getUserProperties(user)) {
            for (Review review : property.getReviews()) {
                propertiesReviews.add(review);
            }
        }

        return propertiesReviews;
    }
}
