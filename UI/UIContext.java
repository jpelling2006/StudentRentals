package UI;

public class UIContext {
    private UIState state;

    public void setState(UIState state) {
        this.state = state;
    }

    public void request() {
        state.handleRequest();
    }
}
