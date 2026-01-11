package room;

import java.util.List;
import java.util.Scanner;

import helpers.Helpers;

public class AdminRoomManager {
    private final RoomQueryService roomQueryService;
    private final Scanner scanner;

    public AdminRoomManager(RoomQueryService roomQueryService, Scanner scanner) {
        this.roomQueryService = roomQueryService;
        this.scanner = scanner;
    }

    public void listAllRooms() {
        List<Room> rooms = roomQueryService.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("There are no rooms.");
            return;
        }

        System.out.println("\nAll rooms:");

        Helpers.printIndexed(rooms, Room::toString);
    }

    public void deleteAnyRoom() {
        List<Room> rooms = roomQueryService.getAllRooms();

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
        selectedRoom.toString();

        selectedRoom.getProperty().removeRoom(selectedRoom);
        System.out.println("Room deleted by admin");
    }
}
