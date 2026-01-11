package user;

import access.BookingAccess;
import access.PropertyAccess;
import access.ReviewAccess;
import access.RoomAccess;
import booking.BookingHandler;
import properties.PropertiesHandler;
import review.ReviewHandler;
import room.RoomHandler;

public class AdministratorUser extends User implements BookingAccess, PropertyAccess, RoomAccess, ReviewAccess {
    private final BookingHandler bookingHandler;
    private final PropertiesHandler propertiesHandler;
    private final RoomHandler roomHandler;
    private final ReviewHandler reviewHandler;

    public AdministratorUser(
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

    @Override
    public BookingHandler getBookingHandler() { return bookingHandler; }

    @Override
    public PropertiesHandler getPropertiesHandler() { return propertiesHandler; }

    @Override
    public RoomHandler getRoomHandler() { return roomHandler; }

    @Override
    public ReviewHandler getReviewHandler() { return reviewHandler; }
}
