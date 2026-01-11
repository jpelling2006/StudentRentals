package booking;

import access.BookingAccess;
import session.Session;
import user.User;

public class BookingManager {
    private final Session session;

    public BookingManager(Session session) { this.session = session; }

    public boolean handleOnce() {
        if (!session.isLoggedIn()) {
            System.out.println("Please log in first.");
            return true;
        }

        User user = session.getCurrentUser();

        if (user instanceof BookingAccess bookingAccess) {
            return bookingAccess.getBookingHandler().handleOnce();
        }

        System.out.println("You do not have access to bookings.");
        return true;
    }
}
