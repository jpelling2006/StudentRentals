public class Property {
    private Integer propertyID;
    private Integer userID; // only homeowners (duh)
    private String address;
    private String propertyType; // like uh house or flat
    private Integer bedrooms;
    private Integer bathrooms;

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

    public String getAddress() { return address; }
    public void setAddress(String address) {
        if (address == null || address.length() > 512) {
            throw new IllegalArgumentException("Address must be up to 512 characters long.");
        }
        this.address = address;
    }

    public String getPropertyType() { return propertyType; }
    public void setPropertyType(String propertyType) {
        if (propertyType == null || (!propertyType.equals("house") && !propertyType.equals("flat"))) {
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
}
