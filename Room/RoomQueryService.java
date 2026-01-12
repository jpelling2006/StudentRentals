package room;

import java.util.List;

import properties.PropertyQueryService;
import user.User;

public final class RoomQueryService {
    private static RoomQueryService instance;

    public static RoomQueryService getInstance() {
        if (instance == null) { instance = new RoomQueryService(); }
        return instance;
    }

    private RoomQueryService() {}

    public static List<Room> getAllRooms() {
        return PropertyQueryService.getAllProperties().stream() // gets all properties
            .flatMap(property -> property.getRooms().stream()) // gets all rooms for each property
            .toList();
    }

    public static List<Room> getUserRooms(User user) {
        return PropertyQueryService.getUserProperties(user).stream() // gets all properties made by user
            .flatMap(property -> property.getRooms().stream()) // gets all rooms for each user property
            .toList();
    }

    public static List<Room> getRoomsByType(RoomType type) {
        return getAllRooms().stream() // gets all rooms
            .filter(room -> room.getRoomType() == type) // gets all rooms that have a certain type
            .toList();
    }
    
}
