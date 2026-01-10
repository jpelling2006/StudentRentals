package Review;

import java.util.ArrayList;
import java.util.List;

import Properties.Property;
import Properties.PropertyQueryService;

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

    public List<Review> getHomeownerReviews(String username) {
        List<Review> propertiesReviews = new ArrayList<>();
        
        for (Property property : propertyQueryService.getUserProperties(username)) {
            for (Review review : property.getReviews()) {
                propertiesReviews.add(review);
            }
        }

        return propertiesReviews;
    }
}
