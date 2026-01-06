package Properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import FrontEnd.Session;
import Helpers.Helpers;

public class PropertyManager {
    private List<Property> properties = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private Session session;

    public PropertyManager(Session session, Scanner scanner) {
        this.session = session;
        this.scanner = scanner;
    }

    private void inputAddress(Property property) {
        while (true) {
            System.out.print("New address: ");
            String address = scanner.nextLine();

            try {
                property.setAddress(address);
                System.out.println("Address set.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void inputDescription(Property property) {
        while (true) {
            System.out.print("New description: ");
            String description = scanner.nextLine();

            try {
                property.setDescription(description);
                System.out.println("Description set.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void inputPropertyType(Property property) {
        while (true) {
            System.out.println("Please pick from the following property types:");
            System.out.println("1. House");
            System.out.println("2. Flat");

            Integer choice = Helpers.readInt(scanner, "Enter choice (1/2): ");

            if (choice == 1) {
                property.setPropertyType("house");
                System.out.println("Property type set.");
                return;
            } else if (choice == 2) {
                property.setPropertyType("flat");
                System.out.println("Property type set.");
                return;
            } else {
                System.out.println("Please enter either 1 or 2.");
            }
        }
    }

    private void inputBedrooms(Property property) {
        while (true) {
            Integer bedrooms = Helpers.readInt(scanner, "Number of bedrooms: ");
            try {
                property.setBedrooms(bedrooms);
                System.out.println("Bedrooms set.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void inputBathrooms(Property property) {
        while (true) {
            Integer bathrooms = Helpers.readInt(scanner, "Number of bathrooms: ");
            try {
                property.setBathrooms(bathrooms);
                System.out.println("Bathrooms set.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // add new property
    public void newProperty() {
        Property property = new Property();

        System.out.println("\nCreating new property");

        property.generatePropertyID();
        property.setUsername(session.getCurrentUser().getUsername());
        inputAddress(property);
        inputDescription(property);
        inputPropertyType(property);
        inputBedrooms(property);
        inputBathrooms(property);

        properties.add(property);
        System.out.println("Property created successfully");
    } 

    public List<Property> getAllProperties() {
        return properties;
    }

    public List<Property> getUserProperties(String username) {
        List<Property> userProperties = new ArrayList<>();

        for(Property property : properties) {
            if (property.getUsername().equalsIgnoreCase(username)) {
                userProperties.add(property);
            }
        }

        return userProperties;
    }

    public Property getPropertyByID(Integer propertyID) {
        for (Property property : properties) {
            if (property.getPropertyID().equals(propertyID)) { return property; }
        }
        return null;
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

    public void editProperty() {
        User currentUser = session.getCurrentUser();
        String username = currentUser.getUsername();
        List<Property> userProperties = getUserProperties(username);

        if (userProperties.isEmpty()) {
            System.out.println("You have no properties to edit.");
            return;
        }


        listProperties(userProperties);

        int choice = Helpers.selectFromList(scanner, userProperties.size(), "Select a property to edit");

        Property selectedProperty = userProperties.get(choice - 1);
        editPropertyMenu(selectedProperty);
    }

    private void editPropertyMenu(Property property) {
        while (true) {
            System.out.println("\nEditing property: " + property.getAddress());
            System.out.println("1. Address");
            System.out.println("2. Description");
            System.out.println("3. Property type");
            System.out.println("4. Bedrooms");
            System.out.println("5. Bathrooms");
            System.out.println("6. Cancel");

            System.out.print("Choose a field to edit: ");
            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1 -> inputAddress(property);
                    case 2 -> inputDescription(property);
                    case 3 -> inputPropertyType(property);
                    case 4 -> inputBedrooms(property);
                    case 5 -> inputBathrooms(property);
                    case 6 -> {
                        System.out.println("Edit cancelled.");
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
    
    public void deleteProperty() {
        String username = session.getCurrentUser().getUsername();
        List<Property> userProperties = getUserProperties(username);

        if (userProperties.isEmpty()) {
            System.out.println("You have no properties to delete.");
            return;
        }

        listProperties(userProperties);

        int choice = Helpers.selectFromList(
            scanner, userProperties.size(),
            "Select a property to delete"
        );

        Property selectedProperty = userProperties.get(choice - 1);

        if (!confirmDeletion(selectedProperty)) {
            System.out.println("Deletion cancelled.");
            return;
        }

        properties.remove(selectedProperty);
        System.out.println("Property deleted successfully.");
    }

    private boolean confirmDeletion(Property property) {
        System.out.println("\nAre you sure you want to delete this property?");
        System.out.println(property.getAddress() + " (" + property.getPropertyType() + ")");

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
            Integer choice = Helpers.readInt(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    newProperty();
                    break;
                case 2:
                    listProperties(
                        getUserProperties(session.getCurrentUser().getUsername())
                    );
                    break;
                case 3:
                    editProperty();
                    break;
                case 4:
                    deleteProperty();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please select an integer between 1-5.");
                    break;
            }
        }
    }
}
