package properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import user.User;

public class PropertyQueryService {
    protected Map<UUID, Property> properties = new HashMap<>();

    public PropertyQueryService() {};

    public Property getPropertyByID(UUID propertyID) {
        return properties.get(propertyID);
    }

    public List<Property> getAllProperties() {
        return new ArrayList<>(properties.values());
    }

    public List<Property> getUserProperties(User user) {
        return properties.values().stream()
            .filter(property -> property.getUser().equals(user))
            .toList();
    }
}
