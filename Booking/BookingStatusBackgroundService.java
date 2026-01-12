package booking;

public class BookingStatusBackgroundService implements Runnable {
    private volatile static boolean running = true;
    private final static long interval = 6000;
    private static BookingStatusBackgroundService instance;

    public static BookingStatusBackgroundService getInstance() {
        if (instance == null) { instance = new BookingStatusBackgroundService(); }
        return instance;
    }

    public BookingStatusBackgroundService() {}

    // updates bookings every given period
    @Override
    public void run() {
        while (running) {
            try {
                BookingStatusUpdater.updateEndedBookings();
                Thread.sleep(interval);
            } catch (InterruptedException e) { running = false; }
        }
    }

    // if interrupted, stops instead of crashing the application
    public void stop() { running = false; }
}
