package com.crm.service;

import com.crm.exception.CustomerNotFoundException;
import com.crm.model.Customer;
import com.crm.model.Interaction;

/**
 * LogInteractionService attaches communications (calls, emails, meetings) to a customer.
 * OOP Concepts:
 * 1. Single Responsibility Principle: Dedicated purely to appending interaction logs.
 * 2. Polymorphism: Accepts any concrete Interaction subclass (Call, Email, Meeting) seamlessly.
 */
public class LogInteractionService {

    private CustomerRepository repository;

    // Receives the shared repository
    public LogInteractionService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Finds the customer and appends the polymorphic interaction to their record
    public void logInteraction(String customerId, Interaction interaction) throws CustomerNotFoundException {
        Customer customer = repository.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID '" + customerId + "'. Check the ID and try again.");
        }
        customer.addInteraction(interaction);
    }

}
