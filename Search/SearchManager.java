package Search;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Properties.*;
import Room.*;
import Session.Session;
import Helpers.*;

public class SearchManager {
    private Scanner scanner;
    private Session session;
    private PropertyManager propertyManager;
    private List<Room> lastResults = new ArrayList<>();

    public SearchManager(
        PropertyManager propertyManager,
        Session session,
        Scanner scanner
    ) {
        this.propertyManager = propertyManager;
        this.session = session;
        this.scanner = scanner;
    }

    private boolean isRoomAvailable(Room room, LocalDate moveIn, LocalDate moveOut) {
        return room.isAvailable(moveIn, moveOut);
    }

    private boolean inPriceRange(Room room, Double minPrice, Double maxPrice) {
        return room.getRentPrice() >= minPrice && room.getRentPrice() <= maxPrice;
    }

    private boolean correctRoomType(Room room, String roomType) {
        return room.getRoomType().equals(roomType);
    }

    private boolean matchesLocation(Property property, String cityOrArea) {
        return cityOrArea == null || property.getCity().equalsIgnoreCase(cityOrArea);
    }

    public List<Room> searchRooms(
        String city,
        Double minPrice, 
        Double maxPrice, 
        LocalDate moveIn, 
        LocalDate moveOut, 
        String roomType
    ) {
        lastResults = propertyManager.getAllProperties().stream()
            .filter(property -> matchesLocation(property, city))
            .flatMap(property -> property.getRooms().stream())
            .filter(room -> isRoomAvailable(room, moveIn, moveOut))
            .filter(room -> inPriceRange(room, minPrice, maxPrice))
            .filter(room -> correctRoomType(room, roomType))
            .collect(Collectors.toList());

        return lastResults;
    }

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
        if (lastResults.isEmpty()) {
            System.out.println("No rooms to select.");
            return null;
        }

        Room choice = Helpers.selectFromList(
            scanner, 
            lastResults, 
            "Select room"
        );

        return choice;
    }

    public void viewRoomDetails(Room room) {
        if (room == null) { return; }

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
