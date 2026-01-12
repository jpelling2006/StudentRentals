package properties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import helpers.Helpers;
import session.Session;

public final class HomeownerPropertyManager implements PropertiesHandler {
    private final static Map<UUID, Property> properties = new HashMap<>();
    private final static Scanner scanner = new Scanner(System.in);
    private static HomeownerPropertyManager instance;

    public static HomeownerPropertyManager getInstance() {
        if (instance == null) { instance = new HomeownerPropertyManager(); }
        return instance;
    }

    private HomeownerPropertyManager() {}

    private static void newProperty() {
        Property property = new Property();

        System.out.println("\nCreating new property");

        property.generatePropertyID();
        property.setUser(Session.getCurrentUser());
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

    private static void listUserProperties() {
        List<Property> userProperties = PropertyQueryService.getUserProperties(
            Session.getCurrentUser()
        );

        if (userProperties.isEmpty()) {
            System.out.println("You have no properties.");
            return;
        }

        System.out.println("\nYour properties:");
        Helpers.printIndexed(userProperties, Property::toString);
    }

    private static void editProperty() {
        List<Property> userProperties = PropertyQueryService.getUserProperties(
            Session.getCurrentUser()
        );

        if (userProperties.isEmpty()) {
            System.out.println("You have no properties to edit.");
            return;
        }

        Property selectedProperty = Helpers.selectFromList(
            scanner,
            userProperties, 
            "Select a property to edit",
            Property::toString
        );
        editPropertyMenu(selectedProperty);
    }

    private static void editPropertyMenu(Property property) {
        while (true) {
            System.out.println("\nEditing property: " + property.getAddress());
            System.out.println("1. Address");
            System.out.println("2. Description");
            System.out.println("3. Property type");
            System.out.println("4. Bedrooms");
            System.out.println("5. Bathrooms");
            System.out.println("6. Cancel");

            switch (
                Helpers.readIntInRange(
                    scanner, "Enter choice: ", 1, 6
                )
            ) {
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
        }
    }

    private static void deleteProperty() {
        List<Property> userProperties = PropertyQueryService.getUserProperties(
            Session.getCurrentUser()
        );

        if (userProperties.isEmpty()) {
            System.out.println("You have no properties to delete.");
            return;
        }

        Property selectedProperty = Helpers.selectFromList(
            scanner, 
            userProperties, 
            "Select a property to delete",
            Property::toString
        );

        if (!confirmDeletion(selectedProperty)) {
            System.out.println("Deletion cancelled.");
            return;
        }

        properties.remove(selectedProperty.getPropertyID());
        System.out.println("Property deleted successfully.");
    }

    // remove this
    private static boolean confirmDeletion(Property property) {
        System.out.println("\nAre you sure you want to delete this property?");
        System.out.println(
            property.getAddress()+ " ("
            + property.getPropertyType() + ")"
        );

        return Helpers.confirm(scanner);
    }

    public static boolean handleOnce() {
        System.out.println("\nHomeowner Property Menu");
        System.out.println("1. Create property");
        System.out.println("2. List properties");
        System.out.println("3. Edit property");
        System.out.println("4. Delete property");
        System.out.println("5. Back");

        return switch (
            Helpers.readIntInRange(
                scanner,
                "Choose option: ",
                1,
                5
            )
        ) {
            case 1 -> {
                newProperty();
                yield false;
            }
            case 2 -> {
                listUserProperties();
                yield false;
            }
            case 3 -> {
                editProperty();
                yield false;
            }
            case 4 -> {
                deleteProperty();
                yield false;
            }
            case 5 -> true;
            default -> false;
        };
    }
}
