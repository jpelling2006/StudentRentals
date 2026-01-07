package Booking;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Booking {
    private static final AtomicInteger idGenerator
        = new AtomicInteger(1);

    private Integer bookingID;
    private String username; // students only
    private LocalDate startDate;
    private LocalDate endDate;

    public Integer getBookingID() { return bookingID; }
    public void generateBookingID() {
        this.bookingID = idGenerator.getAndIncrement();
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
}
