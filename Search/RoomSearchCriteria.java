package search;

import java.time.LocalDate;

import room.RoomType;

public class RoomSearchCriteria {
    public String city;
    public Double minPrice;
    public Double maxPrice;
    public LocalDate moveIn;
    public LocalDate moveOut;
    public RoomType roomType;
}
