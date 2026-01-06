package Booking;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Booking {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);

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
        // add regex
        if (username == null || username.length() > 32) {
            throw new IllegalArgumentException("Username must be up to 32 characters.");
        }
        this.username = username;
    }

    // check if dates have passed
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) {
        validateDate(startDate, this.endDate);
        this.startDate = startDate;
    }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { 
        validateDate(this.startDate, endDate);
        this.endDate = endDate;
    }

    private void validateDate(LocalDate start, LocalDate end) {
        LocalDate today = LocalDate.now();

        if (start != null && start.isBefore(today)) {
            throw new IllegalArgumentException("Start date cannot be in the past.");
        }
        if (end != null && end.isBefore(today)) {
            throw new IllegalArgumentException("End date cannot be in the past.");
        }
        if (start != null && end != null && end.isBefore(start)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }
    }

    public boolean hasEnded() {
        return !endDate.isAfter(LocalDate.now());
    }
}
