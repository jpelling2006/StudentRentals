package booking;

import java.util.List;

import room.RoomQueryService;
import user.User;

public final class BookingQueryService {
    private static BookingQueryService instance;

    public static BookingQueryService getInstance() {
        if (instance == null) { instance = new BookingQueryService(); }
        return instance;
    }

    private BookingQueryService() {}

    public static List<Booking> getAllBookings() {
        return RoomQueryService.getAllRooms().stream() // gets all rooms
            .flatMap(room -> room.getBookings().stream()) // gets bookings for each room
            .toList();
    }

    public List<Booking> getBookingsForStudent(String username) {
        return RoomQueryService.getAllRooms().stream() // gets all rooms
            .map(room -> room.getBookingByUser(username)) // gets all bookings by user
            .filter(booking -> booking != null) // gets rid of null bookings
            .toList();
    }

    public List<Booking> getBookingsForHomeowner(User user) {
        return RoomQueryService.getAllRooms().stream() // gets all rooms
            .filter(room -> room.getProperty().getUser().equals(user)) // gets all rooms by user
            .flatMap(room -> room.getBookings().stream()) // gets all bookings for rooms
            .toList();
    }
}
