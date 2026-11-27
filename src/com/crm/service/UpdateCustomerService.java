package com.crm.service;

import com.crm.exception.CustomerNotFoundException;
import com.crm.exception.InvalidInputException;
import com.crm.model.Customer;
import com.crm.util.InputValidator;

/**
 * UpdateCustomerService updates editable contact information for an existing customer.
 * OOP Concepts: Single Responsibility Principle & Exception Handling - focuses exclusively
 * on safely modifying existing records with thorough validation and duplicate-email prevention.
 */
public class UpdateCustomerService {

    private CustomerRepository repository;

    // Receives the shared repository
    public UpdateCustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Updates name, email, and phone for a customer; skips any fields left blank
    public void updateCustomer(String customerId, String newName, String newEmail, String newPhone)
            throws CustomerNotFoundException, InvalidInputException {

        Customer customer = repository.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID '" + customerId + "'. Check the ID and try again.");
        }

        // Update name if a non-empty value was provided
        if (newName != null && !newName.trim().isEmpty()) {
            customer.setName(newName.trim());
        }

        // Validate and update email if provided
        if (newEmail != null && !newEmail.trim().isEmpty()) {
            String trimmedEmail = newEmail.trim();
            if (!InputValidator.isValidEmail(trimmedEmail)) {
                throw new InvalidInputException("Invalid email format: " + trimmedEmail);
            }
            // Ensure email is not already taken by another customer
            for (Customer other : repository.findAll()) {
                if (!other.getCustomerId().equals(customerId)
                        && other.getEmail() != null
                        && other.getEmail().equalsIgnoreCase(trimmedEmail)) {
                    throw new InvalidInputException("Email '" + trimmedEmail + "' is already in use by customer " + other.getCustomerId());
                }
            }
            customer.setEmail(trimmedEmail);
        }

        // Validate and update phone if provided
        if (newPhone != null && !newPhone.trim().isEmpty()) {
            String trimmedPhone = newPhone.trim();
            if (!InputValidator.isValidPhone(trimmedPhone)) {
                throw new InvalidInputException("Invalid phone number. Must be exactly 10 digits: " + trimmedPhone);
            }
            customer.setPhone(trimmedPhone);
        }
    }

}
