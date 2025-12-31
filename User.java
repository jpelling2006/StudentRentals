import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class User {
    private String username;
    private String userType; // student, homeowner, or administrator
    private String email;
    private String phone;
    private String passwordHash;
    private byte[] salt;
    private Integer campusID;
    private String studentNumber;

    // add userID back?

    public String getUsername() { return username; }
    public void setUsername(String username) {
        // add regex
        if (username == null || username.length() > 32) {
            throw new IllegalArgumentException("Username must be up to 32 characters.");
        }
        this.username = username;
    }

    public String getUserType() { return userType; }
    public void setUserType(String userType) {
        if (userType == null || (!userType.equals("student") && !userType.equals("homeowner") && !userType.equals("administrator"))) {
            throw new IllegalArgumentException("Type must be one of the following: student, homeowner, administrator.");
        }
        this.userType = userType; 
    }

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
            throw new IllegalArgumentException("Phone number must be ten digits long.");
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

    public Integer getCampusID() { return campusID; }
    public void setCampusID(Integer campusID) {
        if (!"student".equals(this.userType)) {
            throw new IllegalStateException("Only student can have a campus ID.");
        }
        this.campusID = campusID;
    }

    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) {
        if (!"student".equals(this.userType)) {
            throw new IllegalStateException("Only students can have a student number.");
        }

        if (studentNumber == null || !studentNumber.matches("^\\d{1,32}$")) {
            throw new IllegalArgumentException("Student number must be up to 32 digits long.");
        }

        this.studentNumber = studentNumber;
    }


    public static boolean isValidEmail(String email) {
        // https://www.baeldung.com/java-email-validation-regex
        String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    public byte[] getSalt() { return salt; }

    public static String hashPassword(String password, byte[] salt) throws Exception {
        // PBKDF2 hash configuration
        int iterations = 65536;
        int keyLength = 256;

        // PBKDF2WithHmacSHA256 algorithm
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(hash);
    }

    public boolean verifyPassword(String rawPassword) throws Exception {
        return hashPassword(rawPassword, salt).equals(passwordHash);
    }
}