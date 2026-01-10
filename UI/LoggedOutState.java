package ui;

import user.UserManager;

public class LoggedOutState implements UIState {
    private final UserManager userManager;

    public LoggedOutState(
        UserManager userManager
    ) {
        this.userManager = userManager;
    }

    @Override
    public void handleRequest() {
        userManager.start();
    }
}
