package Properties;

import java.util.List;
import java.util.Scanner;

import Helpers.Helpers;

public class AdminPropertyManager extends PropertyManager {
    private final PropertyManager propertyManager;
    private final Scanner scanner;

    public AdminPropertyManager(PropertyManager propertyManager, Scanner scanner) {
        this.propertyManager = propertyManager;
        this.scanner = scanner;
    }

    public void editAnyProperty() {
        List<Property> properties = getAllProperties();
        Property property = Helpers.selectFromList(scanner, properties, "Select a property to edit");
    }

    public void deleteAnyProperty() {
        List<Property> properties = getAllProperties();
        Property selectedProperty = Helpers.selectFromList(scanner, properties, "Select a property to delete");

        if (selectedProperty == null) { return; }

        // fix this
        properties.remove(selectedProperty.getPropertyID());
        System.out.println("Property deleted successfully.");
    }
}
