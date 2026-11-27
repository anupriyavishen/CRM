package com.crm.service;

import com.crm.exception.DuplicateCustomerException;
import com.crm.exception.InvalidInputException;
import com.crm.model.Customer;

/**
 * AddCustomerService handles the registration of new customers.
 * OOP Concept: Single Responsibility Principle & Exception Handling - focuses exclusively on
 * registering a customer, validating fields polymorphically, and throwing checked exceptions
 * if duplicates or invalid values are detected.
 */
public class AddCustomerService {

    private CustomerRepository repository;

    // Receives the shared repository via constructor dependency injection
    public AddCustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Validates customer, checks for duplicate ID/email, and saves to repository
    public void addCustomer(Customer customer) throws DuplicateCustomerException, InvalidInputException {
        if (customer == null) {
            throw new InvalidInputException("Customer details cannot be empty.");
        }

        // Polymorphic validation: calls customer.validate() based on the actual object type
        customer.validate();

        // Check for duplicate customer ID
        if (repository.exists(customer.getCustomerId())) {
            throw new DuplicateCustomerException("A customer with ID '" + customer.getCustomerId() + "' already exists.");
        }

        // Check for duplicate email address across existing customers (case-insensitive)
        for (Customer existing : repository.findAll()) {
            if (existing.getEmail() != null && existing.getEmail().equalsIgnoreCase(customer.getEmail())) {
                throw new DuplicateCustomerException("A customer with email '" + customer.getEmail() + "' already exists.");
            }
        }

        repository.save(customer);
    }

}
