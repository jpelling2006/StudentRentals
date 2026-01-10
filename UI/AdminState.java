package UI;

public class AdminState implements UIState {
    @Override
    public void handleRequest() {
        System.out.println("Admin!");
    }
}
