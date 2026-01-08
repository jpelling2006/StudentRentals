package Booking;

import java.util.Scanner;

import FrontEnd.Session;
import Room.RoomManager;

public class StudentBookingManager extends BookingManager {
    protected final RoomManager roomManager;
    protected final Session session;
    protected final Scanner scanner;
    
    public StudentBookingManager(
        RoomManager roomManager,
        Session session,
        Scanner scanner
    ) {
        this.roomManager = roomManager;
        this.session = session;
        this.scanner = scanner;
    }
}
