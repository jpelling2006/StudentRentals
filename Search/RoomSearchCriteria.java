package search;

import java.time.LocalDate;

import room.RoomType;

// since searchRooms() had too many parameters
public class RoomSearchCriteria {
    protected String city;
    protected Double minPrice;
    protected Double maxPrice;
    protected LocalDate moveIn;
    protected LocalDate moveOut;
    protected RoomType roomType;
}
