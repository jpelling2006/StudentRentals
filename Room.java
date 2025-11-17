import java.util.*;

public class Room {
    private Integer ID;
    private Integer propertyID;
    private String type;
    private Double rentPrice;
    private Boolean billsIncluded;
    private String location; // as in where it is in the house, what floor etc
    private String amenities;
    private Date startDate;
    private Date endDate;
    // photos? *scratches head*

    public Integer getID() { return ID; }
    public void setID(Integer ID) { this.ID = ID; }

    public Integer getPropertyID() { return propertyID; }
    public void setPropertyID(Integer propertyID) { this.propertyID = propertyID; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Double getRentPrice() { return rentPrice; }
    public void setRentPrice(Double rentPrice) { this.rentPrice = rentPrice; }

    public Boolean getBillsIncluded() { return billsIncluded; }
    public void setBillsIncluded(Boolean billsIncluded) { this.billsIncluded = billsIncluded; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
}