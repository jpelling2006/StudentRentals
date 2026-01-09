package Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Helpers.Helpers;
import Properties.Property;
import Properties.PropertyManager;
import Room.Room;
import FrontEnd.Session;

public class ReviewManager {
    private final StudentReviewManager studentReviewManager;
    private final HomeownerReviewManager homeownerReviewManager;
    private final AdminReviewManager adminReviewManager;

    private Scanner scanner = new Scanner(System.in);
    private Session session;
    private PropertyManager propertyManager;

    public ReviewManager(
        StudentReviewManager studentReviewManager,
        HomeownerReviewManager homeownerReviewManager,
        AdminReviewManager adminReviewManager,
        PropertyManager propertyManager,
        Session session,
        Scanner scanner
    ) {
        this.studentReviewManager = studentReviewManager;
        this.homeownerReviewManager = homeownerReviewManager;
        this.adminReviewManager = adminReviewManager;
        this.propertyManager = propertyManager;
        this.session = session;
        this.scanner = scanner;
    }
    

    public void start() {
        if (!session.isLoggedIn()) {
            System.out.println("Please log in first.");
            return;
        }

        switch (session.getCurrentUser().getUserType()) {
            case STUDENT -> studentMenu();
            case HOMEOWNER -> homeownerMenu();
            case ADMINISTRATOR -> adminMenu();
        }
    }

    private void studentMenu() {
        while (true) {
            System.out.println("\nStudent Review Menu");
            System.out.println("1. Create review");
            System.out.println("2. View reviews");
            System.out.println("3. Edit review");
            System.out.println("4. Delete review");
            System.out.println("5. Back");

            switch (
                Helpers.readIntInRange(
                    scanner, 
                    "Choose option: ", 
                    1, 
                    5
                )
            ) {
                case 1 -> studentReviewManager.createReview();
                case 2 -> studentReviewManager.listReviews(); // create query service
                case 3 -> studentReviewManager.editReview();
                case 4 -> studentReviewManager.deleteReview();
                case 5 -> { return; }
            }
        }
    }

    private void homeownerMenu() {
        while (true) {
            System.out.println("\nHomeowner Review Menu");
            System.out.println("1. View reviews");
            System.out.println("2. Back");

            switch (
                Helpers.readIntInRange(
                    scanner, 
                    "Choose option: ", 
                    1, 
                    2
                )
            ) {
                case 1 -> homeownerReviewManager.listReviews(); // create query service
                case 2 -> { return; }
            }
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Review Menu");
            System.out.println("1. View reviews");
            System.out.println("1. Delete review");
            System.out.println("2. Back");

            switch (
                Helpers.readIntInRange(
                    scanner, 
                    "Choose option: ", 
                    1, 
                    3
                )
            ) {
                case 1 -> adminReviewManager.listReviews(); // create query service
                case 2 -> adminReviewManager.deleteAnyReview();
                case 3 -> { return; }
            }
        }
    }
}
