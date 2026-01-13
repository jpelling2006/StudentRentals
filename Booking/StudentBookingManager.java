package booking;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import helpers.Helpers;
import room.Room;
import room.RoomQueryService;
import session.Session;

public final class StudentBookingManager implements BookingHandler {
    private final static Scanner scanner = new Scanner(System.in);
    private static StudentBookingManager instance;

    public static StudentBookingManager getInstance() {
        if (instance == null) { instance = new StudentBookingManager(); }
        return instance;
    }

    private StudentBookingManager() {}

    private static void createBooking() {
        Room selectedRoom = Helpers.selectFromList(
            scanner,
            RoomQueryService.getAllRooms(),
            "Select room",
            Room::toString
        );

        if (selectedRoom == null) { return; }

        String username = Session.getCurrentUser().getUsername();
        BookingStatus bookingStatus = BookingStatus.PENDING;

        try {
            LocalDate startDate = Helpers.readFutureDate(
                scanner, 
                "Start date"
            );
            LocalDate endDate = Helpers.readFutureDate(scanner, "End date");

            Booking booking = new Booking(
                bookingStatus, 
                selectedRoom, 
                username, 
                startDate, 
                endDate
            );

            // add booking to room
            selectedRoom.addBooking(booking);

            System.out.println("Booking created successfully.");
        } catch (Exception e) {
            System.out.println("Failed to create booking: " + e.getMessage());
        }
    }

    private static void editBooking() {
        List<Booking> userBookings = BookingQueryService.getBookingsForStudent(
            Session.getCurrentUser().getUsername()
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

    private static void editBookingMenu(Booking booking) {
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

    private static void listBookings() {
        List<Booking> bookings =
            BookingQueryService.getBookingsForStudent(
                Session.getCurrentUser().getUsername()
            );

        Helpers.printIndexed(bookings, Booking::toString);
    }

    private static void cancelBooking() {
        List<Booking> userBookings = BookingQueryService.getBookingsForStudent(
            Session.getCurrentUser().getUsername()
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

    protected static boolean handleOnce() {
        boolean running = true;
        while (running) {
            System.out.println("\nStudent Booking Menu");
            System.out.println("1. Create booking");
            System.out.println("2. View bookings");
            System.out.println("3. Edit Booking");
            System.out.println("4. Cancel booking");
            System.out.println("5. Back");

            switch (
                Helpers.readIntInRange(
                    scanner, 
                    "Choose option: ", 
                    1, 
                    5
                )
            ) {
                case 1 -> createBooking();
                case 2 -> listBookings();
                case 3 -> editBooking();
                case 4 -> cancelBooking();
                case 5 -> running = false;
            }
        }
        return true;
    }
}
