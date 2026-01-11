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
        return propertyQueryService.getAllProperties().stream()
            .flatMap(property -> property.getRooms().stream())
            .toList();
    }

    public List<Room> getUserRooms(User user) {
        return propertyQueryService.getUserProperties(user).stream()
            .flatMap(property -> property.getRooms().stream())
            .toList();
    }

    public List<Room> getRoomsByType(RoomType type) {
        return getAllRooms().stream()
            .filter(room -> room.getRoomType() == type)
            .toList();
    }
    
}
