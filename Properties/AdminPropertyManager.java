package properties;

import java.util.List;
import java.util.Scanner;

import helpers.Helpers;

public class AdminPropertyManager implements PropertiesHandler {
    private final PropertyQueryService propertyQueryService;
    private final Scanner scanner;

    public AdminPropertyManager(
        PropertyQueryService propertyQueryService, 
        Scanner scanner
    ) {
        this.propertyQueryService = propertyQueryService;
        this.scanner = scanner;
    }

    public void listAllProperties() {
        List<Property> properties = propertyQueryService.getAllProperties();

        if (properties.isEmpty()) {
            System.out.println("There are no properties");
            return;
        }

        // prints list of all properties
        System.out.println("\nAll properties:");
        Helpers.printIndexed(properties, Property::toString);
    }

    public void deleteAnyProperty() {
        List<Property> properties = propertyQueryService.getAllProperties();
        Property selectedProperty = Helpers.selectFromList(
            scanner, 
            properties, 
            "Select a property to delete",
            Property::toString
        );

        if (selectedProperty == null) { return; }

        properties.remove(selectedProperty);
        System.out.println("Property deleted successfully.");
    }

    @Override
    public boolean handleOnce() {
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
