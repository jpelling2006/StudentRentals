package Booking;

import java.time.LocalDate;

import Room.Room;
import Room.RoomQueryService;

public class BookingStatusUpdater {
    private final RoomQueryService roomQueryService;

    public BookingStatusUpdater(RoomQueryService roomQueryService) {
        this.roomQueryService = roomQueryService;
    }

    public void updateEndedBookings() {
        LocalDate today = LocalDate.now();
        
        for (Room room : roomQueryService.getAllRooms()) {
            for (Booking booking : room.getBookings()) {
                if (
                    booking.getBookingStatus() != BookingStatus.ENDED
                    && booking.getEndDate().isBefore(today)
                ) {
                    booking.setBookingStatus(BookingStatus.ENDED);
                }
            }
        }
    }
}
