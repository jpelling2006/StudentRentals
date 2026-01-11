package properties;

import java.util.List;
import java.util.Scanner;

import helpers.Helpers;

public class AdminPropertyManager {
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
            "Select a property to delete"
        );

        if (selectedProperty == null) { return; }

        properties.remove(selectedProperty);
        System.out.println("Property deleted successfully.");
    }
}
