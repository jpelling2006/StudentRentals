package review;

import java.util.UUID;

import properties.Property;

public class Review {
    private UUID reviewID;
    private Property property;
    private String username;
    private Integer stars;
    private String title;
    private String content;

    public Review(
        Property property,
        String username,
        Integer stars,
        String title,
        String content
    ) throws Exception {
        generateReviewID();
        setProperty(property);
        setUsername(username);
        setStars(stars);
        setTitle(title);
        setContent(content);
        property.addReview(this);
    }

    public UUID getReviewID() { return reviewID; }
    public void generateReviewID() {
        this.reviewID = UUID.randomUUID();
    }

    public Property getProperty() { return property; }
    public void setProperty(Property property) {
        if (property == null) {
            throw new IllegalArgumentException("Property required.");
        }
        this.property = property;
    }

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

    public Integer getStars() { return stars; }
    public void setStars(Integer stars) {
        if (stars == null || stars < 1 || stars > 5) {
            throw new IllegalArgumentException(
                "Stars must be an integer between 1 and 5."
            );
        }
        this.stars = stars;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) {
        if (title != null && title.length() > 256) {
            throw new IllegalArgumentException(
                "Title must be up to 256 characters long."
            );
        }
        this.title = title;
    }

    public String getContent() { return content; }
    public void setContent(String content) {
        if (content != null && content.length() > 1024) {
            throw new IllegalArgumentException(
                "Content must be up to 1024 characters long."
            );
        }
        this.content = content;
    }

    @Override
    public String toString() {
        Property property = getProperty();

        // if null, return "unknown property"
        String address = (property != null)
            ? property.getAddress()
            : "Unknown property";

        // formats string
        return address + " - (" + stars + ")\n" + title + "\n" + content;
    }
}
