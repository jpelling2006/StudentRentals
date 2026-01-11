package booking;

import java.util.List;

import room.RoomQueryService;
import user.User;

public class BookingQueryService {
    private final RoomQueryService roomQueryService;

    public BookingQueryService(RoomQueryService roomQueryService) {
        this.roomQueryService = roomQueryService;
    }

    public List<Booking> getAllBookings() {
        return roomQueryService.getAllRooms().stream() // gets all rooms
            .flatMap(room -> room.getBookings().stream()) // gets bookings for each room
            .toList();
    }

    public List<Booking> getBookingsForStudent(String username) {
        return roomQueryService.getAllRooms().stream() // gets all rooms
            .map(room -> room.getBookingByUser(username)) // gets all bookings by user
            .filter(booking -> booking != null) // gets rid of null bookings
            .toList();
    }

    public List<Booking> getBookingsForHomeowner(User user) {
        return roomQueryService.getAllRooms().stream() // gets all rooms
            .filter(room -> room.getProperty().getUser().equals(user)) // gets all rooms by user
            .flatMap(room -> room.getBookings().stream()) // gets all bookings for rooms
            .toList();
    }
}
