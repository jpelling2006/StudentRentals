package user;

import booking.BookingHandler;
import review.ReviewHandler;

public final class StudentUserFactory extends UserFactory {
    private static StudentUserFactory INSTANCE;

    private final BookingHandler bookingHandler;
    private final ReviewHandler reviewHandler;

    private StudentUserFactory(
        BookingHandler bookingHandler,
        ReviewHandler reviewHandler
    ) {
        this.bookingHandler = bookingHandler;
        this.reviewHandler = reviewHandler;
    }

    public static StudentUserFactory init(
        BookingHandler bookingHandler,
        ReviewHandler reviewHandler
    ) {
        if (INSTANCE == null) {
            INSTANCE = new StudentUserFactory(bookingHandler, reviewHandler);
        }
        return INSTANCE;
    }

    public static StudentUserFactory getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("StudentUserFactory not initialised");
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
            return new StudentUser(
                username,
                email,
                phone,
                passwordHash,
                university,
                studentNumber,
                bookingHandler,
                reviewHandler
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to create StudentUser", e);
        }
    }
}
