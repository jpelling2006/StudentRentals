package Room;

import java.util.List;
import java.util.Scanner;

import Helpers.Helpers;
import Properties.Property;
import Properties.PropertyManager;

public class AdminRoomManager extends RoomManager {
    private final PropertyManager propertyManager;
    private final Scanner scanner;

    public AdminRoomManager(PropertyManager propertyManager, Scanner scanner) {
        this.propertyManager = propertyManager;
        this.scanner = scanner;
    }

    private List<Room> getAllRooms() {
        return propertyManager.getAllProperties()
            .stream()
            .flatMap(property -> property.getRooms().steam())
            .toList();
    }

    public void editAnyRoom() {
        List<Room> rooms = getAllRooms();
        Room room = Helpers.selectFromList(scanner, rooms, "Select room to edit");
    }

    public void deleteAnyRoom() {
        List<Room> rooms = getAllRooms();
        Room room = Helpers.selectFromList(scanner, rooms, "Select room to delete");

        if (room == null) { return; }

        room.getProperty().removeRoom(room);
        System.out.println("Room deleted by admin");
    }
}
