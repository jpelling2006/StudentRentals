package Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Helpers.*;
import Properties.*;
import FrontEnd.Session;

public class RoomManager {
    private Scanner scanner = new Scanner(System.in);
    private Session session;
    private PropertyManager propertyManager;

    public RoomManager(PropertyManager propertyManager, Session session, Scanner scanner) {
        this.propertyManager = propertyManager;
        this.session = session;
        this.scanner = scanner;
    }

    public void inputProperty(Room room) {
        List<Property> userProperties = propertyManager.getUserProperties(session.getCurrentUser().getUsername());

        if (userProperties.isEmpty()) {
            System.out.println("You have no properties.");
            return;
        }

        for (int i = 0; i < userProperties.size(); i++) {
            System.out.println((i + 1) + ". " + userProperties.get(i).getAddress());
        }

        int choice = Helpers.selectFromList(scanner, userProperties.size(), "Select property");

        Property selectedProperty = userProperties.get(choice - 1);
        room.setProperty(selectedProperty);
    }

    public void inputRoomType(Room room) {
        Integer choice;
        
        while (true) {
            System.out.println("Please pick from the following room types: ");
            System.out.println("1. Single");
            System.out.println("2. Double");
            System.out.print("Enter choice (1/2): ");

            String input = scanner.nextLine();
            
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
                continue;
            }

            if (choice == 1) {
                room.setRoomType("single");
                System.out.println("Room type set.");
                break;
            } else if (choice == 2) {
                room.setRoomType("double");
                System.out.println("Room type set.");
                break;
            } else {
                System.out.println("Please enter either 1 or 2.");
            }
        }
    }

    private void inputRentPrice(Room room) {
        while (true) {
            Double rentPrice = Helpers.readDouble(scanner, "Rent price per week: ");

            try {
                room.setRentPrice(rentPrice);
                System.out.println("Rent price set.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void inputBillsIncluded(Room room) {
        System.out.println("Are bills included?");
        boolean included = Helpers.confirm(scanner);
        room.setBillsIncluded(included);
        System.out.println("Bills updated.");
    }


    public void inputLocation(Room room) {
        while (true) {
            System.out.print("Enter room's location in property: ");
            String location = scanner.nextLine();

            try {
                room.setLocation(location);
                System.out.println("Room location set.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void inputAmenities(Room room) {
        while (true) {
            System.out.print("Enter amenities: ");
            String amenities = scanner.nextLine();

            try {
                room.setAmenities(amenities);
                System.out.println("Amenities set.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void inputStartDate(Room room) {
        LocalDate startDate = Helpers.readFutureDate(scanner, "Availability start date");
        room.setStartDate(startDate);
        System.out.println("Start date set.");
    }

    public void inputEndDate(Room room) {
        while (true) {
            LocalDate endDate = Helpers.readFutureDate(scanner, "Availability end date");

            if (endDate.isBefore(room.getStartDate())) {
                System.out.println("End date must be after the start date.");
                continue;
            }

            room.setEndDate(endDate);
            System.out.println("End date set.");
            return;
        }
    }


    public void newRoom() {
        if (!session.getCurrentUser().getUserType().equals("homeowner")) {
            System.out.println("Only homeowners can add rooms.");
            return;
        }

        Room room = new Room();

        System.out.println("\nCreating new room");

        room.generateRoomID();
        inputProperty(room);
        if (room.getProperty() == null) {
            System.out.println("Room creation cancelled.");
            return;
        }
        inputRoomType(room);
        inputRentPrice(room);
        inputBillsIncluded(room);
        inputLocation(room);
        inputAmenities(room);
        inputStartDate(room);
        inputEndDate(room);

        room.getProperty().addRoom(room);
        System.out.println("Room created successfully");
    }

    public List<Room> getUserRooms() {
        List<Room> userRooms = new ArrayList<>();

        for (
            Property property : propertyManager.getUserProperties(session.getCurrentUser().getUsername())) {
            userRooms.addAll(property.getRooms());
        }

        return userRooms;
    }

    public void listRooms(List<Room> userRooms) {
        if (userRooms.isEmpty()) {
            System.out.println("You have no rooms.");
            return;
        }

        System.out.println("\nYour rooms:");
        for (int i = 0; i < userRooms.size(); i++) {
            Room room = userRooms.get(i);
            Property property = room.getProperty();
            System.out.println(
                (i + 1) + ". "
                + property.getAddress() + " - "
                + room.getLocation() + " ("
                + room.getRoomType() + ")"
            );
        }
    }    

    public void editRoom() {
        List<Room> userRooms = getUserRooms();

        if (userRooms.isEmpty()) {
            System.out.println("You have no rooms to edit.");
            return;
        }

        listRooms(userRooms);

        int choice = Helpers.selectFromList(scanner, userRooms.size(), "Select a room to edit");

        Room selectedRoom = userRooms.get(choice - 1);
        editRoomMenu(selectedRoom);
    }

    private void editRoomMenu(Room room) {
        while (true) {
            Property property = room.getProperty();
            String address = (property != null) ? property.getAddress() : "Unknown property";

            System.out.println(
                "\nEditing room: "
                + address + " - "
                + room.getLocation()
            );
            System.out.println("1. Room Type");
            System.out.println("2. Rent price");
            System.out.println("3. Bills included");
            System.out.println("4. Room location");
            System.out.println("5. Amenities");
            System.out.println("6. Availability start date");
            System.out.println("7. Availability end date");
            System.out.println("8. Cancel");
            
            Integer choice = Helpers.readInt(scanner, "Choose a field to edit: ");
                
            switch (choice) {
                case 1 -> inputRoomType(room);
                case 2 -> inputRentPrice(room);
                case 3 -> inputBillsIncluded(room);
                case 4 -> inputLocation(room);
                case 5 -> inputAmenities(room);
                case 6 -> inputStartDate(room);
                case 7 -> inputEndDate(room);
                case 8 -> {
                    System.out.println("Edit cancelled.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    public void deleteRoom() {
        List<Room> userRooms = getUserRooms();

        if (userRooms.isEmpty()) {
            System.out.println("You have no rooms to delete");
            return;
        }

        listRooms(userRooms);

        int choice = Helpers.selectFromList(scanner, userRooms.size(), "Select a room to delete");

        Room selectedRoom = userRooms.get(choice - 1);

        if (!confirmDeletion(selectedRoom)) {
            System.out.println("Deletion cancelled");
            return;
        }

        selectedRoom.getProperty().removeRoom(selectedRoom);
        System.out.println("Room deleted successfully.");
    }

    private boolean confirmDeletion(Room room) {
        Property property = room.getProperty();
        String address = (property != null) ? property.getAddress() : "Unknown property";

        System.out.println("\nAre you sure you want to delete this room?");
        System.out.println(address + " (" + room.getLocation() + ", " + room.getRoomType() + ")");

        return Helpers.confirm(scanner);
    }


    public void start() {
        while (true) {
            System.out.println("\nRoom Management System");
            System.out.println("1. Create room");
            System.out.println("2. List rooms");
            System.out.println("3. Edit rooms");
            System.out.println("4. Delete rooms");
            System.out.println("5. Exit");
            int choice = Helpers.readInt(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    newRoom();
                    break;
                case 2:
                    listRooms(getUserRooms());
                    break;
                case 3:
                    editRoom();
                    break;
                case 4:
                    deleteRoom();
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