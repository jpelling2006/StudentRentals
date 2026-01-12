package room;

import access.RoomAccess;
import session.Session;
import user.User;

public class RoomManager {
    protected final Session session;

    protected RoomManager(Session session) { this.session = session; }

    public boolean handleOnce() {
        if (!session.isLoggedIn()) {
            System.out.println("Please log in first.");
            return true;
        }

        User user = session.getCurrentUser();

        if (user instanceof RoomAccess roomAccess) {
            return roomAccess.getRoomHandler().handleOnce();
        }

        System.out.println("You do not have access to rooms.");
        return true;
    }
}
