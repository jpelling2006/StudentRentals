package booking;

public class BookingStatusBackgroundService implements Runnable {
    private final BookingStatusUpdater bookingStatusUpdater;
    private volatile boolean running = true;
    private final long interval;

    public BookingStatusBackgroundService(
        BookingStatusUpdater bookingStatusUpdater,
        long interval
    ) {
        this.bookingStatusUpdater = bookingStatusUpdater;
        this.interval = interval;
    }

    // updates bookings every given period
    @Override
    public void run() {
        while (running) {
            try {
                bookingStatusUpdater.updateEndedBookings();
                Thread.sleep(interval);
            } catch (InterruptedException e) { running = false; }
        }
    }

    // if interrupted, stops instead of crashing the application
    public void stop() { running = false; }
}
