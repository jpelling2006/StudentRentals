package Properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import Review.Review;
import Room.Room;

public class Property {
    private UUID propertyID;
    private String username; // only homeowners
    private String city;
    private String address;
    public String description;
    private PropertyType propertyType;
    private Integer bedrooms;
    private Integer bathrooms;
    private List<Room> rooms = new ArrayList<>();
    private Map<String, Review> reviewsByUser = new HashMap<>();

    public UUID getPropertyID() { return propertyID; }
    public void generatePropertyID() {
        this.propertyID = UUID.randomUUID();
    }

    // if exists too
    public String getUsername() { return username; }
    public void setUsername(String username) {
        // add regex
        if (username == null || username.length() > 32) {
            throw new IllegalArgumentException(
                "Username must be up to 32 characters."
            );
        }
        this.username = username;
    }

    public String getCity() { return city; }
    public void setCity(String city) {
        if (city == null || city.length() > 64) {
            throw new IllegalArgumentException(
                "City must be up to 64 characters."
            );
        }
        this.city = city;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) {
        if (address == null || address.length() > 512) {
            throw new IllegalArgumentException(
                "Address must be up to 512 characters long."
            );
        }
        this.address = address;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        if (description == null || description.length() > 2048) {
            throw new IllegalArgumentException(
                "Description must be up to 2048 characters long."
            );
        }
        this.description = description;
    }

    public PropertyType getPropertyType() { return propertyType; }
    public void setPropertyType(PropertyType propertyType) {
        if (propertyType == null) {
            throw new IllegalArgumentException("Room type is required.");
        }
        this.propertyType = propertyType;
    }

    public Integer getBedrooms() { return bedrooms; }
    public void setBedrooms(Integer bedrooms) { 
        if (bedrooms == null || bedrooms <= 0) {
            throw new IllegalArgumentException(
                "Bedrooms must be a positive integer."
            );
        }
        this.bedrooms = bedrooms;
    }

    public Integer getBathrooms() { return bathrooms; }
    public void setBathrooms(Integer bathrooms) { 
        if (bathrooms == null || bathrooms <= 0) {
            throw new IllegalArgumentException(
                "Bathrooms must be a positive integer."
            );
        }
        this.bathrooms = bathrooms;
    }

    public List<Room> getRooms() { return rooms; }
    public void addRoom(Room room) { rooms.add(room); }
    public void removeRoom(Room room) { rooms.remove(room); }

    // reviews

    public Collection<Review> getReviews() { return reviewsByUser.values(); }

    public Review getReviewByUser(String username) {
        return reviewsByUser.get(username);
    }

    public boolean hasReviewFromUser(String username) {
        return reviewsByUser.containsKey(username);
    }

    public void addReview(Review review) {
        String username = review.getUsername();

        if (reviewsByUser.containsKey(username)) {
            throw new IllegalStateException(
                "User has already reviewed this property."
            );
        }

        reviewsByUser.put(username, review);
    }

    public void removeReview(String username) { reviewsByUser.remove(username); }

    public double getAverageRating() {
        if (reviewsByUser.isEmpty()) { return 0; }

        Integer sum = 0;
        for (Review review : reviewsByUser.values()) {
            sum += review.getStars();
        }

        return (double) sum / reviewsByUser.size();
    }
}
