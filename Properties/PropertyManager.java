package properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import helpers.Helpers;
import session.Session;

public class PropertyManager {
    protected Map<UUID, Property> properties = new HashMap<>();

    private final HomeownerPropertyManager homeownerPropertyManager;
    private final AdminPropertyManager adminPropertyManager;

    protected Scanner scanner = new Scanner(System.in);
    protected Session session;

    public PropertyManager(
        HomeownerPropertyManager homeownerPropertyManager,
        AdminPropertyManager adminPropertyManager,
        Session session, 
        Scanner scanner
    ) {
        this.homeownerPropertyManager = homeownerPropertyManager;
        this.adminPropertyManager = adminPropertyManager;
        this.session = session;
        this.scanner = scanner;
    }

    public void start() {
        if (!session.isLoggedIn()) {
            System.out.println("Please log in first.");
            return;
        }

        switch (session.getCurrentUser().getUserType()) {
            case STUDENT -> System.out.println("Access denied.");
            case HOMEOWNER -> homeownerMenu();
            case ADMINISTRATOR -> adminMenu();
        }
    }

    private void homeownerMenu() {
        while (true) {
            System.out.println("\nHomeowner Property Menu");
            System.out.println("1. Create property");
            System.out.println("2. List properties");
            System.out.println("3. Edit property");
            System.out.println("4. Delete property");
            System.out.println("5. Back");

            switch (
                Helpers.readIntInRange(
                    scanner,
                    "Choose option: ",
                    1,
                    5
                )
            ) {
                case 1 -> homeownerPropertyManager.newProperty();
                case 2 -> homeownerPropertyManager.listProperties();
                case 3 -> homeownerPropertyManager.editProperty();
                case 4 -> homeownerPropertyManager.deleteProperty();
                case 5 -> { return; }
            }
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Property Menu");
            System.out.println("1. List properties");
            System.out.println("2. Delete property");
            System.out.println("3. Back");

            switch (
                Helpers.readIntInRange(
                    scanner,
                    "Choose option: ",
                    1,
                    3
                )
            ) {
                case 1 -> adminPropertyManager.listAllProperties();
                case 2 -> adminPropertyManager.deleteAnyProperty();
                case 3 -> { return; }
            }
        }
    }
}
