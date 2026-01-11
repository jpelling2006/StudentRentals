package session;

import user.User;

// exists to store the current logged in user
public class Session {
    private User currentUser;

    public User getCurrentUser() { return currentUser; }

    public boolean isLoggedIn() { return currentUser != null; }

    public void login(User user) { this.currentUser = user; }

    public void logout() { this.currentUser = null; }
}
