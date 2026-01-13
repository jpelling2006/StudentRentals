package properties;

import java.util.List;
import java.util.Scanner;

import helpers.Helpers;

public final class AdminPropertyManager implements PropertiesHandler {
    private final static Scanner scanner = new Scanner(System.in);
    private static AdminPropertyManager instance;

    public static AdminPropertyManager getInstance() {
        if (instance == null) { instance = new AdminPropertyManager(); }
        return instance;
    }

    private AdminPropertyManager() {}

    private static void listAllProperties() {
        List<Property> properties = PropertyQueryService.getAllProperties();

        if (properties.isEmpty()) {
            System.out.println("There are no properties");
            return;
        }

        // prints list of all properties
        System.out.println("\nAll properties:");
        Helpers.printIndexed(properties, Property::toString);
    }

    private static void deleteAnyProperty() {
        List<Property> properties = PropertyQueryService.getAllProperties();
        Property selectedProperty = Helpers.selectFromList(
            scanner, 
            properties, 
            "Select a property to delete",
            Property::toString
        );

        if (selectedProperty == null) {
            System.out.println("Property doesn't exist.");
            return;
        }

        System.out.println("Are you sure you want to delete this booking?");
        System.out.println(selectedProperty.toString());

        PropertyQueryService.removeFromIndex(selectedProperty);
        selectedProperty.removeProperty(selectedProperty.getPropertyID());
        System.out.println("Property deleted successfully.");
    }

    protected static boolean handleOnce() {
        boolean running = true;
        while (running) {
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
                case 1 -> listAllProperties();
                case 2 -> deleteAnyProperty();
                case 3 -> running = false;
            }
        }
        return true;
    }
}
