package booking;

import java.time.LocalDate;

import room.RoomQueryService;

public final class BookingStatusUpdater {
    private static BookingStatusUpdater instance;

    public static BookingStatusUpdater getInstance() {
        if (instance == null) { instance = new BookingStatusUpdater(); }
        return instance;
    }

    private BookingStatusUpdater() {}

    public static void updateEndedBookings() {
        LocalDate today = LocalDate.now();

        RoomQueryService.getAllRooms().stream() // gets all rooms
            .flatMap(room -> room.getBookings().stream()) // gets all bookings for each room
            .filter( // only bookings to update
                booking -> booking.getBookingStatus() != BookingStatus.ENDED
                && booking.getEndDate().isBefore(today)
            )
            .forEach(booking -> booking.setBookingStatus(BookingStatus.ENDED)); //update
    }
}
