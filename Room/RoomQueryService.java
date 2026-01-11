package room;

import java.util.List;

import properties.PropertyQueryService;
import user.User;

public class RoomQueryService {
    private final PropertyQueryService propertyQueryService;

    public RoomQueryService(PropertyQueryService propertyQueryService) {
        this.propertyQueryService = propertyQueryService; 
    }

    public List<Room> getAllRooms() {
        return propertyQueryService.getAllProperties().stream() // gets all properties
            .flatMap(property -> property.getRooms().stream()) // gets all rooms for each property
            .toList();
    }

    public List<Room> getUserRooms(User user) {
        return propertyQueryService.getUserProperties(user).stream() // gets all properties made by user
            .flatMap(property -> property.getRooms().stream()) // gets all rooms for each user property
            .toList();
    }

    public List<Room> getRoomsByType(RoomType type) {
        return getAllRooms().stream() // gets all rooms
            .filter(room -> room.getRoomType() == type) // gets all rooms that have a certain type
            .toList();
    }
    
}
