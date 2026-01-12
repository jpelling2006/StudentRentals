package user;

import java.util.ArrayList;
import java.util.List;

import properties.Property;

public class HomeownerUser extends User
// implements BookingAccess, PropertyAccess, RoomAccess, ReviewAccess
{
    // private final BookingHandler bookingHandler;
    // private final PropertiesHandler propertiesHandler;
    // private final RoomHandler roomHandler;
    // private final ReviewHandler reviewHandler;

    private final List<Property> properties = new ArrayList<>();

    public HomeownerUser(
        String username,
        String email,
        String phone,
        String passwordHash
    ) throws Exception {
        super(username, email, phone, passwordHash);
    }


    public List<Property> getProperties() { return properties; }
    public void addProperty(Property property) { properties.add(property); }
    public void removeProperty(Property property) { properties.remove(property); }

    // @Override
    // public BookingHandler getBookingHandler() { return bookingHandler; }

    // @Override
    // public PropertiesHandler getPropertiesHandler() { return propertiesHandler; }

    // @Override
    // public RoomHandler getRoomHandler() { return roomHandler; }

    // @Override
    // public ReviewHandler getReviewHandler() { return reviewHandler; }
}
