package session;

import user.User;

// exists to store the current logged in user
public final class Session {
    private static User currentUser;

    public static User getCurrentUser() { return currentUser; }

    public static boolean isLoggedIn() { return currentUser != null; }

    public static void login(User user) { currentUser = user; }

    public static void logout() { currentUser = null; }
}
