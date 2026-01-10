package Properties;

import java.util.List;
import java.util.Scanner;

import Helpers.Helpers;

public class AdminPropertyManager {
    private final PropertyManager propertyManager;
    private final PropertyQueryService propertyQueryService;
    private final Scanner scanner;

    public AdminPropertyManager(PropertyManager propertyManager, PropertyQueryService propertyQueryService, Scanner scanner) {
        this.propertyManager = propertyManager;
        this.propertyQueryService = propertyQueryService;
        this.scanner = scanner;
    }

    public void listAllProperties() {}

    public void deleteAnyProperty() {
        List<Property> properties = propertyQueryService.getAllProperties();
        Property selectedProperty = Helpers.selectFromList(
            scanner, 
            properties, 
            "Select a property to delete"
        );

        if (selectedProperty == null) { return; }

        // fix this
        properties.remove(selectedProperty.getPropertyID()); // what
        System.out.println("Property deleted successfully.");
    }
}
