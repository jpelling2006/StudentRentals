package Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import Booking.Booking;
import Properties.Property;

public class Room {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);

    private Integer roomID;
    private Property property;
    private String roomType;
    private Double rentPrice;
    private Boolean billsIncluded;
    private String location; // as in where it is in the house, what floor etc
    private String amenities;
    private LocalDate startDate;
    private LocalDate endDate;
    private Map<String, Booking> bookings = new HashMap<>();

    public Integer getRoomID() { return roomID; }
    public void generateRoomID() {
        this.roomID = idGenerator.getAndIncrement();
    }

    public Property getProperty() { return property; }
    public void setProperty(Property property) {
        if (property == null) {
            throw new IllegalArgumentException("Property required.");
        }
        this.property = property;
    }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) {
        if (
            roomType == null
            || (!roomType.equals("single")
            && !roomType.equals("double"))
        ) {
            throw new IllegalArgumentException(
                "Room must be one of the following types: single, double"
            );
        }
        this.roomType = roomType;
    }

    public Double getRentPrice() { return rentPrice; }
    public void setRentPrice(Double rentPrice) {
        if (rentPrice == null || rentPrice <= 0) {
            throw new IllegalArgumentException(
                "RentPrice must be a positive value."
            );
        }
        this.rentPrice = rentPrice;
    }

    public Boolean getBillsIncluded() { return billsIncluded; }
    public void setBillsIncluded(Boolean billsIncluded) {
        if (billsIncluded == null) {
            throw new IllegalArgumentException(
                "BillsIncluded must be either True or False."
            );
        }
        this.billsIncluded = billsIncluded;
    }

    public String getLocation() { return location; }
    public void setLocation(String location) {
        if (location == null || location.length() > 64) {
            throw new IllegalArgumentException(
                "Location must be up to 64 characters long."
            );
        }
        this.location = location;
    }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) {
        if (amenities != null && amenities.length() > 256) {
            throw new IllegalArgumentException(
                "Amenities must be up to 256 characters long."
            );
        }
        this.amenities = amenities;
    }

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

    // also see abt changing this
    private void validateDate(LocalDate start, LocalDate end) {
        LocalDate today = LocalDate.now();

        if (start != null && start.isBefore(today)) {
            throw new IllegalArgumentException("Start date cannot be in the past.");
        }
        if (end != null && end.isBefore(today)) {
            throw new IllegalArgumentException("End date cannot be in the past.");
        }
        if (start != null && end != null && end.isBefore(start)) {
            throw new IllegalArgumentException(
                "End date cannot be before start date."
            );
        }
    }

    // check later, see if getBookings() can still exist similar to the other one

    // public List<Booking> getBookings() { return bookings; }

    public void addBooking(Booking booking) {
        String username = booking.getUsername();

        if (bookings.containsKey(username)) {
            throw new IllegalStateException(
                "You already have a booking for this property"
            );
        }

        if (!isAvailable(booking.getStartDate(), booking.getEndDate())) {
            throw new IllegalStateException("Room is not available for these dates.");
        }

        bookings.put(username, booking);
    }

    public void removeBooking(String username) { bookings.remove(username); }

    public boolean hasBooking(String username) { return bookings.containsKey(username); }

    public boolean bookingCompletedByUser(String username) {
        Booking booking = bookings.get(username);
        return booking != null && booking.hasEnded();
    }

    public boolean isAvailable(LocalDate from, LocalDate to) {
        for (Booking booking : bookings.values()) {
            if (booking.overlaps(from, to)) { return false; }
        }
        return true;
    }
}