package booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import helpers.Helpers;
import room.Room;
import room.RoomQueryService;

public class AdminBookingManager {

    private final RoomQueryService roomQueryService;
    private final Scanner scanner;

    public AdminBookingManager(
        RoomQueryService roomQueryService,
        Scanner scanner
    ) {
        this.roomQueryService = roomQueryService;
        this.scanner = scanner;
    }

    private List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();

        for (Room room : roomQueryService.getAllRooms()) {
            bookings.addAll(room.getBookings());
        }

        return bookings;
    }

    public void listAllBookings() {
        List<Booking> bookings = getAllBookings();

        if (bookings.isEmpty()) {
            System.out.println("No bookings exist.");
            return;
        }

        System.out.println("\nAll bookings:");
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);

            System.out.println(
                (i + 1) + ". "
                + b.getUsername() + " | "
                + b.getStartDate() + " → "
                + b.getEndDate() + " | "
                + b.getRoom().getProperty().getAddress() + " | "
                + b.getBookingStatus()
            );
        }
    }

    public void forceEditStatus() {
        List<Booking> bookings = getAllBookings();
        if (bookings.isEmpty()) return;

        listAllBookings();

        Booking booking = Helpers.selectFromList(
            scanner,
            bookings,
            "Select booking to update"
        );

        if (booking == null) return;

        if (booking.hasEnded()) {
            System.out.println("Booking has already ended. Status is locked.");
            return;
        }

        BookingStatus newStatus = Helpers.readEnum(
            scanner,
            "Select new status",
            BookingStatus.class
        );

        booking.setBookingStatus(newStatus);
        System.out.println("Booking status updated.");
    }

    public void deleteBooking() {
        List<Booking> bookings = getAllBookings();
        if (bookings.isEmpty()) return;

        listAllBookings();

        Booking booking = Helpers.selectFromList(
            scanner,
            bookings,
            "Select booking to delete"
        );

        if (booking == null) return;

        System.out.println("Are you sure you want to delete this booking?");
        System.out.println(
            booking.getUsername() + " | "
            + booking.getStartDate() + " → "
            + booking.getEndDate()
        );

        if (!Helpers.confirm(scanner)) {
            System.out.println("Deletion cancelled.");
            return;
        }

        booking.getRoom().removeBooking(booking.getUsername());
        System.out.println("Booking deleted.");
    }
}
