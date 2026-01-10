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

    @Override
    public void run() {
        while (running) {
            try {
                bookingStatusUpdater.updateEndedBookings();
                Thread.sleep(interval);
            } catch (InterruptedException e) { running = false; }
        }
    }

    public void stop() { running = false; }
}
