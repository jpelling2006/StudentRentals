package booking;

public enum BookingStatus {
    PENDING,
    ACCEPTED,
    REJECTED,
    CANCELLED,
    ENDED;

    public boolean blocksAvailability() {
        return this == PENDING || this == ACCEPTED;
    }
}
