package ui;

import java.util.Scanner;

import room.RoomManager;
import session.Session;
import booking.BookingManager;
import helpers.Helpers;
import properties.PropertyManager;
import review.ReviewManager;
import user.UserManager;

public final class AdminState implements UIState {
    private final Scanner scanner = new Scanner(System.in);
    private static AdminState instance;

    public static AdminState getInstance() {
        if (instance == null) { instance = new AdminState(); }
        return instance;
    }

    private AdminState() {}

    @Override
    public void handleRequest(UIContext context) {
        boolean running = true;
        while (running) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. User Manager");
            System.out.println("2. Property manager");
            System.out.println("3. Room manager");
            System.out.println("4. Booking manager");
            System.out.println("5. Review manager");
            System.out.println("6. Exit");

            switch (
                Helpers.readIntInRange(
                    scanner, 
                    "Choose option: ", 
                    1, 
                    6
                )
            ) {
                case 1 -> UserManager.handleManager(context);
                case 2 -> PropertyManager.handleOnce();
                case 3 -> RoomManager.handleOnce();
                case 4 -> BookingManager.handleOnce();
                case 5 -> ReviewManager.handleOnce();
                case 6 -> {
                    context.setState(null);
                    running = false;
                }
            }
            if (Session.isLoggedIn() == false) { running = false; }
        }
        
    }
}
