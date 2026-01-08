package Helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public final class Helpers {
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
                System.out.println("Input must be under " + maxLength + " characters.");
            }
            else { return input; }
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

    public static Double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try { return Double.parseDouble(input); }
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid decimal number.");
            }
        }
    }

    public static <T> T selectFromList(
        Scanner scanner,
        List<T> items,
        String prompt
    ) {
        if (items == null || items.isEmpty()) {
            System.out.println("No options available.");
            return null;
        }

        for (int i = 0; i < items.size(); i++) {
            System.out.println((i+1) + ". " + items.get(i));
        }

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

    public static LocalDate readDate(
        Scanner scanner,
        String prompt
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (true) {
            System.out.print(prompt + " (yyyy-mm-dd): ");
            String input = scanner.nextLine();

            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format.");
            }
        }
    }

    public static LocalDate readFutureDate(
        Scanner scanner,
        String prompt
    ) {
        while (true) {
            LocalDate date = readDate(scanner, prompt);

            if (!date.isBefore(LocalDate.now())) {
                return date;
            }

            System.out.println("Date must be today or in the future.");
        }
    }

    public static boolean confirm(Scanner scanner) {
        while (true) {
            System.out.print("Confirm (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y")) return true;
            if (input.equals("n")) return false;

            System.out.println("Please enter 'y' or 'n'.");
        }
    }
}
