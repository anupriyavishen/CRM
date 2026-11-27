package com.crm.service;

import com.crm.exception.CustomerNotFoundException;
import com.crm.model.Customer;
import com.crm.model.CustomerCategory;
import com.crm.model.CustomerStatus;

/**
 * TagCustomerService modifies customer status and category tags.
 * OOP Concepts: Single Responsibility Principle & Type Safety - allows updates
 * to customer classifications strictly using the CustomerStatus and CustomerCategory enums.
 */
public class TagCustomerService {

    private CustomerRepository repository;

    // Receives the shared repository
    public TagCustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Sets a new lifecycle status (LEAD, PROSPECT, ACTIVE, INACTIVE) for the customer
    public void setStatus(String customerId, CustomerStatus status) throws CustomerNotFoundException {
        Customer customer = repository.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID '" + customerId + "'. Check the ID and try again.");
        }
        customer.setStatus(status);
    }

    // Sets a new category tier (NEW, REGULAR, VIP, WHOLESALE) for the customer
    public void setCategory(String customerId, CustomerCategory category) throws CustomerNotFoundException {
        Customer customer = repository.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID '" + customerId + "'. Check the ID and try again.");
        }
        customer.setCategory(category);
    }

}
