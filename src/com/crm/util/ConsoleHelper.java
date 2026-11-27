package com.crm.util;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * ConsoleHelper handles safe text input and output with the user.
 * OOP Concept: Encapsulation - hides Scanner setup and input parsing details
 * behind easy-to-use methods so other classes stay simple and clean.
 */
public class ConsoleHelper {

    private Scanner scanner;

    // Initializes the ConsoleHelper with standard system input
    public ConsoleHelper() {
        this.scanner = new Scanner(System.in);
    }

    // Prompts the user and reads a trimmed line of text
    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // Repeatedly prompts until the user enters a valid whole integer
    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid whole number.");
            }
        }
    }

    // Repeatedly prompts until the user enters a valid decimal number
    public double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid numerical amount.");
            }
        }
    }

    // Repeatedly prompts until the user enters a valid date in yyyy-MM-dd format
    public LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (InputValidator.isValidDate(input)) {
                return LocalDate.parse(input);
            }
            System.out.println("Invalid date format. Please use yyyy-MM-dd (for example: 2026-10-15).");
        }
    }

    // Prints a message followed by a new line to the console
    public void printLine(String message) {
        System.out.println(message);
    }

    // Pauses console output until the user presses Enter
    public void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

}
