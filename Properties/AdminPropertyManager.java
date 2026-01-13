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

        if (selectedProperty == null) { return; }

        selectedProperty.removeProperty(selectedProperty.getPropertyID());
        System.out.println("Property deleted successfully.");
    }

    public static boolean handleOnce() {
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
                listAllProperties();
                yield false;
            }
            case 2 -> {
                deleteAnyProperty();
                yield false;
            }
            case 3 -> true;
            default -> false;
        };
    }
}
