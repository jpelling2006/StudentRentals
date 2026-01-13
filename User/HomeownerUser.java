package user;

import java.util.ArrayList;
import java.util.List;

import properties.Property;

public class HomeownerUser extends User {
    private final List<Property> properties = new ArrayList<>();

    public HomeownerUser(
        String username,
        String email,
        String phone,
        String passwordHash
    ) throws Exception {
        super(username, email, phone, passwordHash);
    }

    public List<Property> getProperties() { return properties; }
    public void addProperty(Property property) { properties.add(property); }
    public void removeProperty(Property property) { properties.remove(property); }
}
