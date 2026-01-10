package ui;

public class HomeownerState implements UIState {
    @Override
    public void handleRequest() {
        System.out.println("Homeowner!");
    }
}
