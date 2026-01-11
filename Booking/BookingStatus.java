package booking;

public enum BookingStatus {
    PENDING,
    ACCEPTED,
    REJECTED,
    CANCELLED,
    ENDED;

    // true if an eligible booking exists
    public boolean blocksAvailability() {
        return this == PENDING || this == ACCEPTED;
    }
}
