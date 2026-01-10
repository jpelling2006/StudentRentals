package properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PropertyQueryService {
    protected Map<UUID, Property> properties = new HashMap<>();

    public PropertyQueryService() {};

    public Property getPropertyByID(UUID propertyID) {
        return properties.get(propertyID);
    }

    public List<Property> getAllProperties() {
        return new ArrayList<>(properties.values());
    }

    public List<Property> getUserProperties(String username) {
        List<Property> userProperties = new ArrayList<>();

        // change to streams i am BEGGING you
        for(Property property : properties.values()) {
            if (property.getUsername().equalsIgnoreCase(username)) {
                userProperties.add(property);
            }
        }

        return userProperties;
    }
}
