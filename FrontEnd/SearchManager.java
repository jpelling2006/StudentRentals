package FrontEnd;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Properties.*;
import Room.*;
import Helpers.*;

public class SearchManager {
    private List<Property> properties = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private Session session;
    private PropertyManager propertyManager;

    public SearchManager(PropertyManager propertyManager, Session session, Scanner scanner) {
        this.propertyManager = propertyManager;
        this.session = session;
        this.scanner = scanner;
    }

    private boolean isRoomAvailable(Room room, LocalDate moveIn, LocalDate moveOut) {
        return !moveIn.isBefore(room.getStartDate()) && !moveOut.isAfter(room.getEndDate());
    }

    private boolean inPriceRange(Room room, Double minPrice, Double maxPrice) {
        return room.getRentPrice() >= minPrice && room.getRentPrice() <= maxPrice;
    }

    private boolean correctRoomType(Room room, String roomType) {
        return room.getRoomType().equalsIgnoreCase(roomType);
    }

    private boolean matchesLocation(Property property, String cityOrArea) {
        return cityOrArea == null || property.getCity().equalsIgnoreCase(cityOrArea);
    }

    // FOR each room
    // IF room not available for dates → skip
    // IF price not in range → skip
    // IF roomType mismatch → skip
    // FETCH property
    // IF city/area mismatch → skip
    // ADD room to results

    // could vectorise?
    public List<Room> searchRooms(
        String cityOrArea,
        Double minPrice,
        Double maxPrice,
        LocalDate moveIn,
        LocalDate moveOut,
        String roomType
    ) {
        List<Room> results = new ArrayList<>();

        for (Property property : propertyManager.getAllProperties()) {
            if (!matchesLocation(property, cityOrArea)) { continue; }

            for (Room room : property.getRooms()) {
                if (!isRoomAvailable(room, moveIn, moveOut)) { continue; }
                if (!inPriceRange(room, minPrice, maxPrice)) { continue; }
                if (!correctRoomType(room, roomType)) { continue; }

                results.add(room);
            }
        }

        return results;
    }

    // 1. £120/week - Single room - City Centre
    public void displaySearchResults(List<Room> rooms) {
        if (rooms.isEmpty()) {
            System.out.println("No rooms match your search criteria.");
            return;
        }

        System.out.println("\nSearch results");

        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            Property property = room.getProperty();

            System.out.println(
                (i + 1) + ". £" + room.getRentPrice() + "/week - "
                + property.getAverageRating() + " stars - "
                + room.getLocation() + " - "
                + property.getCity() + " - "
                + property.getAddress()
            );
        }
    }

    public Room selectRoom() {
        int choice = Helpers.selectFromList(scanner, rooms.size(), "Select room");
        return rooms.get(choice - 1);
    }

    public void viewRoomDetails(Room room) {
        Property property = room.getProperty();

        System.out.println("\nRoom Details");
        System.out.println("Property: " + property.getAddress());
        System.out.println("City: " + property.getCity());
        System.out.println("Room type: " + room.getRoomType());
        System.out.println("Rent: £" + room.getRentPrice() + "/week");
        System.out.println("Bills included: " + (room.getBillsIncluded() ? "Yes" : "No"));
        System.out.println("Amenities: " + room.getAmenities());
        System.out.println("Available from: " + room.getStartDate());
        System.out.println("Available until: " + room.getEndDate());
    }
}
