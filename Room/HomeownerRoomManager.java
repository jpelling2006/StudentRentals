package room;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import helpers.Helpers;
import properties.Property;
import properties.PropertyQueryService;
import session.Session;

public final class HomeownerRoomManager implements RoomHandler {
    private final static Scanner scanner = new Scanner(System.in);
    private static HomeownerRoomManager instance;

    public static HomeownerRoomManager getInstance() {
        if (instance == null) { instance = new HomeownerRoomManager(); }
        return instance;
    }

    private HomeownerRoomManager() {}

    private static void listRooms() {
        List<Room> rooms = RoomQueryService.getUserRooms(Session.getCurrentUser());

        if (rooms.isEmpty()) {
            System.out.println("You have no rooms.");
        }

        System.out.println("\nYour rooms:");
        Helpers.printIndexed(rooms, Room::toString);
    }

    private static void createRoom() {
        Property selectedProperty = Helpers.selectFromList(
            scanner, 
            PropertyQueryService.getUserProperties(Session.getCurrentUser()), 
            "Select property",
            Property::toString
        );

        if (selectedProperty == null) { return; }

        try {
            RoomType roomType = Helpers.readEnum(
                scanner, 
                "Select room type", 
                RoomType.class
            );
            Double rentPrice = Helpers.readDouble(scanner, "Rent per week: ");
            System.out.println("Are bills included?");
            boolean billsIncluded = (Helpers.confirm(scanner));
            String location = Helpers.readString(
                scanner, "Location: ", 64);
            String amenities = Helpers.readString(
                scanner, "Amenities: ", 256
            );
            LocalDate startDate = Helpers.readFutureDate(
                scanner, "Start date"
            );
            LocalDate endDate = Helpers.readFutureDate(scanner, "End date");

            new Room(
                selectedProperty, 
                roomType, 
                rentPrice, 
                billsIncluded, 
                location, 
                amenities, 
                startDate, 
                endDate
            );

            System.out.println("Room created successfully.");
        } catch (Exception e) {
            System.out.println("Failed to create room: " + e.getMessage());
        }
    }

    private static void editRoom() {
        List<Room> rooms = RoomQueryService.getUserRooms(Session.getCurrentUser());

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

    private static void editRoomMenu(Room room) {
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
                        8
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
                        Helpers.readString(
                            scanner, 
                            "Amenities: ", 
                            256
                        )
                    );
                    case 6 -> {
                        room.setStartDate(
                            Helpers.readFutureDate(scanner, "Start date")
                        );
                    }
                    case 7 -> {
                        room.setEndDate(
                            Helpers.readFutureDate(scanner, "End date")
                        );
                    }
                    case 8 -> { return; }
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void deleteRoom() {
        List<Room> rooms = RoomQueryService.getUserRooms(Session.getCurrentUser());

        // user selects booking from list
        Room selectedRoom = Helpers.selectFromList(
            scanner, 
            rooms, 
            "Select room to delete",
            Room::toString
        );

        if (selectedRoom == null) return;

        System.out.println("Are you sure you want to delete this room?");
        System.out.println(selectedRoom.toString());

        // if user selects "n"
        if (!Helpers.confirm(scanner)) {
            System.out.println("Deletion cancelled.");
            return;
        }

        selectedRoom.getProperty().removeRoom(selectedRoom);
        System.out.println("Room deleted.");
    }

    protected static boolean handleOnce() {
        boolean running = true;
        while (running) {
            System.out.println("\nHomeowner Room Menu");
            System.out.println("1. Create room");
            System.out.println("2. View rooms");
            System.out.println("3. Edit room");
            System.out.println("4. Delete room");
            System.out.println("5. Back");

            switch (
                Helpers.readIntInRange(
                    scanner,
                    "Choose option: ",
                    1,
                    5
                )
            ) {
                case 1 -> createRoom();
                case 2 -> listRooms();
                case 3 -> editRoom();
                case 4 -> deleteRoom();
                case 5 -> running = false;
            }
        }
        return true;
    }
}
