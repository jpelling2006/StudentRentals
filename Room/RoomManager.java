package room;

import java.util.Scanner;

import helpers.Helpers;
import session.Session;

public class RoomManager {
    private final HomeownerRoomManager homeownerRoomManager;
    private final AdminRoomManager adminRoomManager;

    protected final Scanner scanner;
    protected final Session session;

    protected RoomManager(
        HomeownerRoomManager homeownerRoomManager,
        AdminRoomManager adminRoomManager,
        Scanner scanner,
        Session session
    ) {
        this.homeownerRoomManager = homeownerRoomManager;
        this.adminRoomManager = adminRoomManager;
        this.scanner = scanner;
        this.session = session;
    }

    public void start() {
        if (!session.isLoggedIn()) {
            System.out.println("Please log in first.");
            return;
        }

        switch (session.getCurrentUser().getUserType()) {
            case STUDENT -> System.out.println("Access denied.");
            case HOMEOWNER -> homeownerMenu();
            case ADMINISTRATOR -> adminMenu();
        }
    }

    private void homeownerMenu() {
        while (true) {
            System.out.println("\nHomeowner Room Menu");
            System.out.println("1. Create room");
            System.out.println("2. View rooms");
            System.out.println("3. Edit room");
            System.out.println("4. Delete room");
            System.out.println("5. Back");

            switch (
                Helpers.readIntInRange(
                    scanner,
                    "Choose option: ",
                    1,
                    5
                )
            ) {
                case 1 -> homeownerRoomManager.createRoom();
                case 2 -> homeownerRoomManager.listRooms();
                case 3 -> homeownerRoomManager.editRoom();
                case 4 -> homeownerRoomManager.deleteRoom();
                case 5 -> { return; }
            }
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Room Menu");
            System.out.println("1. View rooms");
            System.out.println("2. Delete room");
            System.out.println("3. Back"); 

            switch (
                Helpers.readIntInRange(scanner, "Choose option: ", 1, 3) 
            ) {
                case 1 -> adminRoomManager.listAllRooms();
                case 2 -> adminRoomManager.deleteAnyRoom();
                case 3 -> { return; }
            }
        }
    }
}
