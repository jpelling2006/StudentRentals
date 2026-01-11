package ui;

import java.util.Scanner;

import booking.BookingManager;
import helpers.Helpers;
import review.ReviewManager;
import search.SearchManager;
import user.UserManager;

public class StudentState implements UIState {
    private final UserManager userManager;
    private final SearchManager searchManager;
    private final BookingManager bookingManager;
    private final ReviewManager reviewManager;

    private final Scanner scanner;

    public StudentState(
        UserManager userManager,
        SearchManager searchManager,
        BookingManager bookingManager,
        ReviewManager reviewManager,
        Scanner scanner
    ) {
        this.userManager = userManager;
        this.searchManager = searchManager;
        this.bookingManager = bookingManager;
        this.reviewManager = reviewManager;
        this.scanner = scanner;
    }

    @Override
    public void handleRequest() {
        while (true) {
            System.out.println("\nAdmin Menu:");
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
                case 1 -> userManager.start();
                case 2 -> searchManager.start();
                case 3 -> {  while (!bookingManager.handleOnce()) {} }
                case 4 -> reviewManager.start();
                case 5 -> { return; }
            }
        }
    }
}
