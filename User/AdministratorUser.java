package user;

public final class AdministratorUser extends User {
    public AdministratorUser(
        String username,
        String email,
        String phone,
        String passwordHash
    ) throws Exception {
        super(username, email, phone, passwordHash);
    }
}
