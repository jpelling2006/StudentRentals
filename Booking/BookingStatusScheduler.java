package booking;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BookingStatusScheduler {
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public static void start() {
        // updates ended bookings automatically
        Runnable task = BookingStatusUpdater::updateEndedBookings;

        // init delay 0, repeat every 12 hrs
        scheduler.scheduleAtFixedRate(task, 0, 12, TimeUnit.HOURS);
    }

    // stops service when application shuts down
    public static void stop() { scheduler.shutdownNow(); }
}
