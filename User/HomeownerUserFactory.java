package User;

public final class HomeownerUserFactory extends UserFactory {
    private static final HomeownerUserFactory INSTANCE = new HomeownerUserFactory();

    private HomeownerUserFactory() {}

    public static HomeownerUserFactory getInstance() { return INSTANCE; }

    @Override
    public User createUser() { return new HomeownerUser(); }
}
