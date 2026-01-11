package booking;

import java.util.List;
import java.util.Scanner;

import access.BookingAccess;
import helpers.Helpers;
import room.Room;
import room.RoomQueryService;
import session.Session;

public class StudentBookingManager implements BookingHandler {
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
        Room room = Helpers.selectFromList(
            scanner,
            roomQueryService.getAllRooms(),
            "Select room",
            Room::toString
        );

        // automatically generated values
        Booking booking = new Booking();
        booking.generateBookingID();
        booking.setRoom(room);
        booking.setUsername(session.getCurrentUser().getUsername());
        booking.setBookingStatus(BookingStatus.PENDING);

        // user input values
        booking.setStartDate(Helpers.readFutureDate(scanner, "Start date"));
        booking.setEndDate(Helpers.readFutureDate(scanner, "End date"));

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
        
        editBookingMenu(
            Helpers.selectFromList(
                scanner, 
                userBookings, 
                "Select a booking to edit",
                Booking::toString
            )
        );
    }

    private void editBookingMenu(Booking booking) {
        // booking can only be edited if booking is pending
        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            System.out.println("Only pending bookings can be edited.");
            return;
        }

        while (true) {            
            System.out.println("\n Editing booking: ");
            System.out.println(booking.toString());
            System.out.println("1. Start date");
            System.out.println("2. End date");
            System.out.println("3. Cancel");

            switch (
                Helpers.readIntInRange(
                    scanner, 
                    "Choose a field to edit: ", 
                    1, 
                    3
                )
            ) {
                case 1 -> booking.setStartDate(
                    Helpers.readFutureDate(scanner, "Enter start date: ")
                );
                case 2 -> booking.setEndDate(
                    Helpers.readFutureDate(scanner, "Enter end date: ")
                );
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

        Helpers.printIndexed(bookings, Booking::toString);
    }

    public void cancelBooking() {
        List<Booking> userBookings = bookingQueryService.getBookingsForStudent(
            session.getCurrentUser().getUsername()
        );

        if (userBookings.isEmpty()) {
            System.out.println("You have no bookings to cancel.");
            return;
        }

        Booking selectedBooking = Helpers.selectFromList(
            scanner,
            userBookings,
            "Select booking",
            Booking::toString
        );

        if (selectedBooking == null) { return; }

        System.out.println("\nAre you sure you want to cancel this booking?");
        System.out.println(selectedBooking.toString());

        if (!Helpers.confirm(scanner)) {
            System.out.println("Cancellation cancelled");
            return;
        }

        selectedBooking.setBookingStatus(BookingStatus.CANCELLED);
        System.out.println("Booking cancelled.");
    }

    @Override
    public boolean handleOnce() {
        System.out.println("\nStudent Booking Menu");
        System.out.println("1. Create booking");
        System.out.println("2. View bookings");
        System.out.println("3. Cancel booking");
        System.out.println("4. Back");

        return switch (
            Helpers.readIntInRange(scanner, "Choose option: ", 1, 4)
        ) {
            case 1 -> {
                createBooking();
                yield false;
            }
            case 2 -> {
                listBookings();
                yield false;
            }
            case 3 -> {
                cancelBooking();
                yield false;
            }
            case 4 -> true;
            default -> false;
        };
    }
}
