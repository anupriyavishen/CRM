package com.crm.exception;

/**
 * InvalidInputException is thrown when user input fails validation (such as an invalid email or phone number).
 * OOP Concept: Exception Handling and Inheritance - extends Exception so calling code can intercept and display
 * friendly error messages without crashing the program.
 */
public class InvalidInputException extends Exception {

    // Creates an exception with an informative error message
    public InvalidInputException(String message) {
        super(message);
    }

}
