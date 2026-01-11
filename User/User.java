package user;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public abstract class User {
    private String username;
    private UserType userType;
    private String email;
    private String phone;
    private String passwordHash;
    private byte[] salt;

    public String getUsername() { return username; }
    public void setUsername(String username) {
        // add regex
        if (username == null || username.length() > 32) {
            throw new IllegalArgumentException("Username must be up to 32 characters.");
        }
        this.username = username;
    }

    public abstract UserType getUserType(); // enum?

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email == null || !isValidEmail(email)) {
            throw new IllegalArgumentException("Email must be valid.");
        }
        this.email = email;
    }

    public String getPhone() { return phone; }
    public void setPhone(String phone) {
        if (phone == null || !phone.matches("^\\d{10}$")) {
            throw new IllegalArgumentException(
                "Phone number must be ten digits long."
            );
        }
        this.phone = phone;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String rawPassword) throws Exception {
        this.salt = generateSalt();
        String hashedPassword = hashPassword(rawPassword, salt);
        this.passwordHash = hashedPassword;
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
        ); // https://www.baeldung.com/java-email-validation-regex
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    public byte[] getSalt() { return salt; }

    public static String hashPassword(
        String password, byte[] salt
    ) throws Exception {
        // PBKDF2 hash configuration
        Integer iterations = 65536;
        Integer keyLength = 256;

        // PBKDF2WithHmacSHA256 algorithm
        PBEKeySpec spec = new PBEKeySpec(
            password.toCharArray(),
            salt,
            iterations,
            keyLength
        );
        
        SecretKeyFactory factory = SecretKeyFactory.getInstance(
            "PBKDF2WithHmacSHA256"
        );
        byte[] hash = factory.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(hash);
    }

    public boolean verifyPassword(String rawPassword) throws Exception {
        return hashPassword(rawPassword, salt).equals(passwordHash);
    }

    @Override
    public String toString() {
        return username + " (" + userType + ") - " + email + " - " + phone;
    }
}