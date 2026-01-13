import bootstrap.DataSeeder;
import ui.LoggedOutState;
import ui.UIContext;

public class Main {
    public static void main(String[] args) {
        DataSeeder.seed();
        
        UIContext uiContext = new UIContext();

        uiContext.setState(LoggedOutState.getInstance());

        uiContext.run();
    }
}