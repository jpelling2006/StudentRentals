import ui.LoggedOutState;
import ui.UIContext;
import user.UserManager;

public class Main {
    private static UserManager userManager;

    public static void main(String[] args) {
        // create context
        UIContext UI = new UIContext();

        // set init state
        UI.setState(new LoggedOutState(userManager));

        // req state change
        UI.request();

        // change state
        // UI.setState(new HomeownerState());

        // req state change
        UI.request();
    }
}