package com.crm.exception;

/**
 * CustomerNotFoundException is thrown when a customer ID cannot be found in the repository.
 * OOP Concept: Exception Handling and Inheritance - extends the standard Exception class
 * to define a specific checked exception for missing customer records.
 */
public class CustomerNotFoundException extends Exception {

    // Creates an exception with an informative error message
    public CustomerNotFoundException(String message) {
        super(message);
    }

}
