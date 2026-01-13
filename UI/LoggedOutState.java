package ui;

public class LoggedOutState implements UIState {
    private static LoggedOutState instance;

    public static LoggedOutState getInstance() {
        if (instance == null) { instance = new LoggedOutState(); }
        return instance;
    }

    private LoggedOutState() {}

    @Override
    public void handleRequest(UIContext context) {
        boolean exit = user.UserManager.handleOnce(context);
        if (exit) {
            context.setState(null);
        }
    }
}