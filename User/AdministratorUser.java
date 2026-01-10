package user;

public class AdministratorUser extends User {
    @Override
    public UserType getUserType() { return UserType.ADMINISTRATOR; }
}
