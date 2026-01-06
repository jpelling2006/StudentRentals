package Review;

import java.util.concurrent.atomic.AtomicInteger;

public class Review {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);

    private Integer reviewID;
    private Integer propertyID; // this makes more sense icl
    private String username;
    private Integer stars; // (LIKE IN TIME??) 1-5, should also calculate average or wtv
    private String title;
    private String content;

    public Integer getReviewID() { return reviewID; }
    private void setReviewID(Integer reviewID) { 
        this.reviewID = idGenerator.getAndIncrement();
    }
    public void generateReviewID() { setReviewID(reviewID); }

    public Integer getPropertyID() { return propertyID; }
    public void setPropertyID(Integer propertyID) {
        if (propertyID == null || propertyID <= 0) {
            throw new IllegalArgumentException("PropertyID must be a positive integer.");
        }
        this.propertyID = propertyID;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) {
        // add regex
        if (username == null || username.length() > 32) {
            throw new IllegalArgumentException("Username must be up to 32 characters.");
        }
        this.username = username;
    }

    public Integer getStars() { return stars; }
    public void setStars(Integer stars) {
        if (stars == null || stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Stars must be an integer between 1 and 5.");
        }
        this.stars = stars;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) {
        if (title != null && title.length() > 256) {
            throw new IllegalArgumentException("Title must be up to 256 characters long.");
        }
        this.title = title;
    }

    public String getContent() { return content; }
    public void setContent(String content) {
        if (content != null && content.length() > 1024) {
            throw new IllegalArgumentException("Content must be up to 1024 characters long.");
        }
        this.content = content;
    }
}
