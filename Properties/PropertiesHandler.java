package properties;

// called this to not mess with java's propertyhandler
public interface PropertiesHandler {
    static boolean handleOnce() { return false; }
}
