package user;

import java.util.List;
import java.util.Map;

public final class UserQueryService {
    private static UserQueryService instance;

    public static UserQueryService getInstance() {
        if (instance == null) { instance = new UserQueryService(); }
        return instance;
    }

    public UserQueryService() {}

    public static User getUserByUsername(String username) {
        if (username == null) return null;
        Map<String, User> users = User.getAllUsersMap();
        return users.get(username.toLowerCase());
    }

    public static List<User> getAllUsers() {
        Map<String, User> users = User.getAllUsersMap();
        return users.values().stream().toList();
    }
}
