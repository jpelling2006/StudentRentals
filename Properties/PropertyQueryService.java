package properties;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import user.User;

public final class PropertyQueryService {
    private static PropertyQueryService instance;

    public static PropertyQueryService getInstance() {
        if (instance == null) { instance = new PropertyQueryService(); }
        return instance;
    }

    private PropertyQueryService() {};

    public static Property getPropertyByID(UUID propertyID) {
        if (propertyID == null) return null;
        Map<UUID, Property> properties = Property.getAllPropertiesMap();
        return properties.get(propertyID);
    }

    public static List<Property> getAllProperties() {
        Map<UUID, Property> properties = Property.getAllPropertiesMap();
        return properties.values().stream().toList();
    }

    public static List<Property> getUserProperties(User user) {
        Map<UUID, Property> properties = Property.getAllPropertiesMap();
        return properties.values().stream() // gets all properties
            .filter(property -> property.getUser().equals(user)) // gets all properties for user
            .toList();
    }
}
