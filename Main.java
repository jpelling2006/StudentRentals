import booking.BookingStatusScheduler;
import bootstrap.DataSeeder;
import ui.LoggedOutState;
import ui.UIContext;

public class Main {
    public static void main(String[] args) {
        DataSeeder.seed();

        BookingStatusScheduler.start();
        
        UIContext uiContext = new UIContext();

        uiContext.setState(LoggedOutState.getInstance());

        uiContext.run();

        BookingStatusScheduler.stop();
    }
}