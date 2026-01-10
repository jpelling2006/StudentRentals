package room;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import booking.Booking;
import properties.Property;

public class Room {
    private UUID roomID;
    private Property property;
    private RoomType roomType;
    private Double rentPrice;
    private Boolean billsIncluded;
    private String location; // as in where it is in the house, what floor etc
    private String amenities;
    private LocalDate startDate;
    private LocalDate endDate;
    private Map<String, Booking> bookingsByUser = new HashMap<>();

    public UUID getRoomID() { return roomID; }
    public void generateRoomID() {
        this.roomID = UUID.randomUUID();
    }

    public Property getProperty() { return property; }
    public void setProperty(Property property) {
        if (property == null) {
            throw new IllegalArgumentException("Property required.");
        }
        this.property = property;
    }

    // get to this
    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) {
        if (roomType == null) {
            throw new IllegalArgumentException("Room type is required.");
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

    public Collection<Booking> getBookings() { return bookingsByUser.values(); }

    public Booking getBookingByUser(String username) {
        return bookingsByUser.get(username);
    }

    public void addBooking(Booking booking) {
        String username = booking.getUsername();

        if (bookingsByUser.containsKey(username)) {
            throw new IllegalStateException(
                "You already have a booking for this property"
            );
        }

        if (!isAvailable(booking.getStartDate(), booking.getEndDate())) {
            throw new IllegalStateException("Room is not available for these dates.");
        }

        bookingsByUser.put(username, booking);
    }

    public void removeBooking(String username) { bookingsByUser.remove(username); }

    public boolean hasBooking(String username) { return bookingsByUser.containsKey(username); }

    public boolean bookingCompletedByUser(String username) {
        Booking booking = bookingsByUser.get(username);
        return booking != null && booking.hasEnded();
    }

    public boolean isAvailable(LocalDate from, LocalDate to) {
        for (Booking booking : bookingsByUser.values()) {
            if (booking.overlaps(from, to)) { return false; }
        }
        return true;
    }
}