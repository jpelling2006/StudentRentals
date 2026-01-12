package booking;

import session.Session;
import user.AdministratorUser;
import user.HomeownerUser;
import user.StudentUser;
import user.User;

public final class BookingManager {
    private static BookingManager instance;

    public static BookingManager getInstance() {
        if (instance == null) { instance = new BookingManager(); }
        return instance;
    }

    private BookingManager() {}

    public static boolean handleOnce() {
        if (!Session.isLoggedIn()) {
            System.out.println("Please log in first.");
            return true;
        }

        User user = Session.getCurrentUser();

        // automatic routing based on user type
        if (user instanceof AdministratorUser) {
            return AdminBookingManager.handleOnce();
        } else if (user instanceof HomeownerUser) {
            return HomeownerBookingManager.handleOnce();
        } else if (user instanceof StudentUser) {
            return StudentBookingManager.handleOnce();
        }

        System.out.println("You do not have access to bookings.");
        return true;
    }
}
