package com.crm.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * InputValidator provides shared validation checks for user inputs.
 * OOP Concept: Encapsulation - packages validation logic in one place so
 * domain objects do not have to repeat low-level string checking.
 */
public class InputValidator {

    // Checks that a string is neither null nor just blank spaces
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // Validates basic email format: username@domain.tld (requires at least 2 letters for the domain extension)
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String trimmed = email.trim();
        if (trimmed.contains(" ")) {
            return false;
        }
        int atIndex = trimmed.indexOf('@');
        int lastAtIndex = trimmed.lastIndexOf('@');
        // Must contain exactly one '@' that is not at the start or end
        if (atIndex <= 0 || atIndex != lastAtIndex || atIndex == trimmed.length() - 1) {
            return false;
        }

        // Check the domain part after the '@'
        String domainPart = trimmed.substring(atIndex + 1);
        int dotIndex = domainPart.lastIndexOf('.');
        // Must contain a '.' with text before it, and at least 2 characters for the TLD (e.g. .com, .in)
        if (dotIndex <= 0 || dotIndex >= domainPart.length() - 2) {
            return false;
        }
        return true;
    }

    // Validates that a phone number contains exactly 10 digits
    public static boolean isValidPhone(String phone) {
        if (phone == null) {
            return false;
        }
        String trimmed = phone.trim();
        if (trimmed.length() != 10) {
            return false;
        }
        for (int i = 0; i < trimmed.length(); i++) {
            if (!Character.isDigit(trimmed.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // Validates that an amount (such as a deal value) is strictly positive
    public static boolean isPositiveAmount(double amount) {
        return amount > 0;
    }

    // Validates that a date string follows the standard yyyy-MM-dd format
    public static boolean isValidDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return false;
        }
        try {
            LocalDate.parse(date.trim());
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}
