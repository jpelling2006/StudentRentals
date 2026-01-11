package booking;

import java.util.ArrayList;
import java.util.List;

import room.Room;
import room.RoomQueryService;
import user.User;

public class BookingQueryService {
    private final RoomQueryService roomQueryService;

    public BookingQueryService(RoomQueryService roomQueryService) {
        this.roomQueryService = roomQueryService;
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();

        for (Room room : roomQueryService.getAllRooms()) {
            bookings.addAll(room.getBookings());
        }
        return bookings;
    }

    public List<Booking> getBookingsForStudent(String username) {
        List<Booking> studentBookings = new ArrayList<>();

        for (Room room : roomQueryService.getAllRooms()) {
            Booking booking = room.getBookingByUser(username);
            if (booking != null) { studentBookings.add(booking); }
        }
        return studentBookings;
    }

    public List<Booking> getBookingsForHomeowner(User user) {
        List<Booking> homeownerBookings = new ArrayList<>();

        for (Room room : roomQueryService.getAllRooms()) {
            if (room.getProperty().getUser().equals(user)) {
                homeownerBookings.addAll(room.getBookings());
            }
        }
        return homeownerBookings;
    }
}
