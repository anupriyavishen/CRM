package com.crm.exception;

/**
 * InvalidDealException is thrown when deal data is invalid or when an illegal deal stage update is attempted.
 * OOP Concept: Exception Handling and Inheritance - extends Exception to safeguard deal business rules
 * (for example, preventing changes to already closed WON or LOST deals).
 */
public class InvalidDealException extends Exception {

    // Creates an exception with an informative error message
    public InvalidDealException(String message) {
        super(message);
    }

}
