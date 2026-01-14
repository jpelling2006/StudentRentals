package properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import room.RoomQueryService;
import user.User;

public final class PropertyQueryService {
    private static final Map<UUID, Property> propertiesByID = Property.getAllPropertiesMap();
    private static final Map<User, Set<UUID>> propertiesByUser = new HashMap<>();
    private static final Map<String, Set<UUID>> propertiesByCity = new HashMap<>();
    private static PropertyQueryService instance;

    public static PropertyQueryService getInstance() {
        if (instance == null) { instance = new PropertyQueryService(); }
        return instance;
    }

    private PropertyQueryService() {};

    public static void indexProperty(Property property) {
        // by user
        propertiesByUser.computeIfAbsent(property.getUser(), k -> new HashSet<>())
            .add(property.getPropertyID());

        // by city
        String city = property.getCity().toLowerCase();
        propertiesByCity.computeIfAbsent(city, k -> new HashSet<>())
            .add(property.getPropertyID());
    }

    public static void removeFromIndex(Property property) {
        // by user
        propertiesByUser.getOrDefault(property.getUser(), Collections.emptySet())
            .remove(property.getPropertyID());

        // by city
        String city = property.getCity().toLowerCase();
        propertiesByCity.getOrDefault(city, Collections.emptySet())
            .remove(property.getPropertyID());
    }

    public static void updateProperty(Property property) {
        if (property != null && property.getPropertyID() != null) {
            Property.getAllPropertiesMap().put(property.getPropertyID(), property);
        }
    }

    public static Property getPropertyByID(UUID propertyID) {
        return propertiesByID.get(propertyID);
    }

    public static List<Property> getAllProperties() {
        return new ArrayList<>(propertiesByID.values());
    }

    public static List<Property> getUserProperties(User user) {
        Set<UUID> propertyIDs = propertiesByUser.getOrDefault(
            user, 
            Collections.emptySet()
        );
        return propertyIDs.stream()
            .map(propertiesByID::get)
            .collect(Collectors.toList());
    }

    public static List<Property> getPropertiesByCity(String city) {
        if (city == null) { return getAllProperties(); }

        Set<UUID> propertyIDs = propertiesByCity.getOrDefault(
            city.toLowerCase(), 
            Collections.emptySet()
        );
        return propertyIDs.stream()
            .map(propertiesByID::get)
            .collect(Collectors.toList());
    }

    public static List<Property> getStayedInProperties(String username) {
        return RoomQueryService.getAllRooms().stream()
            .map(room -> room.getBookingByUser(username))
            .filter(booking -> booking != null)
            .filter(booking -> booking.hasEnded())
            .map(booking -> booking.getRoom().getProperty())
            .distinct()
            .collect(Collectors.toList());
    }
}
