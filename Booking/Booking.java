package Booking;

import java.time.LocalDate;
import java.util.UUID;

import Room.Room;

public class Booking {
    private UUID bookingID;
    private BookingStatus bookingStatus;
    private Room room;
    private String username; // students only
    private LocalDate startDate;
    private LocalDate endDate;

    public UUID getBookingID() { return bookingID; }
    public void generateBookingID() {
        this.bookingID = UUID.randomUUID();
    }

    public Room getRoom() { return room; }
    public void setRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room required.");
        }
        this.room = room;
    }

    public BookingStatus getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(BookingStatus bookingStatus) {
        if (bookingStatus == null) {
            throw new IllegalArgumentException("Booking status is required.");
        }
        if (this.bookingStatus == bookingStatus.ENDED) {
            throw new IllegalStateException("Ended bookings cannot be modified.");
        }

        this.bookingStatus = bookingStatus;
    }

    // if exists too
    public String getUsername() { return username; }
    public void setUsername(String username) {
        if (username == null || username.length() > 32) {
            throw new IllegalArgumentException(
                "Username must be up to 32 characters."
            );
        }
        this.username = username;
    }

    // check if dates have passed
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date required.");
        }
        this.startDate = startDate;
    }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { 
        if (endDate == null || endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Invalid end date.");
        }
        this.endDate = endDate;
    }

    public boolean hasEnded() { return endDate.isBefore(LocalDate.now()); }

    public boolean overlaps(LocalDate from, LocalDate to) {
        return !(to.isBefore(startDate) || from.isAfter(endDate));
    }

    @Override
    public String toString() {
        Room room = getRoom();
        String address = (room != null && room.getProperty() != null)
            ? room.getProperty().getAddress()
            : "Unknown property";

        return startDate + " to " + endDate + " (" + bookingStatus + ")\n" + address;
    }
}
