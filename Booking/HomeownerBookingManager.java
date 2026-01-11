package booking;

import java.util.List;
import java.util.Scanner;

import helpers.Helpers;
import session.Session;

public class HomeownerBookingManager {
    private final BookingQueryService bookingQueryService;
    private final Session session;
    private final Scanner scanner;

    public HomeownerBookingManager(
        BookingQueryService bookingQueryService,
        Session session,
        Scanner scanner
    ) {
        this.bookingQueryService = bookingQueryService;
        this.session = session;
        this.scanner = scanner;
    }

    public void listBookings() {
        List<Booking> bookings = bookingQueryService.getBookingsForHomeowner(
            session.getCurrentUser()
        );

        if (bookings.isEmpty()) {
            System.out.println("You have no bookings.");
            return;
        }

        System.out.println("\nYour bookings:");

        // prints list of bookings
        Helpers.printIndexed(bookings, Booking::toString);
    }

    public void editBookingStatus() {
        List<Booking> userBookings = bookingQueryService.getBookingsForHomeowner(
            session.getCurrentUser()
        );

        if (userBookings.isEmpty()) {
            System.out.println("You have no bookings to edit.");
            return;
        }

        listBookings();

        // edit selected booking
        editStatusMenu(
            Helpers.selectFromList(
                scanner, 
                userBookings, 
                "Select a booking to edit"
            )
        );
    }

    private void editStatusMenu(Booking booking) {
        while (true) {            
            System.out.println( "\n Editing booking: ");
            System.out.println(booking.toString());
            System.out.println("1. Status");
            System.out.println("2. Cancel");

            switch (
                Helpers.readIntInRange(
                    scanner, 
                    "Choose a field to edit: ", 
                    1, 
                    2
                )
            ) {
                case 1 -> booking.setBookingStatus(
                    Helpers.readEnum( // select booking status from BookingStatus enum
                        scanner, 
                        "Set booking status: ", 
                        BookingStatus.class
                    )
                );
                case 2 -> {
                    System.out.println("Edit cancelled.");
                    return;
                }
            }
        }
    }
}
