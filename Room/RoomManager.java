package Room;

import java.util.List;
import java.util.Scanner;

import Helpers.Helpers;
import Properties.Property;

public class RoomManager {
    protected final Scanner scanner;

    protected RoomManager(Scanner scanner) {
        this.scanner = scanner;
    }

    protected void editRoomMenu(Room room) {
        while (true) {
            Property property = room.getProperty();
            String address = (property != null) 
                ? property.getAddress() : "Unknown property";

            System.out.println(
                "\nEditing room: "
                + address + " - "
                + room.getLocation()
            );
            System.out.println("1. Room type");
            System.out.println("2. Rent price");
            System.out.println("3. Bills included");
            System.out.println("4. Location");
            System.out.println("5. Amenities");
            System.out.println("6. Dates");
            System.out.println("7. Cancel");

            int choice = Helpers.readIntInRange(scanner, "Choose option: ", 1, 7);

            try {
                switch (choice) {
                    case 1 ->
                        room.setRoomType(
                            Helpers.readEnum(scanner, "Room type", RoomType.class)
                        );
                    case 2 ->
                        room.setRentPrice(
                            Helpers.readDouble(scanner, "Rent per week: ")
                        );
                    case 3 ->
                        room.setBillsIncluded(
                            Helpers.confirm(scanner)
                        );
                    case 4 ->
                        room.setLocation(
                            Helpers.readString(scanner, "Location: ", 64)
                        );
                    case 5 ->
                        room.setAmenities(
                            Helpers.readString(scanner, "Amenities: ", 256)
                        );
                    case 6 -> {
                        room.setStartDate(
                            Helpers.readFutureDate(scanner, "Start date")
                        );
                        room.setEndDate(
                            Helpers.readFutureDate(scanner, "End date")
                        );
                    }
                    case 7 -> { return; }
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
