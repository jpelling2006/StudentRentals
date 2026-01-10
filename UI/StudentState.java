package ui;

public class StudentState implements UIState {
    @Override
    public void handleRequest() {
        System.out.println("Student!");
    }
}
