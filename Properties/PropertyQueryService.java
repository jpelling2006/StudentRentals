package properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import user.User;

public final class PropertyQueryService {
    private static Map<UUID, Property> properties = new HashMap<>();
    private static PropertyQueryService instance;

    public static PropertyQueryService getInstance() {
        if (instance == null) { instance = new PropertyQueryService(); }
        return instance;
    }

    private PropertyQueryService() {};

    public static Property getPropertyByID(UUID propertyID) {
        return properties.get(propertyID);
    }

    public static List<Property> getAllProperties() {
        return new ArrayList<>(properties.values());
    }

    public static List<Property> getUserProperties(User user) {
        return properties.values().stream() // gets all properties
            .filter(property -> property.getUser().equals(user)) // gets all properties for user
            .toList();
    }
}
