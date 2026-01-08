package User;

public final class UserFactoryProvider {
    private UserFactoryProvider() {}

    public static UserFactory getFactory(UserType type) {
        return switch (type) {
            case STUDENT -> StudentUserFactory.getInstance();
            case HOMEOWNER -> HomeownerUserFactory.getInstance();
        };
    }
}
