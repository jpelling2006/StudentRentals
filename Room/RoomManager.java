package room;

import session.Session;
import user.AdministratorUser;
import user.HomeownerUser;
import user.User;

public final class RoomManager {
    private static RoomManager instance;

    public static RoomManager getInstance() {
        if (instance == null) { instance = new RoomManager(); }
        return instance;
    }

    private RoomManager() {}

    public static boolean handleOnce() {
        if (!Session.isLoggedIn()) {
            System.out.println("Please log in first.");
            return true;
        }

        User user = Session.getCurrentUser();

        if (user instanceof AdministratorUser) {
            return AdminRoomManager.handleOnce();
        } else if (user instanceof HomeownerUser) {
            return HomeownerRoomManager.handleOnce();
        }

        System.out.println("You do not have access to rooms.");
        return true;
    }
}
