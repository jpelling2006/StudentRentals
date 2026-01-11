package user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserQueryService {
    protected Map<String, User> users = new HashMap<>();

    public UserQueryService() {};

    public User getUserByUsername(String username) {
        return users.get(username);
    }

    public List<User> getAllUsers() {
        return users.values().stream().toList();
    }
}
