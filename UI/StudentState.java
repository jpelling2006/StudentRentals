package ui;

import java.util.Scanner;

import booking.BookingManager;
import helpers.Helpers;
import review.ReviewManager;
import search.SearchManager;
import session.Session;
import user.UserManager;

public class StudentState implements UIState {
    private final Scanner scanner = new Scanner(System.in);
    private static StudentState instance;

    public static StudentState getInstance() {
        if (instance == null) { instance = new StudentState(); }
        return instance;
    }

    private StudentState() {}

    @Override
    public void handleRequest(UIContext context) {
        boolean running = true;
        while (running) {
            System.out.println("\nStudent Menu:");
            System.out.println("1. User Manager");
            System.out.println("2. Room searcher");
            System.out.println("3. Booking manager");
            System.out.println("4. Review manager");
            System.out.println("5. Exit");

            Integer choice = Helpers.readIntInRange(
                scanner, 
                "Choose option: ", 
                1, 
                5
            );

            switch (choice) {
                case 1 -> UserManager.handleManager(context);
                case 2 -> SearchManager.handleOnce();
                case 3 -> BookingManager.handleOnce();
                case 4 -> ReviewManager.handleOnce();
                case 5 -> {
                    context.setState(null);
                    running = false;
                }
            }

            if (Session.isLoggedIn() == false) {
                running = false;
            }
        }
    }
}
