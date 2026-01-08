package Properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import FrontEnd.Session;
import Helpers.Helpers;
import User.User;

public class HomeownerPropertyManager extends PropertyManager {
    private final Map<UUID, Property> properties = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);
    private final Session session;

    public List<Property> getUserProperties(String username) {
        List<Property> userProperties = new ArrayList<>();

        for(Property property : properties.values()) {
            if (property.getUsername().equalsIgnoreCase(username)) {
                userProperties.add(property);
            }
        }

        return userProperties;
    }

    public void newProperty() {
        Property property = new Property();

        System.out.println("\nCreating new property");

        property.generatePropertyID();
        property.setUsername(session.getCurrentUser().getUsername());
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

    public void editProperty() {
        User currentUser = session.getCurrentUser();
        String username = currentUser.getUsername();
        List<Property> userProperties = getUserProperties(username);

        if (userProperties.isEmpty()) {
            System.out.println("You have no properties to edit.");
            return;
        }

        listProperties(userProperties);

        Property selectedProperty = Helpers.selectFromList(
            scanner,
            userProperties, 
            "Select a property to edit"
        );
        editPropertyMenu(selectedProperty);
    }

    public void deleteProperty() {
        String username = session.getCurrentUser().getUsername();
        List<Property> userProperties = getUserProperties(username);

        if (userProperties.isEmpty()) {
            System.out.println("You have no properties to delete.");
            return;
        }

        listProperties(userProperties);

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
                case 2 -> listProperties(
                    getUserProperties(session.getCurrentUser().getUsername())
                );
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
