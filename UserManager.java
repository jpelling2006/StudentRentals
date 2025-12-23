import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    private List<User> users = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    private boolean usernameExists(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) { return true; }
        }
        return false;
    }

    // register new user
    public void register() {
        // username
        String username;

        while (true) {
            System.out.print("\nEnter username: ");
            username = scanner.nextLine();

            if (username.isBlank()) {
                System.out.println("Username cannot be empty.");
                continue;
            }

            if (username.length() > 32) {
                System.out.println("Username must up to 32 characters long.");
                continue;
            }

            if (usernameExists(username)) {
                System.out.println("Username already exists.");
                continue;
            }

            break;
        }

        // userType
        Integer typeChoice;
        String userType;
        while (true) {
            System.out.println("Please pick from the following user types:");
            System.out.println("1. Student");
            System.out.println("2. Homeowner");
            System.out.print("Enter choice (1/2): ");

            String input = scanner.nextLine();

            try {
                typeChoice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
                continue;
            }

            if (typeChoice == 1) {
                userType = "student";
                break;
            } else if (typeChoice == 2) {
                userType = "homeowner";
                break;
            } else {
                System.out.println("Please enter either 1 or 2.");
            }
        }


        String rawPassword;
        while (true) {
            System.out.print("Enter password: ");
            rawPassword = scanner.nextLine();

            if (rawPassword.isBlank()) {
                System.out.println("Input required.");
                continue;
            }

            if (rawPassword.length() < 8) {
                System.out.println("Password must be at least 8 characters.");
                continue;
            }
            
            break;
        }

        String email;
        while (true) {
            System.out.print("Enter email: ");
            email = scanner.nextLine();

            if (!User.isValidEmail(email)) { 
                System.out.println("Invalid email.");
            } else { break; }
        }

        String phone;
        while (true) {
            System.out.print("Enter phone number: ");
            phone = scanner.nextLine();

            if (!phone.matches("^\\d{10}$")) {
                System.out.println("Invalid phone number.");
            } else { break; }
        }
        
        Integer campusID = null;
        String studentNumber = null;

        if (userType.equals("student")) {
            // please change later im begging you
            // maybe start by searching by letter?
            while (true) {
                System.out.print("Enter campus ID: ");
                
                try {
                    campusID = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Please enter an integer.");
                }
            }

            while (true) {
                System.out.print("Enter your student number: ");
                studentNumber = scanner.nextLine();

                if (!studentNumber.matches("^\\d{1,32}$")) {
                    System.out.println("Student number must be between 1-32 digits long.");
                } else { break; }
            }

        } else if (userType.equals("homeowner")) {
        } else {
            System.out.println("Invalid user type.");
            return;
        }

        try {
            User newUser = new User(username, userType, rawPassword, email, phone, campusID, studentNumber);
            users.add(newUser);
            System.out.println("Registration successful!");
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    // user login
    public void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String rawPassword = scanner.nextLine();

        for (User user : users) {
            try {
                if (user.getUsername().equalsIgnoreCase(username) &&
                    user.verifyPassword(rawPassword, user.getPasswordHash(), user.getSalt())) {
                    System.out.println("Login successful.");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Login error.");
            }
        }

        System.out.println("Login failed. Username or password incorrect.");
    }

    // forgot password operation
    public void forgetPassword() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                
                try {
                    user.setPasswordHash(newPassword);
                    System.out.println("Password reset successful!");
                    return;
                } catch (Exception e) {
                    System.out.println("Password reset failed.");
                }
            }
        }
        System.out.println("User not found.");
    }

    public void start() {
        while (true) {
            System.out.println("\nUser Management System");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Forget Password");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    forgetPassword();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, 3, or 4.");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        UserManager manager = new UserManager();
        manager.start();
    }
}
