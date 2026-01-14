package helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Helpers {
    private static Helpers instance;

    public static Helpers getInstance() {
        if (instance == null) { instance = new Helpers(); }
        return instance;
    }

    // prevent instantiation
    private Helpers() {}

    public static String readString(
        Scanner scanner,
        String prompt,
        Integer maxLength
    ) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) { System.out.println("Input cannot be empty."); }
            else if (input.length() > maxLength) {
                System.out.println(
                    "Input must be under " 
                    + maxLength 
                    + " characters."
                );
            }
            else { return input; }
        }
    }

    public static String readOptionalString(
        Scanner scanner,
        String prompt,
        Integer maxLength
    ) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            // allows null values
            if (input.isEmpty()) { return null; }

            else if (input.length() > maxLength) {
                System.out.println(
                    "Input must be under " 
                    + maxLength 
                    + " characters."
                );
            }
            else { return input; }
        }
    }

    public static String readEmail(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = readString(scanner, prompt, 64);

            Pattern pattern = Pattern.compile(
                "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
            ); // https://www.baeldung.com/java-email-validation-regex
            Matcher matcher = pattern.matcher(input);

            if (!matcher.matches()) {
                System.out.print(
                    "Invalid email. Please use the format \"example@example.com\"."
                );
            } else { return input; }
        }
    }

    public static String readPhoneNumber(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = readString(scanner, prompt, 32);

            Pattern pattern = Pattern.compile("^\\d{10}$");
            Matcher matcher = pattern.matcher(input);

            if (!matcher.matches()) {
                System.out.print(
                    "Invalid phone number. Please enter ten digits."
                );
            } else { return input; }
        }
    }

    public static Integer readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try { return Integer.parseInt(input); }
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static Integer readOptionalInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) return null;

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number or leave blank.");
            }
        }
    }

    public static Integer readIntInRange(
        Scanner scanner,
        String prompt,
        Integer min,
        Integer max
    ) {
        while (true) {
            Integer value = readInt(scanner, prompt);

            if (value >= min && value <= max) { return value; }

            System.out.println(
                "Please enter a number between " 
                + min 
                + " and " 
                + max + "."
            );
        }
    }

    public static Integer readOptionalIntInRange(
        Scanner scanner,
        String prompt,
        Integer min,
        Integer max
    ) {
        while (true) {
            Integer value = readOptionalInt(scanner, prompt); // use blankable int

            if (value == null) return null;

            if (value >= min && value <= max) return value;

            System.out.println(
                "Please enter a number between " 
                + min 
                + " and "
                + max 
                + " or leave blank."
            );
        }
    }

    public static Double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try { return Double.parseDouble(input); }
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid decimal number.");
            }
        }
    }

    public static Double readOptionalDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            // allows blank input
            if (input.isEmpty()) { 
                return null; 
            }

            try { 
                return Double.parseDouble(input); 
            } catch (NumberFormatException e) {
                System.out.println(
                    "Please enter a valid decimal number or leave blank."
                );
            }
        }
    }

    public static <T> T selectFromList(
        Scanner scanner,
        List<T> items,
        String prompt,
        Function<T, String> formatter
    ) {
        if (items == null || items.isEmpty()) {
            System.out.println("No options available.");
            return null;
        }

        printIndexed(items, formatter);

        Integer choice = readIntInRange(scanner, prompt + ": ", 1, items.size());
        return items.get(choice - 1);
    }

    public static <T extends Enum<T>> T readEnum(
        Scanner scanner,
        String prompt,
        Class<T> enumType
    ) {
        T[] values = enumType.getEnumConstants();

        while (true) {
            System.out.println(prompt);
            for (int i = 0; i < values.length; i++) {
                System.out.println((i + 1) + ". " + values[i].name());
            }

            Integer choice = readIntInRange(
                scanner, 
                "Choose option: ", 
                1, 
                values.length
            );
            return values[choice - 1];
        }
    }

    public static <T extends Enum<T>> T readOptionalEnum(
        Scanner scanner,
        String prompt,
        Class<T> enumType
    ) {
        T[] values = enumType.getEnumConstants();

        while (true) {
            System.out.println(prompt);
            for (int i = 0; i < values.length; i++) {
                System.out.println((i + 1) + ". " + values[i].name());
            }

            Integer choice = readOptionalIntInRange(
                scanner, 
                "Choose option (leave blank for any): ", 
                1, 
                values.length
            );

            if (choice == null) return null;

            return values[choice - 1];
        }
    }

    public static LocalDate readDate(Scanner scanner, String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd"
        );

        while (true) {
            System.out.print(prompt + " (yyyy-mm-dd): ");
            String input = scanner.nextLine();

            try { return LocalDate.parse(input, formatter); }
            catch (DateTimeParseException e) {
                System.out.println("Invalid date format.");
            }
        }
    }

    public static LocalDate readOptionalDate(Scanner scanner, String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd"
        );

        while (true) {
            System.out.print(prompt + " (yyyy-mm-dd, leave blank for any): ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) { return null; }

            try { 
                return LocalDate.parse(input, formatter); 
            } catch (DateTimeParseException e) {
                System.out.println(
                    "Invalid date format. Please use yyyy-MM-dd or leave blank."
                );
            }
        }
    }

    public static LocalDate readFutureDate(Scanner scanner, String prompt) {
        while (true) {
            LocalDate date = readDate(scanner, prompt);

            if (!date.isBefore(LocalDate.now())) { return date; }

            System.out.println("Date must be today or in the future.");
        }
    }

    public static LocalDate readOptionalFutureDate(Scanner scanner, String prompt) {
        while (true) {
            LocalDate date = readOptionalDate(scanner, prompt);

            if (date == null) { return null; }

            if (!date.isBefore(LocalDate.now())) { return date; }

            System.out.println("Date must be today or in the future.");
        }
    }

    public static boolean confirm(Scanner scanner) {
        while (true) {
            System.out.print("Confirm (y/n): ");

            // converts to lowercase just in case user inputs "Y" or "N"
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y")) { return true; }
            if (input.equals("n")) { return false; }

            System.out.println("Please enter 'y' or 'n'.");
        }
    }

    // prints formatted list using .toString() overrides given in object classes
    public static <T> void printIndexed(
        List<T> list,
        Function<T, String> formatter
    ) {
        if (list == null || list.isEmpty()) {
            System.out.println("No items found.");
            return;
        }

        // applies format for each item in list
        for (int i = 0; i < list.size(); i++) {
            System.out.println("\n" + (i + 1) + ". " + formatter.apply(list.get(i)));
        }
    }
}
