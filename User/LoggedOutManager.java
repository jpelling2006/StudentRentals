package user;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import helpers.Helpers;
import session.Session;

public class LoggedOutManager {
    private Map<String, User> users = new HashMap<>();
    private final Scanner scanner;
    private final Session session;

    public LoggedOutManager(
        Scanner scanner,
        Session session
    ) {
        this.scanner = scanner;
        this.session = session;
    }

    private void inputStudentDetails(StudentUser student) {
        student.setUniversity(
            Helpers.readString(scanner, "Enter university: ", 128)
        );
        student.setStudentNumber(
            Helpers.readString(
                scanner, 
                "Enter student number: ", 
                32
            )
        );
    }

    public void register() {
        System.out.println("\nRegister new user");
        System.out.println("1. Student");
        System.out.println("2. Homeowner");

        Integer choice = Helpers.readIntInRange(scanner, "Choose user type: ", 1, 2);

        UserFactory factory;
        boolean isStudent = choice == 1;
        if (isStudent) {
            factory = StudentUserFactory.getInstance();
        } else {
            factory = HomeownerUserFactory.getInstance();
        }

        // common user info
        String username;
        while (true) {
            username = Helpers.readString(scanner, "Username: ", 32).toLowerCase();
            if (users.containsKey(username)) {
                System.out.println("Username already exists.");
                continue;
            }
            break;
        }

        String email = Helpers.readString(scanner, "Enter email: ", 64);
        String phone = Helpers.readString(scanner, "Enter phone number: ", 10);

        String password;
        while (true) {
            try {
                password = Helpers.readString(scanner, "Enter password (minimum 8 characters): ", 128);
                break;
            } catch (Exception e) {
                System.out.println("Password error. Try again.");
            }
        }

        // student-specific info
        String university = null;
        String studentNumber = null;
        if (isStudent) {
            university = Helpers.readString(scanner, "Enter university: ", 128);
            studentNumber = Helpers.readString(scanner, "Enter student number: ", 32);
        }

        // create user via factory
        User user = factory.createUser(
            username,
            email,
            phone,
            password,
            university,
            studentNumber
        );

        users.put(username, user);

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
