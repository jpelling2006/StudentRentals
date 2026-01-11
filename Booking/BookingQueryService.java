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
        return roomQueryService.getAllRooms().stream()
            .flatMap(room -> room.getBookings().stream())
            .toList();
    }

    public List<Booking> getBookingsForStudent(String username) {
        return roomQueryService.getAllRooms().stream()
            .map(room -> room.getBookingByUser(username))
            .filter(booking -> booking != null)
            .toList();
    }

    public List<Booking> getBookingsForHomeowner(User user) {
        return roomQueryService.getAllRooms().stream()
            .filter(room -> room.getProperty().getUser().equals(user))
            .flatMap(room -> room.getBookings().stream())
            .toList();
    }
}
