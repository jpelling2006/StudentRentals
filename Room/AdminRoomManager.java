package room;

import java.util.List;
import java.util.Scanner;

import helpers.Helpers;

public final class AdminRoomManager implements RoomHandler {
    private final static Scanner scanner = new Scanner(System.in);
    private static AdminRoomManager instance;

    public static AdminRoomManager getInstance() {
        if (instance == null) { instance = new AdminRoomManager(); }
        return instance;
    }

    private AdminRoomManager() {}

    private static void listAllRooms() {
        List<Room> rooms = RoomQueryService.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("There are no rooms.");
            return;
        }

        System.out.println("\nAll rooms:");

        Helpers.printIndexed(rooms, Room::toString);
    }

    private static void deleteAnyRoom() {
        List<Room> rooms = RoomQueryService.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("There are no rooms.");
            return;
        }

        Room selectedRoom = Helpers.selectFromList(
            scanner, 
            rooms, 
            "Select room to delete",
            Room::toString
        );

        if (selectedRoom == null) { return; }

        System.out.println("Are you sure you want to delete this room?");
        System.out.println(selectedRoom.toString());

        selectedRoom.getProperty().removeRoom(selectedRoom);
        System.out.println("Room deleted by admin");
    }

    protected static boolean handleOnce() {
        boolean running = true;
        while (running) {
            System.out.println("\nAdmin Room Menu");
            System.out.println("1. View rooms");
            System.out.println("2. Delete room");
            System.out.println("3. Back"); 

            switch (
                Helpers.readIntInRange(
                    scanner, 
                    "Choose option: ", 
                    1, 
                    3
                ) 
            ) {
                case 1 -> listAllRooms();
                case 2 -> deleteAnyRoom();
                case 3 -> running = false;
            }
        }
        return true;
    }
}
