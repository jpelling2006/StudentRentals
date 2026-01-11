package user;

import java.util.ArrayList;
import java.util.List;

import access.BookingAccess;
import access.PropertyAccess;
import access.ReviewAccess;
import access.RoomAccess;
import booking.HomeownerBookingManager;
import properties.Property;
import properties.PropertyManager;
import review.ReviewManager;
import room.RoomManager;

public class HomeownerUser extends User implements BookingAccess, PropertyAccess, RoomAccess, ReviewAccess {
    @Override
    public UserType getUserType() { return UserType.HOMEOWNER; }

    private List<Property> properties = new ArrayList<>();

    private final HomeownerBookingManager bookingManager;
    private final PropertyManager propertyManager;
    private final RoomManager roomManager;
    private final ReviewManager reviewManager;

    public List<Property> getProperties() { return properties; }
    public void addProperty(Property property) { properties.add(property); }
    public void removeProperty(Property property) { properties.remove(property); }

    @Override
    public boolean handleBooking() {
        return bookingManager.handleOnce();
    }

    @Override
    public boolean handleProperty() {
        return propertyManager.handleOnce();
    }

    @Override
    public boolean handleRoom() {
        return roomManager.handleOnce();
    }

    @Override
    public boolean handleReview() {
        return reviewManager.handleOnce();
    }
}
