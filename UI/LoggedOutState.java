package ui;

public class LoggedOutState implements UIState {
    @Override
    public void handleRequest() {
        System.out.println("Log in plssss");
    }
}
