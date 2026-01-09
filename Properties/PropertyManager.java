package Properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import FrontEnd.Session;
import Helpers.Helpers;

public class PropertyManager {
    protected Map<UUID, Property> properties = new HashMap<>();
    protected Scanner scanner = new Scanner(System.in);
    protected Session session;

    public PropertyManager(Session session, Scanner scanner) {
        this.session = session;
        this.scanner = scanner;
    }

    // read
    
    public Property getPropertyByID(UUID propertyID) {
        return properties.get(propertyID);
    }

    public List<Property> getAllProperties() {
        return new ArrayList<>(properties.values());
    }

    public List<Property> getUserProperties(String username) {
        List<Property> userProperties = new ArrayList<>();

        for (Property property : properties.values()) {
            if (property.getUsername().equals(username)) {
                userProperties.add(property);
            }
        }

        return userProperties;
    }

    public void listProperties(List<Property> userProperties) {
        if (userProperties.isEmpty()) {
            System.out.println("You have no properties.");
            return;
        }

        System.out.println("\nYour properties:");
        for (int i = 0; i < userProperties.size(); i++) {
            Property property = userProperties.get(i);
            System.out.println(
                (i+1) + ") "
                + property.getAddress() + " ("
                + property.getPropertyType() + ")"
            );
        }
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
}
