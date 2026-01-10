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
        Room room = Helpers.selectFromList(scanner, rooms, "Select room to delete");

        if (room == null) { return; }

        room.getProperty().removeRoom(room);
        System.out.println("Room deleted by admin");
    }
}
