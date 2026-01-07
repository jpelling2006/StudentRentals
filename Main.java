import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import FrontEnd.*;
import Properties.PropertyManager;
import Review.ReviewManager;
import Room.RoomManager;
import User.*;
import Helpers.Helpers;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<User> users = new ArrayList<>();
        Session session = new Session();

        UserManager userManager = new UserManager(session, scanner);
        PropertyManager propertyManager = new PropertyManager(session, scanner);
        RoomManager roomManager = new RoomManager(propertyManager, session, scanner);
        ReviewManager reviewManager = new ReviewManager(propertyManager, session, scanner);
        SearchManager searchManager = new SearchManager(propertyManager, session, scanner);

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
                System.out.println("1. My account");
                System.out.println("2. Reviews");
                System.out.println("3. Search for rooms");

                switch (session.getCurrentUser().getUserType()) {
                    case "student" -> {
                        System.out.println("4. Logout");

                        // figure out why this isnt working
                        Integer choice = selectFromList(scanner, 4, "Select choice");

                        switch (choice) {
                            case 1 -> userManager.start();
                            case 2 -> reviewManager.start();
                            case 3 -> searchManager.start(); // complete searchManager
                            case 4 -> session.logout();
                        }
                    }
                    case "homeowner" -> {
                        System.out.println("4. Properties");
                        System.out.println("5. Rooms");
                        System.out.println("6. Logout");

                        Integer choice = selectFromList(scanner, 6, "Select choice");

                        switch (choice) {
                            case 1 -> userManager.start();
                            case 2 -> reviewManager.start();
                            case 3 -> searchManager.start(); // complete searchManager
                            case 4 -> propertyManager.start();
                            case 5 -> roomManager.start();
                            case 6 -> session.logout();
                        }
                    }
                    case "administrator" -> System.out.println("Work in progress."); // so complete it!
                }
            }
        }
    }
}
