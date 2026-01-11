package booking;

import java.util.Scanner;

import session.Session;
import helpers.Helpers;

public class BookingManager {
    private final StudentBookingManager studentBookingManager;
    private final HomeownerBookingManager homeownerBookingManager;
    private final AdminBookingManager adminBookingManager;

    private final Session session;
    private final Scanner scanner;

    public BookingManager(
        StudentBookingManager studentBookingManager,
        HomeownerBookingManager homeownerBookingManager,
        AdminBookingManager adminBookingManager,
        Session session,
        Scanner scanner
    ) {
        this.studentBookingManager = studentBookingManager;
        this.homeownerBookingManager = homeownerBookingManager;
        this.adminBookingManager = adminBookingManager;
        this.session = session;
        this.scanner = scanner;
    }

    public boolean handleOnce() {
        if (!session.isLoggedIn()) {
            System.out.println("Please log in first.");
            return true;
        }

        return switch (session.getCurrentUser().getUserType()) {
            case STUDENT -> handleStudent();
            case HOMEOWNER -> handleHomeowner();
            case ADMINISTRATOR -> handleAdmin();
        };
    }

    private boolean handleStudent() {
        System.out.println("\nStudent Booking Menu");
        System.out.println("1. Create booking");
        System.out.println("2. View bookings");
        System.out.println("3. Cancel booking");
        System.out.println("4. Back");

        return switch (
            Helpers.readIntInRange(
                scanner, 
                "Choose option: ", 
                1, 
                4
            )
        ) {
            case 1 -> {
                studentBookingManager.createBooking();
                yield false;
            }
            case 2 -> {
                studentBookingManager.listBookings();
                yield false;
            }
            case 3 -> {
                studentBookingManager.cancelBooking();
                yield false;
            }
            case 4 -> true;
            default -> false;
        };
    }

    private boolean handleHomeowner() {
        System.out.println("\nHomeowner Booking Menu");
        System.out.println("1. View bookings for properties");
        System.out.println("2. Update booking status");
        System.out.println("3. Back");

        return switch (
            Helpers.readIntInRange(
                scanner, 
                "Choose option: ", 
                1, 
                3
            )
        ) {
            case 1 -> {
                homeownerBookingManager.listBookings();
                yield false;
            }
            case 2 -> {
                homeownerBookingManager.editBookingStatus();
                yield false;
            }
            case 3 -> true;
            default -> false;
        };
    }

    private boolean handleAdmin() {
        System.out.println("\nAdmin Booking Menu");
        System.out.println("1. View all bookings");
        System.out.println("2. Delete booking");
        System.out.println("3. Back");

        return switch (
            Helpers.readIntInRange(
                scanner, 
                "Choose option: ", 
                1, 
                3
            )
        ) {
            case 1 -> {
                adminBookingManager.listAllBookings();
                yield false;
            }
            case 2 -> {
                adminBookingManager.deleteBooking();
                yield false;
            }
            case 3 -> true;
            default -> false;
        };
    }
}
