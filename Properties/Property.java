package Properties;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import Review.Review;
import Room.Room;

public class Property {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);

    private Integer propertyID;
    private String username; // only homeowners
    private String city;
    private String address;
    public String description;
    private String propertyType;
    private Integer bedrooms;
    private Integer bathrooms;
    private List<Room> rooms = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();

    public Integer getPropertyID() { return propertyID; }
    public void generatePropertyID() {
        this.propertyID = idGenerator.getAndIncrement();
    }

    // if exists too
    public String getUsername() { return username; }
    public void setUsername(String username) {
        // add regex
        if (username == null || username.length() > 32) {
            throw new IllegalArgumentException("Username must be up to 32 characters.");
        }
        this.username = username;
    }

    public String getCity() { return city; }
    public void setCity(String city) {
        if (city == null || city.length() > 64) {
            throw new IllegalArgumentException("City must be up to 64 characters.");
        }
        this.city = city;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) {
        if (address == null || address.length() > 512) {
            throw new IllegalArgumentException("Address must be up to 512 characters long.");
        }
        this.address = address;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        if (description == null || description.length() > 2048) {
            throw new IllegalArgumentException("Description must be up to 2048 characters long.");
        }
        this.description = description;
    }

    public String getPropertyType() { return propertyType; }
    public void setPropertyType(String propertyType) {
        if (
            propertyType == null
            || (!propertyType.equals("house")
            && !propertyType.equals("flat"))
        ) {
            throw new IllegalArgumentException("Property must be one of the following types: house, flat");
        }
        this.propertyType = propertyType;
    }

    public Integer getBedrooms() { return bedrooms; }
    public void setBedrooms(Integer bedrooms) { 
        if (bedrooms == null || bedrooms <= 0) {
            throw new IllegalArgumentException("Bedrooms must be a positive integer.");
        }
        this.bedrooms = bedrooms;
    }

    public Integer getBathrooms() { return bathrooms; }
    public void setBathrooms(Integer bathrooms) { 
        if (bathrooms == null || bathrooms <= 0) {
            throw new IllegalArgumentException("Bathrooms must be a positive integer.");
        }
        this.bathrooms = bathrooms;
    }

    public List<Room> getRooms() { return rooms; }
    public void addRoom(Room room) { rooms.add(room); }
    public void removeRoom(Room room) { rooms.remove(room); }

    public List<Review> getReviews() { return reviews; }
    public void addReview(Review review) { reviews.add(review); }
    public void removeReview(Review review) { reviews.remove(review); }

    public double getAverageRating() {
        if (reviews.isEmpty()) { return 0; }

        Integer sum = 0;
        for (Review review : reviews) {
            sum += review.getStars();
        }

        return (double) sum / reviews.size();
    }

    public Property() {}
}
