package search;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import helpers.Helpers;
import properties.Property;
import properties.PropertyQueryService;
import room.Room;
import room.RoomType;

public class SearchManager {
    private Scanner scanner;
    private PropertyQueryService propertyQueryService;
    private List<Room> lastResults = new ArrayList<>();

    public SearchManager(
        PropertyQueryService propertyQueryService,
        Scanner scanner
    ) {
        this.propertyQueryService = propertyQueryService;
        this.scanner = scanner;
    }

    private boolean isRoomAvailable(Room room, LocalDate moveIn, LocalDate moveOut) {
        if (moveIn == null || moveOut == null) { return true; }
        return room.isAvailable(moveIn, moveOut);
    }

    private boolean inPriceRange(Room room, Double minPrice, Double maxPrice) {
        Double price = room.getRentPrice();

        if (
            (minPrice != null && price < minPrice)
            || (maxPrice != null && price > maxPrice)
        ) { return false; }

        return true;
    }

    private boolean correctRoomType(Room room, RoomType roomType) {
        return roomType == null || room.getRoomType() == roomType;
    }

    private boolean matchesLocation(Property property, String cityOrArea) {
        return cityOrArea == null || property.getCity().equalsIgnoreCase(cityOrArea);
    }

    public List<Room> searchRooms(RoomSearchCriteria criteria) {
        lastResults = propertyQueryService.getAllProperties().stream()
            .filter(property -> matchesLocation(property, criteria.city))
            .flatMap(property -> property.getRooms().stream())
            .filter(room -> isRoomAvailable(room, criteria.moveIn, criteria.moveOut))
            .filter(room -> inPriceRange(room, criteria.minPrice, criteria.maxPrice))
            .filter(room -> correctRoomType(room, criteria.roomType))
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

    private void viewSelectedRoom() {
        Room room = selectRoom();
        if (room != null) { viewRoomDetails(room); }
    }

    private void performSearch() {
        RoomSearchCriteria criteria = new RoomSearchCriteria();

        criteria.city = Helpers.readOptionalString(
            scanner, 
            "City (blank for any): ", 
            64
        );
        criteria.minPrice = Helpers.readOptionalDouble(
            scanner, 
            "Min price (blank for any): "
        );
        criteria.maxPrice = Helpers.readOptionalDouble(
            scanner, 
            "Min price (blank for any): "
        );

        while (true) {
            criteria.moveIn = Helpers.readFutureDate(
                scanner, 
                "Move in date (blank for any): "
            );
            criteria.moveOut = Helpers.readFutureDate(
                scanner, 
                "Move out date (blank for any): "
            );

            if (
                (criteria.moveIn != null) ||
                (
                    criteria.moveOut != null
                    && criteria.moveOut.isBefore(criteria.moveIn)
                )
            ) {
                System.out.println("Move out date must be after move in date.");
                continue;
            }
            break;
        }

        criteria.roomType = Helpers.readEnum(
            scanner, 
            "Room type: ", 
            RoomType.class
        );

        List<Room> results = searchRooms(criteria);
        displaySearchResults(results);
    }

    public void start() {
        while (true) {
            System.out.println("\nRoom Search");
            System.out.println("1. Search rooms");
            System.out.println("2. View last results");
            System.out.println("3. View room details");
            System.out.println("4. Back");

            switch (
                Helpers.readIntInRange(scanner, "Choose option: ", 1, 4)
            ) {
                case 1 -> performSearch();
                case 2 -> displaySearchResults(lastResults);
                case 3 -> viewSelectedRoom();
                case 4 -> { return; }
            }
        }
    }
}
