package user;

import java.util.ArrayList;
import java.util.List;

import properties.Property;

public class HomeownerUser extends User {
    @Override
    public UserType getUserType() { return UserType.HOMEOWNER; }

    private List<Property> properties = new ArrayList<>();

    public List<Property> getProperties() { return properties; }
    public void addProperty(Property property) { properties.add(property); }
    public void removeProperty(Property property) { properties.remove(property); }
}
