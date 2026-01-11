package room;

import java.util.ArrayList;
import java.util.List;

import properties.Property;
import properties.PropertyQueryService;
import user.User;

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

    public List<Room> getUserRooms(User user) {
        List<Room> rooms = new ArrayList<>();

        for (Property property : propertyQueryService.getUserProperties(user)) {
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
