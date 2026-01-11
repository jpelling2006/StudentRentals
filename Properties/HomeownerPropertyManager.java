package properties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import helpers.Helpers;
import session.Session;

public class HomeownerPropertyManager {
    private final Map<UUID, Property> properties = new HashMap<>();

    private final PropertyQueryService propertyQueryService;
    private final Scanner scanner;
    private final Session session;

    public HomeownerPropertyManager(
        PropertyQueryService propertyQueryService, 
        Scanner scanner, 
        Session session
    ) {
        this.propertyQueryService = propertyQueryService;
        this.scanner = scanner;
        this.session = session;
    }

    public void newProperty() {
        Property property = new Property();

        System.out.println("\nCreating new property");

        property.generatePropertyID();
        property.setUser(session.getCurrentUser());
        property.setCity(
            Helpers.readString(scanner, "Enter city: ", 64)
        );
        property.setAddress(
            Helpers.readString(scanner, "Enter address: ", 512)
        );
        property.setDescription(
            Helpers.readString(
                scanner, 
                "Enter property description: ", 
                2048)
        );
        property.setPropertyType(
            Helpers.readEnum(
                scanner, 
                "Enter property type: ", 
                PropertyType.class
            )
        );
        property.setBedrooms(
            Helpers.readInt(scanner, "Enter bedrooms amount: ")
        );
        property.setBathrooms(
            Helpers.readInt(scanner, "Enter bathrooms amount: ")
        );

        properties.put(property.getPropertyID(), property);
        System.out.println("Property created successfully");
    } 

    public void listProperties() {
        List<Property> userProperties = propertyQueryService.getUserProperties(
            session.getCurrentUser()
        );

        if (userProperties.isEmpty()) {
            System.out.println("You have no properties.");
            return;
        }

        System.out.println("\nYour properties:");
        for (int i = 0; i < userProperties.size(); i++) {
            Property property = userProperties.get(i);
            System.out.println(
                (i+1) + ". "
                + property.getAddress() + " ("
                + property.getPropertyType() + ")"
            );
        }
    }

    public void editProperty() {
        List<Property> userProperties = propertyQueryService.getUserProperties(
            session.getCurrentUser()
        );

        if (userProperties.isEmpty()) {
            System.out.println("You have no properties to edit.");
            return;
        }

        listProperties();

        Property selectedProperty = Helpers.selectFromList(
            scanner,
            userProperties, 
            "Select a property to edit"
        );
        editPropertyMenu(selectedProperty);
    }

    protected void editPropertyMenu(Property property) {
        while (true) {
            System.out.println("\nEditing property: " + property.getAddress());
            System.out.println("1. Address");
            System.out.println("2. Description");
            System.out.println("3. Property type");
            System.out.println("4. Bedrooms");
            System.out.println("5. Bathrooms");
            System.out.println("6. Cancel");

            try {
                Integer choice = Helpers.readIntInRange(
                    scanner, "Enter choice: ", 1, 6
                );

                switch (choice) {
                    case 1 -> property.setAddress(
                        Helpers.readString(
                            scanner, 
                            "Enter new address: ", 
                            512
                        )
                    );
                    case 2 -> property.setDescription(
                        Helpers.readString(
                            scanner, 
                            "Enter new description: ", 
                            2048
                        )
                    );
                    case 3 -> property.setPropertyType(
                        Helpers.readEnum(
                            scanner, 
                            "Enter new property type: ", 
                            PropertyType.class
                        )
                    );
                    case 4 -> property.setBedrooms(
                        Helpers.readInt(scanner, "Enter bedrooms amount: ")
                    );
                    case 5 -> property.setBathrooms(
                        Helpers.readInt(scanner, "Enter bathrooms amount: ")
                    );
                    case 6 -> {
                        System.out.println("Edit cancelled.");
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public void deleteProperty() {
        List<Property> userProperties = propertyQueryService.getUserProperties(
            session.getCurrentUser()
        );

        if (userProperties.isEmpty()) {
            System.out.println("You have no properties to delete.");
            return;
        }

        listProperties();

        Property selectedProperty = Helpers.selectFromList(
            scanner, 
            userProperties, 
            "Select a property to delete"
        );

        if (!confirmDeletion(selectedProperty)) {
            System.out.println("Deletion cancelled.");
            return;
        }

        properties.remove(selectedProperty.getPropertyID());
        System.out.println("Property deleted successfully.");
    }

    private boolean confirmDeletion(Property property) {
        System.out.println("\nAre you sure you want to delete this property?");
        System.out.println(
            property.getAddress()+ " ("
            + property.getPropertyType() + ")"
        );

        return Helpers.confirm(scanner);
    }

    public void start() {
        while (true) {
            System.out.println("\nProperty Management System");
            System.out.println("1. Create property");
            System.out.println("2. List properties");
            System.out.println("3. Edit properties");
            System.out.println("4. Delete properties");
            System.out.println("5. Exit");
            Integer choice = Helpers.readIntInRange(
                scanner, 
                "Enter your choice: ", 
                1, 
                5
            );

            switch (choice) {
                case 1 -> newProperty();
                case 2 -> listProperties();
                case 3 -> editProperty();
                case 4 -> deleteProperty();
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
            }
        }
    }
}
