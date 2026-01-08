package Booking;

import java.util.Scanner;

import FrontEnd.Session;
import Room.RoomManager;

public class BookingManager {
    private Scanner scanner = new Scanner(System.in);
    private Session session;
    private RoomManager roomManager;

    public BookingManager(
        RoomManager roomManager, 
        Session session, 
        Scanner scanner
    ) {
        this.roomManager = roomManager;
        this.session = session;
        this.scanner = scanner;
    }

    protected void editBookingMenu(Booking booking) {
        while (true) {
            Room room = room.get
        }
    }
}
