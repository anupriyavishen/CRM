package com.crm.service;

import java.util.List;
import com.crm.exception.CustomerNotFoundException;
import com.crm.model.Customer;
import com.crm.model.Interaction;

/**
 * InteractionHistoryService fetches the communication timeline for a customer.
 * OOP Concept: Single Responsibility Principle & Encapsulation - safely accesses the
 * customer's encapsulated interaction history without exposing internal storage.
 */
public class InteractionHistoryService {

    private CustomerRepository repository;

    // Receives the shared repository
    public InteractionHistoryService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Retrieves all logged interactions for the specified customer ID
    public List<Interaction> viewHistory(String customerId) throws CustomerNotFoundException {
        Customer customer = repository.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID '" + customerId + "'. Check the ID and try again.");
        }
        return customer.getInteractions();
    }

}
