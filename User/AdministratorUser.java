package user;

import access.BookingAccess;
import access.PropertyAccess;
import access.ReviewAccess;
import access.RoomAccess;
import booking.AdminBookingManager;
import properties.PropertyManager;
import review.ReviewManager;
import room.RoomManager;

public class AdministratorUser extends User implements BookingAccess, PropertyAccess, RoomAccess, ReviewAccess {
    @Override
    public UserType getUserType() { return UserType.ADMINISTRATOR; }

    private final AdminBookingManager bookingManager;
    private final PropertyManager propertyManager;
    private final RoomManager roomManager;
    private final ReviewManager reviewManager;

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
