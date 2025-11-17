public class Property {
    private Integer propertyID;
    private Integer userID; // only homeowners (duh)
    private String address;
    private String type; // like uh house or flat
    private Integer bedrooms;
    private Integer bathrooms;

    public Integer getPropertyID() { return propertyID; }
    public void setPropertyID(Integer propertyID) { this.propertyID = propertyID; }

    public Integer getUserID() { return userID; }
    public void setUserID(Integer userID) { this.userID = userID; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getBedrooms() { return bedrooms; }
    public void setBedrooms(Integer bedrooms) { this.bedrooms = bedrooms; }

    public Integer getBathrooms() { return bathrooms; }
    public void setBathrooms(Integer bathrooms) { this.bathrooms = bathrooms; }
}
