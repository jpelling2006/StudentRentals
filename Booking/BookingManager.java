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

    public void start() {
        if (!session.isLoggedIn()) {
            System.out.println("Please log in first.");
            return;
        }

        switch (session.getCurrentUser().getUserType()) {
            case STUDENT -> studentMenu();
            case HOMEOWNER -> homeownerMenu();
            case ADMINISTRATOR -> adminMenu();
        }
    }

    private void studentMenu() {
        while (true) {
            System.out.println("\nStudent Booking Menu");
            System.out.println("1. Create booking");
            System.out.println("2. View bookings");
            System.out.println("3. Cancel booking");
            System.out.println("4. Back");

            switch (
                Helpers.readIntInRange(
                    scanner, 
                    "Choose option: ", 
                    1, 
                    4
                )
            ) {
                case 1 -> studentBookingManager.createBooking();
                case 2 -> studentBookingManager.listBookings();
                case 3 -> studentBookingManager.cancelBooking();
                case 4 -> { return; }
            }
        }
    }

    private void homeownerMenu() {
        while (true) {
            System.out.println("\nHomeowner Booking Menu");
            System.out.println("1. View bookings for properties");
            System.out.println("2. Update booking status");
            System.out.println("3. Back");

            switch (
                Helpers.readIntInRange(
                    scanner, 
                    "Choose option: ", 
                    1, 
                    3
                )
            ) {
                case 1 -> homeownerBookingManager.listBookings();
                case 2 -> homeownerBookingManager.editBookingStatus();
                case 3 -> { return; }
            }
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Booking Menu");
            System.out.println("1. View all bookings");
            System.out.println("2. Force update status");
            System.out.println("3. Delete booking");
            System.out.println("4. Back");

            switch (
                Helpers.readIntInRange(
                    scanner, 
                    "Choose option: ", 
                    1, 
                    4
                )
            ) {
                case 1 -> adminBookingManager.listAllBookings();
                case 2 -> adminBookingManager.forceEditStatus();
                case 3 -> adminBookingManager.deleteBooking();
                case 4 -> { return; }
            }
        }
    }
}
