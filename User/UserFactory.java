package user;

public abstract class UserFactory {
    public abstract User createUser(
        String username,
        String email,
        String phone,
        String passwordHash,
        String university,
        String studentNumber);
}
