package user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class UserQueryService {
    private static Map<String, User> users = new HashMap<>();
    private static UserQueryService instance;

    public static UserQueryService getInstance() {
        if (instance == null) { instance = new UserQueryService(); }
        return instance;
    }

    public UserQueryService() {}

    public static User getUserByUsername(String username) {
        return users.get(username);
    }

    public static List<User> getAllUsers() {
        return users.values().stream().toList();
    }
}
