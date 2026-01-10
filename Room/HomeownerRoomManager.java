package Room;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import Helpers.Helpers;
import Properties.Property;
import Properties.PropertyQueryService;
import Session.Session;

public class HomeownerRoomManager {
    private final PropertyQueryService propertyQueryService;
    private final RoomQueryService roomQueryService;
    private final Session session;
    private final Scanner scanner;

    public HomeownerRoomManager(
        PropertyQueryService propertyQueryService,
        RoomQueryService roomQueryService,
        Session session, 
        Scanner scanner
    ) {
        this.propertyQueryService = propertyQueryService;
        this.roomQueryService = roomQueryService;
        this.session = session;
        this.scanner = scanner;
    }

    public void listRooms() {
        List<Room> rooms = roomQueryService.getUserRooms(session.getCurrentUser().getUsername());
        System.out.println("\nYour rooms:");
        Helpers.printIndexed(rooms, Room::toString);
    }

    public void createRoom() {
        Room room = new Room();
        room.generateRoomID();

        // create this method
        Property property = Helpers.selectFromList(scanner, propertyQueryService.getUserProperties(session.getCurrentUser().getUsername()), "Select property");

        if (property == null) { return; }

        room.setProperty(property);

        RoomType type = Helpers.readEnum(scanner, "Select room type", RoomType.class);

        room.setRoomType(type);
        room.setRentPrice(Helpers.readDouble(scanner, "Rent per week: "));
        room.setBillsIncluded(Helpers.confirm(scanner));
        room.setLocation(Helpers.readString(scanner, "Location: ", 64));
        room.setAmenities(Helpers.readString(scanner, "Amenities: ", 256));
        

        LocalDate start = Helpers.readFutureDate(scanner, "Start date");
        LocalDate end;

        while (true) {
            end = Helpers.readFutureDate(scanner, "End date");
            try {
                room.setStartDate(start);
                room.setEndDate(end);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        property.addRoom(room);
        System.out.println("Room created.");
    }

    public void editRoom() {
        List<Room> rooms = roomQueryService.getUserRooms(session.getCurrentUser().getUsername());
        Room room = Helpers.selectFromList(scanner, rooms, "Select room");
        if (room != null) { editRoomMenu(room); }
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
            System.out.println("1. Room Type");
            System.out.println("2. Rent price");
            System.out.println("3. Bills included");
            System.out.println("4. Room location");
            System.out.println("5. Amenities");
            System.out.println("6. Availability start date");
            System.out.println("7. Availability end date");
            System.out.println("8. Cancel");
            
            Integer choice = Helpers.readIntInRange(
                scanner, 
                "Choose a field to edit: ", 
                1, 
                7
            );
                
            try {
                switch (choice) {
                    case 1 -> room.setRoomType(
                        Helpers.readEnum(scanner, "Room type", RoomType.class)
                    );
                    case 2 -> room.setRentPrice(
                        Helpers.readDouble(scanner, "Rent per week")
                    );
                    case 3 -> room.setBillsIncluded(Helpers.confirm(scanner));
                    case 4 -> room.setLocation(
                        Helpers.readString(scanner, "Location: ", 64)
                    );
                    case 5 -> room.setAmenities(
                        Helpers.readString(scanner, "Amenities: ", 256)
                    );
                    case 6 -> {
                        LocalDate start = Helpers.readFutureDate(scanner, "Start date");
                        LocalDate end = Helpers.readFutureDate(scanner, "End date");
                        room.setStartDate(start);
                        room.setEndDate(end);
                    }
                    case 7 -> { return; }
                }
            } catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
        }
    }

    public void deleteRoom() {
        List<Room> rooms = roomQueryService.getUserRooms(session.getCurrentUser().getUsername());
        Room room = Helpers.selectFromList(scanner, rooms, "Select room to delete");
        if (room == null) return;

        room.getProperty().removeRoom(room);
        System.out.println("Room deleted.");
    }
}
