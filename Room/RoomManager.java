package room;

import session.Session;
import user.AdministratorUser;
import user.HomeownerUser;
import user.User;

public class RoomManager {
    private final Session session;

    private final AdminRoomManager adminRoomManager;
    private final HomeownerRoomManager homeownerRoomManager;

    public RoomManager(
        Session session,
        AdminRoomManager adminRoomManager,
        HomeownerRoomManager homeownerRoomManager
    ) {
        this.session = session;
        this.adminRoomManager = adminRoomManager;
        this.homeownerRoomManager = homeownerRoomManager;
    }

    public boolean handleOnce() {
        if (!session.isLoggedIn()) {
            System.out.println("Please log in first.");
            return true;
        }

        User user = session.getCurrentUser();

        if (user instanceof AdministratorUser) {
            return adminRoomManager.handleOnce();
        } else if (user instanceof HomeownerUser) {
            return homeownerRoomManager.handleOnce();
        }

        System.out.println("You do not have access to rooms.");
        return true;
    }
}
