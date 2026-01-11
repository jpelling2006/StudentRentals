package booking;

import java.time.LocalDate;

import room.RoomQueryService;

public class BookingStatusUpdater {
    private final RoomQueryService roomQueryService;

    public BookingStatusUpdater(RoomQueryService roomQueryService) {
        this.roomQueryService = roomQueryService;
    }

    public void updateEndedBookings() {
        LocalDate today = LocalDate.now();

        roomQueryService.getAllRooms().stream() // gets all rooms
            .flatMap(room -> room.getBookings().stream()) // gets all bookings for each room
            .filter( // only bookings to update
                booking -> booking.getBookingStatus() != BookingStatus.ENDED
                && booking.getEndDate().isBefore(today)
            )
            .forEach(booking -> booking.setBookingStatus(BookingStatus.ENDED)); //update
    }
}
