import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PropertyManager {
    private List<User> users = new ArrayList<>();
    private List<Property> properties = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    // put into helpers class
    private boolean usernameExists(String username) {
        for (User user : users) {
            if (username.equalsIgnoreCase(user.getUsername())) { return true; }
        }
        return false;
    }

    public void inputUsername(Property property) {
        while (true) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            if (!usernameExists(username)) {
                System.out.println("Username doesn't exist.");
                continue;
            }

            try {
                property.setUsername(username);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
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
        Integer choice;

        while (true) {
            System.out.println("Please pick from the following property types:\n1. House\n2. Flat");
            System.out.print("Enter choice (1/2): ");

            String input = scanner.nextLine();

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
                continue;
            }

            if (choice == 1) {
                property.setPropertyType("house");
                System.out.println("Property type set.");
                break;
            } else if (choice == 2) {
                property.setPropertyType("flat");
                System.out.println("Property type set.");
                break;
            } else {
                System.out.println("Please enter either 1 or 2.");
            }
        }
    }

    private void inputBedrooms(Property property) {
        while (true) {
            System.out.print("Number of bedrooms: ");
            try {
                int bedrooms = Integer.parseInt(scanner.nextLine());
                property.setBedrooms(bedrooms);
                System.out.println("Bedrooms set.");
                return;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void inputBathrooms(Property property) {
        while (true) {
            System.out.print("Number of bathrooms: ");
            try {
                int bathrooms = Integer.parseInt(scanner.nextLine());
                property.setBathrooms(bathrooms);
                System.out.println("Bathrooms set.");
                return;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
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
        inputUsername(property);
        inputAddress(property);
        inputDescription(property);
        inputPropertyType(property);
        inputBedrooms(property);
        inputBathrooms(property);

        properties.add(property);
        System.out.println("Property created successfully");
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

    public void listProperties(List<Property> userProperties) {
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
                + property.getPropertyType() + ") "
            );
        }
    }

    public void editProperty(String username){
        List<Property> userProperties = getUserProperties(username);

        listProperties(userProperties);

        // select property to edit
        int choice;
        while (true) {
            System.out.print("Select a property to edit (1-" + userProperties.size() + "): ");
            try {
                choice = Integer.parseInt(scanner.nextLine()); // do this for others
                if (choice < 1 || choice > userProperties.size()) {
                    System.out.println("Invalid selection.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

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
    
    public void deleteProperty(String username) {
        List<Property> userProperties = getUserProperties(username);

        if (userProperties.isEmpty()) {
            System.out.println("You have no properties to delete.");
            return;
        }

        // display properties
        System.out.println("\nYour properties:");
        for (int i = 0; i < userProperties.size(); i++) {
            Property property = userProperties.get(i);
            System.out.println(
                (i + 1) + ". " +
                property.getAddress() + " (" +
                property.getPropertyType() + ")"
            );
        }

        // select property
        int choice;
        while (true) {
            System.out.print("Select a property to delete (1-" + userProperties.size() + "): ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > userProperties.size()) {
                    System.out.println("Invalid selection.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        Property selectedProperty = userProperties.get(choice - 1);

        // Confirm deletion
        if (!confirmDeletion(selectedProperty)) {
            System.out.println("Deletion cancelled.");
            return;
        }

        // Remove from main list
        properties.remove(selectedProperty);
        System.out.println("Property deleted successfully.");
    }

    private boolean confirmDeletion(Property property) {
        while (true) {
            System.out.println("\nAre you sure you want to delete this property?");
            System.out.println(property.getAddress() + " (" + property.getPropertyType() + ")");
            System.out.print("Type Y to confirm or N to cancel: ");

            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            } else {
                System.out.println("Please enter Y or N.");
            }
        }
    }

    private String promptUsername() {
        while (true) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            if (usernameExists(username)) return username;
            System.out.println("Username doesn't exist.");
        }
    }


    public void start() {
        while (true) {
            System.out.println("\nProperty Management System");
            System.out.println("1. Create property");
            System.out.println("2. List properties");
            System.out.println("3. Edit properties");
            System.out.println("4. Delete properties");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    newProperty();
                    break;
                case 2:
                    // get current username
                    listProperties(getUserProperties(promptUsername()));
                    break;
                case 3:
                    editProperty(promptUsername());
                    break;
                case 4:
                    deleteProperty(promptUsername());
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

    public static void main(String[] args) {
        PropertyManager manager = new PropertyManager();
        manager.start();
    }
}
