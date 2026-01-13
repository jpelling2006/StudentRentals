package user;

public class AdministratorUser extends User 
// implements BookingAccess, PropertyAccess, RoomAccess, ReviewAccess
{
    // private final BookingHandler bookingHandler;
    // private final PropertiesHandler propertiesHandler;
    // private final RoomHandler roomHandler;
    // private final ReviewHandler reviewHandler;

    public AdministratorUser(
        String username,
        String email,
        String phone,
        String passwordHash
    ) throws Exception {
        super(username, email, phone, passwordHash);
    }

    // @Override
    // public BookingHandler getBookingHandler() { return bookingHandler; }

    // @Override
    // public PropertiesHandler getPropertiesHandler() { return propertiesHandler; }

    // @Override
    // public RoomHandler getRoomHandler() { return roomHandler; }

    // @Override
    // public ReviewHandler getReviewHandler() { return reviewHandler; }
}
