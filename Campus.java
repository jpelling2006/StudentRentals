public class Campus {
    private Integer campusID;
    private String campusName;
    private String location;

    public Integer getCampusID() { return campusID; }
    public void setCampusID(Integer campusID) {
        if (campusID == null || campusID <= 0) {
            throw new IllegalArgumentException("CampusID must be a positive integer.");
        }
        this.campusID = campusID;
    }

    public String getCampusName() { return campusName; }
    public void setCampusName(String campusName) {
        if (campusName == null || campusName.length() > 128) {
            throw new IllegalArgumentException("Campus name must be up to 128 characters long.");
        }
    }
    
    public String getLocation() { return location; }
    public void setLocation(String location) {
        if (location == null || location.length() > 512) {
            throw new IllegalArgumentException("Location must be up to 512 characters long.");
        }
    }
}
