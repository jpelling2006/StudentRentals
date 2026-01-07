import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import FrontEnd.Session;
import Properties.PropertyManager;
import Review.ReviewManager;
import Room.RoomManager;
import User.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<User> users = new ArrayList<>();
        Session session = new Session();

        UserManager userManager = new UserManager(session, scanner);
        PropertyManager propertyManager = new PropertyManager(session, scanner);
        RoomManager roomManager = new RoomManager(propertyManager, session, scanner);
        ReviewManager reviewManager = new ReviewManager(propertyManager, session, scanner);

        while (true) {
            if (!session.isLoggedIn()) {
                System.out.println("\nWelcome to Student Rentals");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");

                Integer choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                    continue;
                }


                switch (choice) {
                    case 1 -> userManager.register();
                    case 2 -> userManager.login();
                    case 3 -> {
                        System.out.println("Goodbye.");
                        return;
                    }
                }
            } else {
                // change to differ for each user
                System.out.println("\nLogged in as " +
                        session.getCurrentUser().getUsername());
                System.out.println("1. My Account");
                System.out.println("2. Properties");
                System.out.println("3. Rooms");
                System.out.println("5. Reviews");
                System.out.println("4. Logout");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> userManager.start();
                    case 2 -> {
                        if (!session.getCurrentUser().getUserType().equals("homeowner")) {
                            System.out.println("Only homeowners can manage properties.");
                        } else { propertyManager.start(); }
                    }
                    case 3 -> {
                        if (!session.getCurrentUser().getUserType().equals("homeowner")) {
                            System.out.println("Only homeowners can manage rooms.");
                        } else { roomManager.start(); }
                    }
                    case 4 -> {
                        if (!session.getCurrentUser().getUserType().equals("student")) {
                            System.out.println("Only students can manage reviews.");
                        } else { reviewManager.start(); }
                    }
                    case 5 -> session.logout();
                }
            }
        }
    }
}
