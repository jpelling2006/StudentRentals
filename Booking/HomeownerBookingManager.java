package booking;

import java.util.List;
import java.util.Scanner;

import helpers.Helpers;
import session.Session;

public class HomeownerBookingManager implements BookingHandler {
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

        // edit selected booking
        editStatusMenu(
            Helpers.selectFromList(
                scanner, 
                userBookings, 
                "Select a booking to edit",
                Booking::toString
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

    @Override
    public boolean handleOnce() {
        System.out.println("\nHomeowner Booking Menu");
        System.out.println("1. View bookings for properties");
        System.out.println("2. Update booking status");
        System.out.println("3. Back");

        return switch (
            Helpers.readIntInRange(scanner, "Choose option: ", 1, 3)
        ) {
            case 1 -> { listBookings(); yield false; }
            case 2 -> { editBookingStatus(); yield false; }
            case 3 -> true;
            default -> false;
        };
    }
}
