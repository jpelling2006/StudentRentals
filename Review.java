public class Review {
    private Integer reviewID;
    private Integer propertyID; // this makes more sense icl
    private Integer userID; // students only!! :3
    private Integer stars; // (LIKE IN TIME??) 1-5, should also calculate average or wtv
    private String title;
    private String content;

    public Integer getReviewID() { return reviewID; }
    public void setReviewID(Integer reviewID) { 
        if (reviewID == null || reviewID <= 0) {
            throw new IllegalArgumentException("ReviewID must be a positive integer.");
        }
        this.reviewID = reviewID;
    }

    public Integer getPropertyID() { return propertyID; }
    public void setPropertyID(Integer propertyID) {
        if (propertyID == null || propertyID <= 0) {
            throw new IllegalArgumentException("PropertyID must be a positive integer.");
        }
        this.propertyID = propertyID;
    }

    public Integer getUserID() { return userID; }
    public void setUserID(Integer userID) {
        if (userID == null || userID <= 0) {
            throw new IllegalArgumentException("UserID must be a positive integer.");
        }
        this.userID = userID;
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
