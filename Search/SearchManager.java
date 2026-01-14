package search;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import helpers.Helpers;
import properties.Property;
import room.Room;
import room.RoomQueryService;
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

    private static List<Room> searchRooms(RoomSearchCriteria criteria) {
        lastResults = RoomQueryService.getAllRooms().stream()
            .filter(room -> {
                Property property = room.getProperty();

                if (
                    criteria.city != null
                    && !property.getCity().equalsIgnoreCase(criteria.city)
                ) { 
                    return false; 
                }

                if (
                    criteria.roomType != null
                    && room.getRoomType() != criteria.roomType
                ) {
                    return false;
                }

                Double price = room.getRentPrice();
                if (criteria.minPrice != null && price < criteria.minPrice) {
                    return false;
                }
                if (criteria.maxPrice != null && price > criteria.maxPrice) {
                    return false;
                }

                if (!isRoomAvailable(room, criteria.moveIn, criteria.moveOut)) {
                    return false;
                }
                return true;
            })
            .collect(Collectors.toList());

        return lastResults;
    }

    private static void displaySearchResults(List<Room> rooms) {
        if (rooms.isEmpty()) {
            System.out.println("No rooms match your search criteria.");
            return;
        }

        // prints list of search results
        System.out.println("\nSearch results");
        Helpers.printIndexed(rooms, Room::toString);
    }

    // different to toString format - more detailed which wouldnt be convinient elsewhere
    private static void viewRoomDetails(Room room) {
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

        while (true) {
            criteria.minPrice = Helpers.readOptionalDouble(
                scanner, 
                "Min price (blank for any): "
            );
            criteria.maxPrice = Helpers.readOptionalDouble(
                scanner, 
                "Max price (blank for any): "
            );

            if (criteria.minPrice != null && criteria.maxPrice != null) {
                if (criteria.minPrice > criteria.maxPrice) {
                    System.out.println(
                        "Min price must be less than or equal to max price!"
                    );
                    continue;
                }
            }

            break;
        }

        while (true) {
            criteria.moveIn = Helpers.readOptionalFutureDate(
                scanner, "Move in date (blank for any): "
            );
            criteria.moveOut = Helpers.readOptionalFutureDate(
                scanner, "Move out date (blank for any): "
            );

            // only check if both dates are given
            if (
                criteria.moveIn != null
                && criteria.moveOut != null
                && criteria.moveOut.isBefore(criteria.moveIn)
            ) {
                System.out.println("Move out date must be after move in date.");
                continue;
            }

            break;
        }

        criteria.roomType = Helpers.readOptionalEnum(
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
