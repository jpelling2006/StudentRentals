package ui;

import java.util.Scanner;

import room.RoomManager;
import booking.BookingManager;
import helpers.Helpers;
import properties.PropertyManager;
import review.ReviewManager;
import user.UserManager;

public class AdminState implements UIState {
    private final UserManager userManager;
    private final PropertyManager propertyManager;
    private final RoomManager roomManager;
    private final BookingManager bookingManager;
    private final ReviewManager reviewManager;

    private final Scanner scanner;

    public AdminState(
        UserManager userManager,
        PropertyManager propertyManager,
        RoomManager roomManager,
        BookingManager bookingManager,
        ReviewManager reviewManager,
        Scanner scanner
    ) {
        this.userManager = userManager;
        this.propertyManager = propertyManager;
        this.roomManager = roomManager;
        this.bookingManager = bookingManager;
        this.reviewManager = reviewManager;
        this.scanner = scanner;
    }

    @Override
    public void handleRequest() {
        while (true) {
            System.out.println("\nAdmin Menu:");
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
                case 1 -> userManager.start();
                case 2 -> propertyManager.start();
                case 3 -> roomManager.start();
                case 4 -> bookingManager.start();
                case 5 -> reviewManager.start();
                case 6 -> { return; }
            }
        }
    }
}
