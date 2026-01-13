package ui;

public class UIContext {
    private UIState state;

    public void setState(UIState state) {
        this.state = state;
    }

    public void run() {
        while (state != null) {
            state.handleRequest(this);
        }
        System.out.println("Exiting, goodbye!");
    }
}