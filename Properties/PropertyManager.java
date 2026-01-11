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

    public boolean handleOnce() {
        if (!session.isLoggedIn()) {
            System.out.println("Please log in first.");
            return true;
        }

        return switch (session.getCurrentUser().getUserType()) {
            case STUDENT -> {
                System.out.println("Access denied.");
                yield true;
            }
            case HOMEOWNER -> handleHomeowner();
            case ADMINISTRATOR -> handleAdmin();
        };
    }

    private boolean handleHomeowner() {
        System.out.println("\nHomeowner Property Menu");
        System.out.println("1. Create property");
        System.out.println("2. List properties");
        System.out.println("3. Edit property");
        System.out.println("4. Delete property");
        System.out.println("5. Back");

        return switch (
            Helpers.readIntInRange(
                scanner,
                "Choose option: ",
                1,
                5
            )
        ) {
            case 1 -> {
                homeownerPropertyManager.newProperty();
                yield false;
            }
            case 2 -> {
                homeownerPropertyManager.listUserProperties();
                yield false;
            }
            case 3 -> {
                homeownerPropertyManager.editProperty();
                yield false;
            }
            case 4 -> {
                homeownerPropertyManager.deleteProperty();
                yield false;
            }
            case 5 -> true;
            default -> false;
        };
    }

    private boolean handleAdmin() {
        System.out.println("\nAdmin Property Menu");
        System.out.println("1. List properties");
        System.out.println("2. Delete property");
        System.out.println("3. Back");

        return switch (
            Helpers.readIntInRange(
                scanner,
                "Choose option: ",
                1,
                3
            )
        ) {
            case 1 -> {
                adminPropertyManager.listAllProperties();
                yield false;
            }
            case 2 -> {
                adminPropertyManager.deleteAnyProperty();
                yield false;
            }
            case 3 -> true;
            default -> false;
        };
    }
}
