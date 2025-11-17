import java.util.*;

public class RoomController {
    private Room model;
    private RoomView view;

    public RoomController(Room model, RoomView view) {
        this.model = model;
        this.view = view;
    }

    public Integer getRoomID() { return model.getID(); }
    public void setRoomID(Integer ID) { model.setID(ID); }

    public Integer getRoomPropertyID() { return model.getPropertyID(); }
    public void setRoomPropertyID(Integer propertyID) { model.setPropertyID(propertyID); }
    
    public String getRoomType() { return model.getType(); }
    public void setRoomType(String type) { model.setType(type); }

    public Double getRoomRentPrice() { return model.getRentPrice(); }
    public void setRoomRentPrice(Double rentPrice) { model.setRentPrice(rentPrice); }

    public Boolean getRoomBillsIncluded() { return model.getBillsIncluded(); }
    public void setRoomBillsIncluded(Boolean billsIncluded) { model.setBillsIncluded(billsIncluded); }

    public String getRoomLocation() { return model.getLocation(); }
    public void setRoomLocation(String location) { model.setLocation(location); }

    public String getRoomAmenities() { return model.getAmenities(); }
    public void setRoomAmenities(String amenities) { model.setAmenities(amenities); }

    public Date getRoomStartDate() { return model.getStartDate(); }
    public void setRoomStartDate(Date startDate) { model.setStartDate(startDate); }

    public Date getRoomEndDate() { return model.getEndDate(); }
    public void setRoomEndDate(Date endDate) { model.setEndDate(endDate); }
}
