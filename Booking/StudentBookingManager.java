package Booking;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import FrontEnd.Session;
import Helpers.Helpers;
import Properties.Property;
import Room.Room;
import Room.RoomQueryService;

public class StudentBookingManager {
    private final BookingQueryService bookingQueryService;
    private final RoomQueryService roomQueryService;
    private final Session session;
    private final Scanner scanner;

    public StudentBookingManager(
        BookingQueryService bookingQueryService,
        RoomQueryService roomQueryService,
        Session session,
        Scanner scanner
    ) {
        this.bookingQueryService = bookingQueryService;
        this.roomQueryService = roomQueryService;
        this.session = session;
        this.scanner = scanner;
    }

    public void createBooking() {
        // see if this outputs the way you want
        Room room = Helpers.selectFromList(
            scanner,
            roomQueryService.getAllRooms(),
            "Select room"
        );

        Booking booking = new Booking();
        booking.generateBookingID();
        booking.setRoom(room);
        booking.setUsername(session.getCurrentUser().getUsername());
        booking.setBookingStatus(BookingStatus.PENDING);

        LocalDate start = Helpers.readFutureDate(scanner, "Start date");
        LocalDate end = Helpers.readFutureDate(scanner, "End date");

        booking.setStartDate(start);
        booking.setEndDate(end);

        room.addBooking(booking);
        System.out.println("Booking created.");
    }

    public void editBooking() {
        List<Booking> userBookings = bookingQueryService.getBookingsForStudent(
                session.getCurrentUser().getUsername()
            );


        if (userBookings.isEmpty()) {
            System.out.println("You have no bookings to edit.");
            return;
        }

        Helpers.printIndexed(userBookings, Booking::toString);

        Booking choice = Helpers.selectFromList(scanner, userBookings, "Select a booking to edit");

        editBookingMenu(choice);
    }

    private void editBookingMenu(Booking booking) {
        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            System.out.println("Only pending bookings can be edited.");
            return;
        }


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
            System.out.println("1. Start date"); // student only
            System.out.println("2. End date"); // student only
            System.out.println("3. Cancel");

            Integer choice = Helpers.readIntInRange(scanner, "Choose a field to edit: ", 1, 3);

            switch (choice) {
                case 1 -> booking.setStartDate(Helpers.readFutureDate(scanner, "Enter start date: ")); // must be before end date
                case 2 -> booking.setEndDate(Helpers.readFutureDate(scanner, "Enter end date: "));
                case 3 -> {
                    System.out.println("Edit cancelled.");
                    return;
                }
            }
        }
    }

    public void listBookings() {
        List<Booking> bookings =
            bookingQueryService.getBookingsForStudent(
                session.getCurrentUser().getUsername()
            );

        
        Helpers.printIndexed(bookings, Booking::toString); // make this
    }

    public void cancelBooking() {
        Booking selectedBooking = Helpers.selectFromList(
            scanner,
            bookingQueryService.getBookingsForStudent(
                session.getCurrentUser().getUsername()
            ),
            "Select booking"
        );

        if (!confirmCancellation(selectedBooking)) {
            System.out.println("Cancellation cancelled");
            return;
        }

        selectedBooking.setBookingStatus(BookingStatus.CANCELLED);
        System.out.println("Booking cancelled.");
    }

    private boolean confirmCancellation(Booking booking) {
        System.out.println("\nAre you sure you want to cancel this booking?");

        Room room = booking.getRoom();
        Property property = room.getProperty();
        String address = (property != null)
            ? property.getAddress() : "Unknown property";

        System.out.println(
            booking.getStartDate().toString() + " to "
            + booking.getEndDate().toString() + " - "
            + address + " ("
            + room.getLocation() + ")"
        );

        return Helpers.confirm(scanner);
    }
}
