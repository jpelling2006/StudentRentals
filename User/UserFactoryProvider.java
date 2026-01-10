package user;

public final class UserFactoryProvider {
    private UserFactoryProvider() {}

    public static UserFactory getFactory(UserType userType) {
        return switch (userType) {
            case STUDENT -> StudentUserFactory.getInstance();
            case HOMEOWNER -> HomeownerUserFactory.getInstance();
            case ADMINISTRATOR -> null; // admins can only be made manually
        };
    }
}
