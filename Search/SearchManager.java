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

public final class SearchManager {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Room> lastResults = new ArrayList<>();
    private static SearchManager instance;

    public static SearchManager getInstance() {
        if (instance == null) { instance = new SearchManager(); }
        return instance;
    }

    public SearchManager() {}

    // checks if room is available between two dates
    private static boolean isRoomAvailable(
        Room room, 
        LocalDate moveIn, 
        LocalDate moveOut
    ) {
        if (moveIn == null || moveOut == null) { return true; }
        return room.isAvailable(moveIn, moveOut);
    }

    // checks if room is in price range
    private static boolean inPriceRange(
        Room room, 
        Double minPrice, 
        Double maxPrice
    ) {
        Double price = room.getRentPrice();

        if (
            (minPrice != null && price < minPrice)
            || (maxPrice != null && price > maxPrice)
        ) { return false; }

        return true;
    }

    // checks if room is a certain type
    private static boolean correctRoomType(Room room, RoomType roomType) {
        return roomType == null || room.getRoomType() == roomType;
    }

    // checks if room is in a property in a certain city
    private static boolean matchesLocation(Property property, String city) {
        return city == null || property.getCity().equalsIgnoreCase(city);
    }

    public static List<Room> searchRooms(RoomSearchCriteria criteria) {
        lastResults = PropertyQueryService.getAllProperties().stream() // gets all properties
            .filter(property -> matchesLocation(property, criteria.city)) // gets all properties in certain city
            .flatMap(property -> property.getRooms().stream()) // gets all rooms for selected properties
            .filter(room -> isRoomAvailable(room, criteria.moveIn, criteria.moveOut)) // selects rooms available in date range
            .filter(room -> inPriceRange(room, criteria.minPrice, criteria.maxPrice)) // selects rooms in price range
            .filter(room -> correctRoomType(room, criteria.roomType)) // selects rooms of a certain type
            .collect(Collectors.toList());

        return lastResults;
    }

    public static void displaySearchResults(List<Room> rooms) {
        if (rooms.isEmpty()) {
            System.out.println("No rooms match your search criteria.");
            return;
        }

        // prints list of search results
        System.out.println("\nSearch results");
        Helpers.printIndexed(rooms, Room::toString);
    }

    // different to toString format - more detailed which wouldnt be convinient elsewhere
    public static void viewRoomDetails(Room room) {
        if (room == null) { return; }

        Property property = room.getProperty();

        System.out.println("\nRoom Details");
        System.out.println("Property: " + property.getAddress());
        System.out.println("City: " + property.getCity());
        System.out.println("Room type: " + room.getRoomType());
        System.out.println("Rent: £" + room.getRentPrice() + "/week");
        System.out.println("Bills included: " + (
            room.getBillsIncluded() ? "Yes" : "No")
        );
        System.out.println("Amenities: " + room.getAmenities());
        System.out.println("Available from: " + room.getStartDate());
        System.out.println("Available until: " + room.getEndDate());
    }

    private static void viewSelectedRoom() {
        if (lastResults.isEmpty()) {
            System.out.println("No rooms to select.");
            return;
        }

        Room selectedRoom = Helpers.selectFromList(
            scanner,
            lastResults, 
            "Select room", 
            Room::toString
        );

        if (selectedRoom == null) {
            System.out.println("Room doesn't exist.");
            return;
        }

        viewRoomDetails(selectedRoom);
    }

    private static void performSearch() {
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

            // only checks if endDate is after startDate if both are given
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

    public static void handleOnce() {
        while (true) {
            System.out.println("\nRoom Search");
            System.out.println("1. Search rooms");
            System.out.println("2. View last results");
            System.out.println("3. View room details");
            System.out.println("4. Back");

            switch (
                Helpers.readIntInRange(
                    scanner, 
                    "Choose option: ", 
                    1, 
                    4
                )
            ) {
                case 1 -> performSearch();
                case 2 -> displaySearchResults(lastResults);
                case 3 -> viewSelectedRoom();
                case 4 -> { return; }
            }
        }
    }
}
