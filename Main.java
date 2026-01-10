import ui.HomeownerState;
import ui.StudentState;
import ui.UIContext;

public class Main {
    public static void main(String[] args) {
        // create context
        UIContext UI = new UIContext();

        // set init state
        UI.setState(new StudentState());

        // req state change
        UI.request();

        // change state
        UI.setState(new HomeownerState());

        // req state change
        UI.request();
    }
}