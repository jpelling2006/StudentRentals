package Room;

import java.util.ArrayList;
import java.util.List;

import Properties.Property;
import Properties.PropertyQueryService;

public class RoomQueryService {
    private final PropertyQueryService propertyQueryService;

    public RoomQueryService(PropertyQueryService propertyQueryService) {
        this.propertyQueryService = propertyQueryService; 
    }

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        for (Property property : propertyQueryService.getAllProperties()) { 
            rooms.addAll(property.getRooms()); 
        }
        return rooms;
    }

    public List<Room> getUserRooms(String username) {
        List<Room> rooms = new ArrayList<>();

        for (Property property : propertyQueryService.getUserProperties(username)) {
            rooms.addAll(property.getRooms());
        }
        return rooms;
    }

    public List<Room> getRoomsByType(RoomType type) {
        List<Room> result = new ArrayList<>();
        for (Room room : getAllRooms()) {
            if (room.getRoomType() == type) { result.add(room); }
        }
        return result;
    }
    
}
