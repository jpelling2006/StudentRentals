package user;

public class HomeownerUser extends User {
    @Override
    public UserType getUserType() { return UserType.HOMEOWNER; }
}
