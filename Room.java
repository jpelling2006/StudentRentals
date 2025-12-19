import java.util.*;

public class Room {
    private Integer roomID;
    private Integer propertyID;
    private String roomType;
    private Double rentPrice;
    private Boolean billsIncluded;
    private String location; // as in where it is in the house, what floor etc
    private String amenities;
    private Date startDate;
    private Date endDate;

    public Integer getID() { return roomID; }
    public void setID(Integer roomID) {
        if (roomID == null || roomID <= 0) {
            throw new IllegalArgumentException("RoomID must be a positive integer.");
        }
        this.roomID = roomID;
    }

    public Integer getPropertyID() { return propertyID; }
    public void setPropertyID(Integer propertyID) {
        if (propertyID == null || propertyID <= 0) {
            throw new IllegalArgumentException("PropertyID must be a positive integer.");
        }
        this.propertyID = propertyID;
    }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) {
        if (roomType == null || (!roomType.equals("single") && !roomType.equals("double"))) {
            throw new IllegalArgumentException("Room must be one of the following types: single, double");
        }
        this.roomType = roomType;
    }

    public Double getRentPrice() { return rentPrice; }
    public void setRentPrice(Double rentPrice) {
        if (rentPrice == null || rentPrice <= 0) {
            throw new IllegalArgumentException("RentPrice must be a positive value.");
        }
        this.rentPrice = rentPrice;
    }

    public Boolean getBillsIncluded() { return billsIncluded; }
    public void setBillsIncluded(Boolean billsIncluded) {
        if (billsIncluded == null) {
            throw new IllegalArgumentException("BillsIncluded must be either True or False.");
        }
        this.billsIncluded = billsIncluded;
    }

    public String getLocation() { return location; }
    public void setLocation(String location) {
        if (location == null || location.length() > 64) {
            throw new IllegalArgumentException("Location must be up to 64 characters long.");
        }
        this.location = location;
    }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) {
        if (amenities != null && amenities.length() > 256) {
            throw new IllegalArgumentException("Amenities must be up to 256 characters long.");
        }
        this.amenities = amenities;
    }

    // check if dates have passed
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
}