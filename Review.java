public class Review {
    private Integer ID;
    private Integer propertyID; // this makes more sense icl
    private Integer userID; // students only!! :3
    private Integer stars; // (LIKE IN TIME??) 1-5, should also calculate average or wtv
    private String title;
    private String content;

    public Integer getID() { return ID; }
    public void setID(Integer ID) { this.ID = ID; }

    public Integer getPropertyID() { return propertyID; }
    public void setPropertyID(Integer propertyID) { this.propertyID = propertyID; }

    public Integer getUserID() { return userID; }
    public void setUserID(Integer userID) { this.userID = userID; }

    public Integer getStars() { return stars; }
    public void setStars(Integer stars) { this.stars = stars; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
