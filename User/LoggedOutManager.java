package user;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import booking.BookingHandler;
import helpers.Helpers;
import properties.PropertiesHandler;
import review.ReviewHandler;
import room.RoomHandler;
import session.Session;

public class LoggedOutManager {
    private Map<String, User> users = new HashMap<>();
    private final Scanner scanner;
    private final Session session;
    private final BookingHandler bookingHandler;
    private final ReviewHandler reviewHandler;
    private final PropertiesHandler propertiesHandler;
    private final RoomHandler roomHandler;

    public LoggedOutManager(
        Scanner scanner,
        Session session,
        BookingHandler bookingHandler,
        ReviewHandler reviewHandler,
        PropertiesHandler propertiesHandler,
        RoomHandler roomHandler
    ) {
        this.scanner = scanner;
        this.session = session;
        this.bookingHandler = bookingHandler;
        this.reviewHandler = reviewHandler;
        this.propertiesHandler = propertiesHandler;
        this.roomHandler = roomHandler;
    }

    public void register() {
        System.out.println("\nRegister new user");
        System.out.println("1. Student");
        System.out.println("2. Homeowner");

        int choice = Helpers.readIntInRange(scanner, "Choose user type: ", 1, 2);
        boolean isStudent = choice == 1;

        // common info
        String username;
        while (true) {
            username = Helpers.readString(scanner, "Username: ", 32).toLowerCase();
            if (users.containsKey(username)) {
                System.out.println("Username already exists.");
            } else break;
        }

        String email = Helpers.readString(scanner, "Enter email: ", 64);
        String phone = Helpers.readString(scanner, "Enter phone number: ", 10);
        String password = Helpers.readString(scanner, "Enter password (minimum 8 characters): ", 128);

        User newUser = null;

        try {
            if (isStudent) {
                String university = Helpers.readString(scanner, "Enter university: ", 128);
                String studentNumber = Helpers.readString(scanner, "Enter student number: ", 32);

                newUser = new StudentUser(
                    username,
                    email,
                    phone,
                    password,
                    university,
                    studentNumber,
                    bookingHandler,
                    reviewHandler
                );
            } else {
                newUser = new HomeownerUser(
                    username,
                    email,
                    phone,
                    password,
                    bookingHandler,
                    propertiesHandler,
                    roomHandler,
                    reviewHandler
                );
            }
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
            return;
        }

        users.put(username, newUser);
        System.out.println("Registration successful.");
    }


    public void login() {
        String username = Helpers.readString(
            scanner, 
            "Username: ", 
            32
        ).toLowerCase();
        String password = Helpers.readString(
            scanner, 
            "Password: ", 
            128
        );

        User user = users.get(username);

        if (user == null) {
            System.out.println("Username doesn't exist.");
            return;
        }

        try {
            if (user.verifyPassword(password)) {
                session.login(user);
                System.out.println("Login successful.");
            } else {
                System.out.println("Login failed.");
            }
        } catch (Exception e) { System.out.println("Login error."); }
    }

    public void forgetPassword() {
        String username = Helpers.readString(
            scanner, 
            "Username: ", 
            32
        ).toLowerCase();
        User user = users.get(username);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        while (true) {
            try {
                user.setPasswordHash(
                    Helpers.readString(
                        scanner, 
                        "New password: ", 
                        128
                    )
                );
                System.out.println("Password reset.");
                return;
            } catch (Exception e) { System.out.println("Password error."); }
        }
    }
}
