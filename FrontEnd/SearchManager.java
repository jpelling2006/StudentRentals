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
        return room.getRentPrice() > minPrice && room.getRentPrice() < maxPrice;
    }

    private boolean correctRoomType(Room room, String roomType) {
        return room.getRoomType().equals(roomType);
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
        List<Room> roomResults = new ArrayList<>();

        for (Room room : rooms) {
            if (!isRoomAvailable(room, moveIn, moveOut)) { continue; }
            if (!inPriceRange(room, minPrice, maxPrice)) { continue; }
            if (!correctRoomType(room, roomType)) { continue; }

            Property property = propertyManager.getPropertyByID(room.getPropertyID());

            // add city to property mayhaps
            roomResults.add(room);
        }
    }

    // 1. £120/week - Single room - City Centre
    public void displaySearchResults(List<Room> rooms) {

    }

    public Room selectRoom() {
        int choice = Helpers.selectFromList(scanner, rooms.size(), "Select room");
        return rooms.get(choice - 1);
    }

    public void viewRoomDetails(Room room) {

    }
}
