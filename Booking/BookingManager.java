package booking;

import session.Session;
import user.AdministratorUser;
import user.HomeownerUser;
import user.StudentUser;
import user.User;

public class BookingManager {
    private final AdminBookingManager adminBookingManager;
    private final HomeownerBookingManager homeownerBookingManager;
    private final StudentBookingManager studentBookingManager;
    private final Session session;

    public BookingManager(
        Session session,
        AdminBookingManager adminBookingManager,
        HomeownerBookingManager homeownerBookingManager,
        StudentBookingManager studentBookingManager
    ) {
        this.session = session;
        this.adminBookingManager = adminBookingManager;
        this.homeownerBookingManager = homeownerBookingManager;
        this.studentBookingManager = studentBookingManager;
    }

    public boolean handleOnce() {
        if (!session.isLoggedIn()) {
            System.out.println("Please log in first.");
            return true;
        }

        User user = session.getCurrentUser();

        // automatic routing based on user type
        if (user instanceof AdministratorUser) {
            return adminBookingManager.handleOnce();
        } else if (user instanceof HomeownerUser) {
            return homeownerBookingManager.handleOnce();
        } else if (user instanceof StudentUser) {
            return studentBookingManager.handleOnce();
        }

        System.out.println("You do not have access to bookings.");
        return true;
    }
}
