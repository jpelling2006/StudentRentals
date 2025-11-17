public class ReviewController {
    private Review model;
    private ReviewView view;

    public ReviewController(Review model, ReviewView view) {
        this.model = model;
        this.view = view;
    }

    public Integer getReviewID() { return model.getID(); }
    public void setReviewID(Integer ID) { model.setID(ID); }

    public Integer getReviewPropertyID() { return model.getPropertyID(); }
    public void setReviewPropertyID(Integer propertyID) { model.setPropertyID(propertyID); }

    public Integer getReviewUserID() { return model.getUserID(); }
    public void setReviewUserID(Integer userID) { model.setUserID(userID); }

    public Integer getReviewStars() { return model.getStars(); }
    public void setReviewStars(Integer stars) { model.setStars(stars); }

    public String getReviewTitle() { return model.getTitle(); }
    public void setReviewTitle(String title) { model.setTitle(title); }

    public String getReviewContent() { return model.getContent(); }
    public void setReviewContent(String content) { model.setContent(content); }
}
