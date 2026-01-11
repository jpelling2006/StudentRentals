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

        Integer choice = Helpers.readIntInRange(
            scanner, 
            "Choose user type: ", 
            1, 
            2
        );

        UserType type = (choice == 1) ? UserType.STUDENT : UserType.HOMEOWNER;

        UserFactory factory = UserFactoryProvider.getFactory(type);

        User user = factory.createUser();

        String username;
        while (true) {
            username = Helpers.readString(
                scanner, 
                "Username: ", 
                32
            ).toLowerCase();

            if (users.containsKey(username.toLowerCase())) {
                System.out.println("Username already exists.");
                continue;
            }

            break;
        }

        user.setUsername(username);

        user.setEmail(
            Helpers.readString(scanner, "Enter email: ", 64)
        );
        user.setPhone(
            Helpers.readString(scanner, "Enter phone number: ", 10)
        );
        
        while (true) {
            try {
                user.setPasswordHash(
                    Helpers.readString(
                        scanner, 
                        "Enter password (minimum 8 characters): ", 
                        128
                    )
                );
                break;
            } catch (Exception e) {
                System.out.println("Password error.");
            }
        }

        // if user is student, student details are input
        if (user instanceof StudentUser student) { inputStudentDetails(student); }

        users.put(user.getUsername().toLowerCase(), user);
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
