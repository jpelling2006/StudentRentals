package user;

import booking.BookingHandler;
import properties.PropertiesHandler;
import properties.Property;
import room.RoomHandler;
import review.ReviewHandler;
import java.util.ArrayList;
import java.util.List;

import access.BookingAccess;
import access.PropertyAccess;
import access.ReviewAccess;
import access.RoomAccess;

public class HomeownerUser extends User implements BookingAccess, PropertyAccess, RoomAccess, ReviewAccess {
    private final BookingHandler bookingHandler;
    private final PropertiesHandler propertiesHandler;
    private final RoomHandler roomHandler;
    private final ReviewHandler reviewHandler;

    private final List<Property> properties = new ArrayList<>();

    public HomeownerUser(
        String username,
        String email,
        String phone,
        String passwordHash,
        BookingHandler bookingHandler,
        PropertiesHandler propertiesHandler,
        RoomHandler roomHandler,
        ReviewHandler reviewHandler
    ) throws Exception {
        super(username, email, phone, passwordHash);
        this.bookingHandler = bookingHandler;
        this.propertiesHandler = propertiesHandler;
        this.roomHandler = roomHandler;
        this.reviewHandler = reviewHandler;
    }


    public List<Property> getProperties() { return properties; }
    public void addProperty(Property property) { properties.add(property); }
    public void removeProperty(Property property) { properties.remove(property); }

    @Override
    public BookingHandler getBookingHandler() { return bookingHandler; }

    @Override
    public PropertiesHandler getPropertiesHandler() { return propertiesHandler; }

    @Override
    public RoomHandler getRoomHandler() { return roomHandler; }

    @Override
    public ReviewHandler getReviewHandler() { return reviewHandler; }
}
