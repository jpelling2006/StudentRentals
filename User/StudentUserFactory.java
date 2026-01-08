package User;

public final class StudentUserFactory extends UserFactory {
    private static final StudentUserFactory INSTANCE = new StudentUserFactory();

    private StudentUserFactory() {}

    public static StudentUserFactory getInstance() { return INSTANCE; }

    @Override
    public User createUser() { return new StudentUser(); }
}
