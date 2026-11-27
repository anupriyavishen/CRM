package com.crm.service;

import com.crm.exception.CustomerNotFoundException;

/**
 * DeleteCustomerService removes a customer record from the system.
 * OOP Concept: Single Responsibility Principle & Exception Handling - encapsulates the deletion
 * logic and ensures checked exceptions are raised if an invalid customer ID is supplied.
 */
public class DeleteCustomerService {

    private CustomerRepository repository;

    // Receives the shared repository
    public DeleteCustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Deletes a customer by ID if they exist, or throws CustomerNotFoundException
    public void deleteCustomer(String customerId) throws CustomerNotFoundException {
        if (!repository.exists(customerId)) {
            throw new CustomerNotFoundException("Customer not found with ID '" + customerId + "'. Check the ID and try again.");
        }
        repository.remove(customerId);
    }

}
