package booking;

import java.util.List;
import java.util.Scanner;

import helpers.Helpers;

public class AdminBookingManager {
    private final BookingQueryService bookingQueryService;
    private final Scanner scanner;

    public AdminBookingManager(
        BookingQueryService bookingQueryService,
        Scanner scanner
    ) {
        this.bookingQueryService = bookingQueryService;
        this.scanner = scanner;
    }

    public void listAllBookings() {
        List<Booking> bookings = bookingQueryService.getAllBookings();

        if (bookings.isEmpty()) {
            System.out.println("No bookings exist.");
            return;
        }

        System.out.println("\nAll bookings:");

        // prints list of all bookings
        Helpers.printIndexed(bookings, Booking::toString);
    }

    public void deleteBooking() {
        List<Booking> bookings = bookingQueryService.getAllBookings();
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
}
