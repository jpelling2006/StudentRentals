package user;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import booking.BookingHandler;
import properties.PropertiesHandler;
import room.RoomHandler;
import review.ReviewHandler;
import helpers.Helpers;
import session.Session;

public class LoggedOutManager implements UserHandler {

    private final Map<String, User> users = new HashMap<>();
    private final Scanner scanner;
    private final Session session;

    // Handlers to pass to users
    private final BookingHandler bookingHandler;
    private final PropertiesHandler propertiesHandler;
    private final RoomHandler roomHandler;
    private final ReviewHandler reviewHandler;

    public LoggedOutManager(
        Scanner scanner,
        Session session,
        BookingHandler bookingHandler,
        PropertiesHandler propertiesHandler,
        RoomHandler roomHandler,
        ReviewHandler reviewHandler
    ) {
        this.scanner = scanner;
        this.session = session;
        this.bookingHandler = bookingHandler;
        this.propertiesHandler = propertiesHandler;
        this.roomHandler = roomHandler;
        this.reviewHandler = reviewHandler;
    }

    private void register() {
        System.out.println("\nRegister new user:");
        System.out.println("1. Student");
        System.out.println("2. Homeowner");

        int choice = Helpers.readIntInRange(scanner, "Choose user type: ", 1, 2);

        String username;
        while (true) {
            username = Helpers.readString(scanner, "Username: ", 32).toLowerCase();
            if (users.containsKey(username)) {
                System.out.println("Username already exists. Try again.");
                continue;
            }
            break;
        }

        String email = Helpers.readString(scanner, "Email: ", 64);
        String phone = Helpers.readString(scanner, "Phone (10 digits): ", 10);
        String password = Helpers.readString(scanner, "Password (min 8 chars): ", 128);

        User newUser = null;

        try {
            if (choice == 1) { // student
                String university = Helpers.readString(scanner, "University: ", 128);
                String studentNumber = Helpers.readString(scanner, "Student number: ", 32);

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
            } else { // homeowner
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
            System.out.println("Failed to create user: " + e.getMessage());
            return;
        }

        users.put(username, newUser);
        System.out.println("Registration successful! You can now login.");
    }

    private void login() {
        String username = Helpers.readString(scanner, "Username: ", 32).toLowerCase();
        String password = Helpers.readString(scanner, "Password: ", 128);

        User user = users.get(username);
        if (user == null) {
            System.out.println("Username doesn't exist.");
            return;
        }

        try {
            if (user.verifyPassword(password)) {
                session.login(user);
                System.out.println("Login successful!");
            } else {
                System.out.println("Incorrect password.");
            }
        } catch (Exception e) {
            System.out.println("Error verifying password.");
        }
    }

    private void forgetPassword() {
        String username = Helpers.readString(scanner, "Username: ", 32).toLowerCase();
        User user = users.get(username);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        while (true) {
            try {
                String newPassword = Helpers.readString(scanner, "New password: ", 128);
                user.setPasswordHash(newPassword);
                System.out.println("Password reset successful.");
                return;
            } catch (Exception e) {
                System.out.println("Failed to reset password. Try again.");
            }
        }
    }

    @Override
    public boolean handleOnce() {
        System.out.println("\nUser Management System (Logged Out)");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Forgot Password");
        System.out.println("4. Exit");

        return switch (
            Helpers.readIntInRange(scanner, "Choose option: ", 1, 4)
        ) {
            case 1 -> {
                register();
                yield false;
            }
            case 2 -> {
                login();
                yield false;
            }
            case 3 -> {
                forgetPassword();
                yield false;
            }
            case 4 -> true;
            default -> false;
        };
    }
}
