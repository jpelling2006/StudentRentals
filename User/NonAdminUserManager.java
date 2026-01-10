package user;

import java.util.Scanner;

import helpers.Helpers;
import session.Session;

public class NonAdminUserManager {
    private final Scanner scanner;
    private final Session session;

    public NonAdminUserManager(
        Scanner scanner,
        Session session
    ) {
        this.scanner = scanner;
        this.session = session;
    }

    public void viewDetails() {
        User user = session.getCurrentUser();

        System.out.println("\nYour details");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Type: " + user.getUserType());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Phone: " + user.getPhone());

        if (user instanceof StudentUser student) {
            System.out.println("University: " + student.getUniversity());
            System.out.println("Student number: " + student.getStudentNumber());
        }
    }

    public void editDetails() {
        if (!session.isLoggedIn()) {
            System.out.println("No user logged in.");
            return;
        }

        User user = session.getCurrentUser();

        while (true) {
            System.out.println("\nEdit My Details");
            System.out.println("1. Email");
            System.out.println("2. Phone");
            System.out.println("3. Password");

            if (user instanceof StudentUser) {
                System.out.println("4. University");
                System.out.println("5. Student Number");
                System.out.println("6. Cancel");
            } else {
                System.out.println("4. Cancel");
            }

            Integer choice = Helpers.readInt(scanner, "Choose option: ");

            switch (choice) {
                case 1 -> user.setEmail(Helpers.readString(scanner, "Enter new email: ", 64));
                case 2 -> user.setPhone(Helpers.readString(scanner, "Enter new phone number: ", 10));
                case 3 -> {
                    try {
                        user.setPasswordHash(Helpers.readString(scanner, "Enter new password:", 128));
                        System.out.println("Password updated.");
                    } catch (Exception e) { System.out.println("Password update failed."); }
                }
                case 4 -> {
                    if (user instanceof StudentUser student) { student.setUniversity(Helpers.readString(scanner, "Enter new university name: ", 128));}
                    else { return; }
                }
                case 5 -> {
                    if (user instanceof StudentUser student) { student.setStudentNumber(Helpers.readString(scanner, "Enter new student number: ", choice));}
                }
                case 6 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
