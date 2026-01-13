package booking;

import java.util.List;
import java.util.Scanner;

import helpers.Helpers;

public final class AdminBookingManager implements BookingHandler {
    private final static Scanner scanner = new Scanner(System.in);
    private static AdminBookingManager instance;

    public static AdminBookingManager getInstance() {
        if (instance == null) { instance = new AdminBookingManager(); }
        return instance;
    }

    private AdminBookingManager() {}

    private static void listAllBookings() {
        List<Booking> bookings = BookingQueryService.getAllBookings();

        if (bookings.isEmpty()) {
            System.out.println("No bookings exist.");
            return;
        }

        System.out.println("\nAll bookings:");

        // prints list of all bookings
        Helpers.printIndexed(bookings, Booking::toString);
    }

    private static void deleteBooking() {
        List<Booking> bookings = BookingQueryService.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings exist.");
            return;
        }

        // user selects booking from list
        Booking selectedBooking = Helpers.selectFromList(
            scanner,
            bookings,
            "Select booking to delete",
            Booking::toString
        );

        if (selectedBooking == null) {
            System.out.println("Booking doesn't exist.");
            return;
        }

        System.out.println("Are you sure you want to delete this booking?");
        System.out.println(selectedBooking.toString());

        if (!Helpers.confirm(scanner)) {
            System.out.println("Deletion cancelled.");
            return;
        }

        selectedBooking.getRoom().forceRemoveBooking(selectedBooking.getUsername());
        System.out.println("Booking deleted.");
    }

    public static boolean handleOnce() {
        boolean running = true;
        while (running) {
            System.out.println("\nAdmin Booking Menu");
            System.out.println("1. View all bookings");
            System.out.println("2. Delete booking");
            System.out.println("3. Back");

            switch (
                Helpers.readIntInRange(
                    scanner, 
                    "Choose option: ", 
                    1, 
                    3
                )
            ) {
                case 1 -> listAllBookings();
                case 2 -> deleteBooking();
                case 3 -> running = false;
            }
        }
        return true;
    }
}
