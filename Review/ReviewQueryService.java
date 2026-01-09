package Review;

import java.util.ArrayList;
import java.util.List;

import FrontEnd.Session;
import Properties.Property;
import Properties.PropertyManager;

public class ReviewQueryService {
    private final PropertyManager propertyManager;
    private final Session session;
    
    public ReviewQueryService(
        PropertyManager propertyManager,
        Session session
    ) {
        this.propertyManager = propertyManager;
        this.session = session;
    }

    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();

        for(Property property : propertyManager.getAllProperties()) {
            for (Review review : property.getReviews()) { reviews.add(review); }
        }

        return reviews;
    }

    public List<Review> getStudentReviews(String username) {
        List<Review> userReviews = new ArrayList<>();

        for(Property property : propertyManager.getAllProperties()) {
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
        
        for (Property property : propertyManager.getUserProperties(username)) {
            for (Review review : property.getReviews()) {
                propertiesReviews.add(review);
            }
        }

        return propertiesReviews;
    }
}
