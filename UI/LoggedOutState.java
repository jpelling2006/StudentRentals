package ui;

import user.UserManager;

public class LoggedOutState implements UIState {
    private static LoggedOutState instance;

    public static LoggedOutState getInstance() {
        if (instance == null) { instance = new LoggedOutState(); }
        return instance;
    }

    public LoggedOutState() {}

    @Override
    public void handleRequest() {
        UserManager.handleOnce();
    }
}
