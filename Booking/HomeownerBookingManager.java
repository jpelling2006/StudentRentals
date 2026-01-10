package booking;

import java.util.List;
import java.util.Scanner;

import helpers.Helpers;
import properties.Property;
import room.Room;
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
            session.getCurrentUser().getUsername()
        );

        Helpers.printIndexed(bookings, Booking::toString); // make this
    }

    public void editBookingStatus() {
        List<Booking> userBookings = bookingQueryService.getBookingsForHomeowner(
            session.getCurrentUser().getUsername()
        );

        if (userBookings.isEmpty()) {
            System.out.println("You have no bookings to edit.");
            return;
        }

        listBookings();

        Booking choice = Helpers.selectFromList(
            scanner, 
            userBookings, 
            "Select a booking to edit"
        );

        editStatusMenu(choice);
    }

    private void editStatusMenu(Booking booking) {
        while (true) {
            Room room = booking.getRoom();
            Property property = room.getProperty();
            String address = (property != null)
                ? property.getAddress() : "Unknown property";
            
            System.out.println(
                "\n Editing booking: "
                + booking.getStartDate().toString() + " to "
                + booking.getEndDate().toString() + " - "
                + address + " ("
                + room.getLocation() + ")"
            );
            System.out.println("1. Status"); // homeowner only
            System.out.println("2. Cancel");

            Integer choice = Helpers.readIntInRange(
                scanner, 
                "Choose a field to edit: ", 
                1, 
                2
            );

            switch (choice) {
                case 1 -> booking.setBookingStatus(
                    Helpers.readEnum(
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
