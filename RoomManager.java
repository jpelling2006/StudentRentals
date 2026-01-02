import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RoomManager {
    // add users to maybe check if they own the property? *shrugs*
    private List<Property> properties = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    private boolean propertyIDExists(Integer propertyID) {
        for (Property property : properties) {
            if (propertyID == property.getPropertyID()) { return true; }
        }
        return false;
    }

    public void inputProperty(Room room) {
        while (true) {
            // update later
            System.out.print("Enter property ID: ");
            
            Integer propertyID;

            try {
                propertyID = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
                continue;
            }

            // add user checking too
            if (!propertyIDExists(propertyID)) {
                System.out.println("Property doesn't exist.");
                continue;
            }

            try {
                room.setPropertyID(propertyID);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }
        }
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
                System.out.println("Room enter either 1 or 2.");
            }
        }
    }

    private void inputRentPrice(Room room) {
        while (true) {
            System.out.print("Rent price per week: ");
            try {
                Double rentPrice = Double.parseDouble(scanner.nextLine());
                room.setRentPrice(rentPrice);
                System.out.println("Rent price set.");
                return;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void inputBillsIncluded(Room room) {
        while (true) {
            System.out.print("Are bills included? (Y/N): ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("Yes")) {
                room.setBillsIncluded(true);
                System.out.println("Bills set to included.");
                return;
            } else if (choice.equalsIgnoreCase("N") || choice.equalsIgnoreCase("No")) {
                room.setBillsIncluded(false);
                System.out.println("Bills set to included.");
                return;
            } else {
                System.out.println("Please enter either Y or N.");
            }
        }
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

    private LocalDate inputDate(String prompt) {
        while (true) {
            System.out.print("Enter availability " + prompt + " (YYYY-MM-DD): ");
            String input = scanner.nextLine();

            try {
                LocalDate date = LocalDate.parse(input);
                if (date.isBefore(LocalDate.now())) {
                    System.out.println("Date cannot be in the past.");
                } else {
                    return date;
                }
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Invalid date format. Please use the format YYYY-MM-DD.");
            }
        }
    }


    public void inputStartDate(Room room) {
        LocalDate startDate = inputDate("start date");
        room.setStartDate(startDate);
        System.out.println("Start date set.");
    }

    public void inputEndDate(Room room) {
        while (true) {
            LocalDate endDate = inputDate("end date");

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
        Room room = new Room();

        System.out.println("\nCreating new room");

        room.generateRoomID();
        inputProperty(room);
        inputRoomType(room);
        inputRentPrice(room);
        inputBillsIncluded(room);
        inputLocation(room);
        inputAmenities(room);
        inputStartDate(room);
        inputEndDate(room);

        rooms.add(room);
        System.out.println("Room created successfully");
    }

    // get uh back to this
    // public List<Room> getUserRooms(String username) {}

    
}