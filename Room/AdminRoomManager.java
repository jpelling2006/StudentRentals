package Room;

import java.util.List;
import java.util.Scanner;

import Helpers.Helpers;

public class AdminRoomManager {
    private final RoomQueryService roomQueryService;
    private final Scanner scanner;

    public AdminRoomManager(RoomQueryService roomQueryService, Scanner scanner) {
        this.roomQueryService = roomQueryService;
        this.scanner = scanner;
    }

    public void editAnyRoom() {
        List<Room> rooms = roomQueryService.getAllRooms();
        Room room = Helpers.selectFromList(scanner, rooms, "Select room to edit");
    }

    public void deleteAnyRoom() {
        List<Room> rooms = roomQueryService.getAllRooms();
        Room room = Helpers.selectFromList(scanner, rooms, "Select room to delete");

        if (room == null) { return; }

        room.getProperty().removeRoom(room);
        System.out.println("Room deleted by admin");
    }
}
