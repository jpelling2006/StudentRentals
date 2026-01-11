package user;

import booking.BookingHandler;
import review.ReviewHandler;
import properties.PropertiesHandler;
import room.RoomHandler;

public final class HomeownerUserFactory extends UserFactory {
    private static HomeownerUserFactory INSTANCE;

    private final BookingHandler bookingHandler;
    private final PropertiesHandler propertiesHandler;
    private final RoomHandler roomHandler;
    private final ReviewHandler reviewHandler;

    private HomeownerUserFactory(
        BookingHandler bookingHandler,
        PropertiesHandler propertiesHandler,
        RoomHandler roomHandler,
        ReviewHandler reviewHandler
    ) {
        this.bookingHandler = bookingHandler;
        this.propertiesHandler = propertiesHandler;
        this.roomHandler = roomHandler;
        this.reviewHandler = reviewHandler;
    }

    public static HomeownerUserFactory init(
        BookingHandler bookingHandler,
        PropertiesHandler propertiesHandler,
        RoomHandler roomHandler,
        ReviewHandler reviewHandler
    ) {
        if (INSTANCE == null) {
            INSTANCE = new HomeownerUserFactory(
                bookingHandler,
                propertiesHandler,
                roomHandler,
                reviewHandler
            );
        }
        return INSTANCE;
    }

    public static HomeownerUserFactory getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("HomeownerUserFactory not initialised");
        }
        return INSTANCE;
    }

    @Override
    public User createUser(
        String username,
        String email,
        String phone,
        String passwordHash,
        String university,
        String studentNumber
    ) {
        try {
            return new HomeownerUser(
                username,
                email,
                phone,
                passwordHash,
                bookingHandler,
                propertiesHandler,
                roomHandler,
                reviewHandler
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to create HomeownerUser", e);
        }
    }
}
