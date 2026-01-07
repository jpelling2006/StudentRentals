package Helpers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Helpers {
    public static int selectFromList(Scanner scanner, int size, String prompt) {
        while (true) {
            System.out.print(prompt + " (1-" + size + "): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > size) {
                    System.out.println("Invalid selection.");
                    continue;
                }
                return choice;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    public static boolean confirm(Scanner scanner) {
        while (true) {
            System.out.print("(Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equalsIgnoreCase("Y")) { return true; }
            else if (input.equalsIgnoreCase("N")) { return false; }
            System.out.println("Please enter Y or N.");
        }
    }

    public static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static LocalDate readFutureDate(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " (YYYY-MM-DD): ");
            try {
                LocalDate date = LocalDate.parse(scanner.nextLine());
                if (date.isBefore(LocalDate.now())) {
                    System.out.println("Date cannot be in the past.");
                } else { return date; }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format.");
            }
        }
    }


}
