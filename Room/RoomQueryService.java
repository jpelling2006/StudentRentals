package Room;

import java.util.ArrayList;
import java.util.List;

import Properties.Property;
import Properties.PropertyManager;

public class RoomQueryService {
    private final PropertyManager propertyManager;

    public RoomQueryService(PropertyManager propertyManager) {
        this.propertyManager = propertyManager; 
    }

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        for (Property property : propertyManager.getAllProperties()) { 
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
