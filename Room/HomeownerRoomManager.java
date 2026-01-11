package room;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import helpers.Helpers;
import properties.Property;
import properties.PropertyQueryService;
import session.Session;

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
        List<Room> rooms = roomQueryService.getUserRooms(session.getCurrentUser());
        System.out.println("\nYour rooms:");
        Helpers.printIndexed(rooms, Room::toString);
    }

    public void createRoom() {
        Room room = new Room();
        room.generateRoomID();

        Property property = Helpers.selectFromList(
            scanner, 
            propertyQueryService.getUserProperties(session.getCurrentUser()), 
            "Select property",
            Property::toString
        );

        if (property == null) { return; }

        // input fields
        room.setProperty(property);
        room.setRoomType(
            Helpers.readEnum(
                scanner, 
                "Select room type", 
                RoomType.class
            )
        );
        room.setRentPrice(
            Helpers.readDouble(scanner, "Rent per week: ")
        );

        System.out.println("Are bills included?");
        room.setBillsIncluded(Helpers.confirm(scanner));

        room.setLocation(
            Helpers.readString(scanner, "Location: ", 64)
        );
        room.setAmenities(
            Helpers.readString(scanner, "Amenities: ", 256)
        );
        room.setStartDate(Helpers.readFutureDate(scanner, "Start date"));
        room.setEndDate(Helpers.readFutureDate(scanner, "End date"));

        property.addRoom(room);
        System.out.println("Room created.");
    }

    public void editRoom() {
        List<Room> rooms = roomQueryService.getUserRooms(session.getCurrentUser());

        if (rooms.isEmpty()) {
            System.out.println("You have no rooms to edit.");
            return;
        }

        Room room = Helpers.selectFromList(
            scanner, 
            rooms, 
            "Select room", 
            Room::toString
        );

        if (room == null) { 
            System.out.println("Room doesn't exist");
            return;
        }

        editRoomMenu(room);
    }

    protected void editRoomMenu(Room room) {
        while (true) {
            System.out.println( "\nEditing room: ");
            System.out.println(room.toString());
            System.out.println("1. Room Type");
            System.out.println("2. Rent price");
            System.out.println("3. Bills included");
            System.out.println("4. Room location");
            System.out.println("5. Amenities");
            System.out.println("6. Availability start date");
            System.out.println("7. Availability end date");
            System.out.println("8. Cancel");
                
            try {
                switch (
                    Helpers.readIntInRange(
                        scanner, 
                        "Choose a field to edit: ", 
                        1, 
                        7
                    )
                ) {
                    case 1 -> room.setRoomType(
                        Helpers.readEnum(
                            scanner, 
                            "Room type", 
                            RoomType.class
                        )
                    );
                    case 2 -> room.setRentPrice(
                        Helpers.readDouble(scanner, "Rent per week")
                    );
                    case 3 -> {
                        System.out.println("Are bills included?");
                        room.setBillsIncluded(Helpers.confirm(scanner));
                    }
                    case 4 -> room.setLocation(
                        Helpers.readString(
                            scanner, 
                            "Location: ", 
                            64
                        )
                    );
                    case 5 -> room.setAmenities(
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

    public void deleteRoom() {
        List<Room> rooms = roomQueryService.getUserRooms(session.getCurrentUser());

        // user selects booking from list
        Room selectedRoom = Helpers.selectFromList(
            scanner, 
            rooms, 
            "Select room to delete",
            Room::toString
        );

        if (selectedRoom == null) return;

        System.out.println("Are you sure you want to delete this room?");
        selectedRoom.toString();

        // if user selects "n"
        if (!Helpers.confirm(scanner)) {
            System.out.println("Deletion cancelled.");
            return;
        }

        selectedRoom.getProperty().removeRoom(selectedRoom);
        System.out.println("Room deleted.");
    }
}
