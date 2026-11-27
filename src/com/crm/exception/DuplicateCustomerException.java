package com.crm.exception;

/**
 * DuplicateCustomerException is thrown when trying to add a customer whose ID or email already exists.
 * OOP Concept: Exception Handling and Inheritance - extends Exception to provide custom domain-level
 * protection against duplicate database/repository entries.
 */
public class DuplicateCustomerException extends Exception {

    // Creates an exception with an informative error message
    public DuplicateCustomerException(String message) {
        super(message);
    }

}
