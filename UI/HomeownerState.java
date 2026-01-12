package ui;

import java.util.Scanner;

import booking.BookingManager;
import helpers.Helpers;
import properties.PropertyManager;
import review.ReviewManager;
import room.RoomManager;
import user.UserManager;

public class HomeownerState implements UIState {
    private final Scanner scanner = new Scanner(System.in);
    private static HomeownerState instance;

    public static HomeownerState getInstance() {
        if (instance == null) { instance = new HomeownerState(); }
        return instance;
    }

    private HomeownerState() {}

    @Override
    public void handleRequest() {
        System.out.println("\nHomeowner Menu:");
        System.out.println("1. User Manager");
        System.out.println("2. Property manager");
        System.out.println("3. Room manager");
        System.out.println("4. Booking manager");
        System.out.println("5. Review manager");
        System.out.println("6. Exit");

        Integer choice = Helpers.readIntInRange(
            scanner, 
            "Choose option: ", 
            1, 
            6
        );

        switch (choice) {
            case 1 -> UserManager.handleOnce();
            case 2 -> PropertyManager.handleOnce();
            case 3 -> RoomManager.handleOnce();
            case 4 -> BookingManager.handleOnce();
            case 5 -> ReviewManager.handleOnce();
            case 6 -> { return; }
        }
    }
}
