package Booking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import FrontEnd.Session;
import Helpers.Helpers;
import Properties.Property;
import Room.Room;
import Room.RoomManager;
import Room.RoomQueryService;

public class BookingManager {
    private Scanner scanner = new Scanner(System.in);
    private Session session;
    private RoomManager roomManager;

    public BookingManager(
        RoomManager roomManager, 
        Session session, 
        Scanner scanner
    ) {
        this.roomManager = roomManager;
        this.session = session;
        this.scanner = scanner;
    }

    // ok this definitely doesnt work correctly but its fineeee
    public void newBooking() {
        // fix this
        Room selectedRoom = Helpers.selectFromList(
            scanner,
            RoomQueryService.getAllRooms(),
            "Select room: "
        );

        Booking booking = new Booking();

        System.out.println("\nCreating new booking");
        
        booking.generateBookingID();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setRoom(selectedRoom);
        booking.setUsername(session.getCurrentUser().getUsername());
        
        LocalDate start = Helpers.readFutureDate(scanner, "Start date");
        LocalDate end;

        while (true) {
            end = Helpers.readFutureDate(scanner, "End date");
            try {
                booking.setStartDate(start);
                booking.setEndDate(end);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            selectedRoom.addBooking(booking);
            System.out.println("Booking created successfully.");
        } catch (IllegalStateException e) { System.out.println(e.getMessage()); }
    }

    public List<Booking> getUserBookings() {
        String username = session.getCurrentUser().getUsername();
        List<Booking> userBookings = new ArrayList<>();

        for(Room room : RoomQueryService.getAllRooms()) {
            for (Booking booking : room.getBookings()) {
                if (booking.getUsername().equals(username)) {
                    userBookings.add(booking);
                }
            }
        }

        return userBookings;
    }

    // later differ from students, homeowners, and admins
    public void listBookings(List<Booking> userBookings) {
        if (userBookings.isEmpty()) {
            System.out.println("You have no bookings.");
            return;
        }

        System.out.println("\nYour bookings:");
        for (int i = 0; i < userBookings.size(); i++) {
            Booking booking = userBookings.get(i);
            Room room = booking.getRoom();
            Property property = room.getProperty();
            String address = (property != null) 
                ? property.getAddress() : "Unknown property";

            System.out.println(
                (i + 1) + ". "
                + booking.getStartDate().toString() + " to "
                + booking.getEndDate().toString() + " - "
                + address + " ("
                + room.getLocation() + ")"
            );
        }
    }

    public void editBooking() {
        List<Booking> userBookings = getUserBookings();

        if (userBookings.isEmpty()) {
            System.out.println("You have no bookings to edit.");
            return;
        }

        listBookings(userBookings);

        Booking choice = Helpers.selectFromList(scanner, userBookings, "Select a booking to edit");

        editBookingMenu(choice);
    }

    private void editBookingMenu(Booking booking) {
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
            System.out.println("3. Status"); // homeowner only
            System.out.println("4. Cancel");

            Integer choice = Helpers.readIntInRange(scanner, "Choose a field to edit: ", 1, 4);

            switch (choice) {
                case 1 -> booking.setStartDate(Helpers.readFutureDate(scanner, "Enter start date: ")); // must be before end date
                case 2 -> booking.setEndDate(Helpers.readFutureDate(scanner, "Enter end date: "));
                case 3 -> booking.setBookingStatus(Helpers.readEnum(scanner, "Set booking status: ", BookingStatus.class));
                case 4 -> {
                    System.out.println("Edit cancelled.");
                    return;
                }
            }
        }
    }

    public void deleteBooking() {
        List<Booking> userBookings = getUserBookings();

        if (userBookings.isEmpty()) {
            System.out.println("You have no bookings to delete");
            return;
        }

        listBookings(userBookings);

        Booking selectedBooking = Helpers.selectFromList(scanner, userBookings, "Select a booking to delete");

        if (!confirmDeletion(selectedBooking)) {
            System.out.println("Deletion cancelled");
            return;
        }

        selectedBooking.getRoom().removeBooking(selectedBooking.getUsername());
        System.out.println("Room deleted successfully.");
    }

    private boolean confirmDeletion(Booking booking) {
        System.out.println("\nAre you sure you want to delete this review?");

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

    public void start() {
        while (true) {
            System.out.println("\nBooking Management System");
            System.out.println("1. Create booking");
            System.out.println("2. List bookings");
            System.out.println("3. Edit bookings");
            System.out.println("4. Delete bookings");
            System.out.println("5. Exit");
            
            Integer choice = Helpers.readIntInRange(scanner, "Enter choice: ", 1, 5);

            switch (choice) {
                case 1 -> newBooking();
                case 2 -> listBookings(getUserBookings());
                case 3 -> editBooking();
                case 4 -> deleteBooking();
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
            }
        }
    }
}
