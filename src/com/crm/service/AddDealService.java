package com.crm.service;

import com.crm.exception.CustomerNotFoundException;
import com.crm.exception.InvalidDealException;
import com.crm.model.Customer;
import com.crm.model.Deal;
import com.crm.util.InputValidator;

/**
 * AddDealService registers a new deal for a customer.
 * OOP Concepts: Single Responsibility Principle & Exception Handling - checks deal title
 * and positive amount before appending the deal to the customer.
 */
public class AddDealService {

    private CustomerRepository repository;

    // Receives the shared repository
    public AddDealService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Validates deal title, positive amount, customer existence, then saves the deal
    public void addDeal(String customerId, Deal deal) throws CustomerNotFoundException, InvalidDealException {
        if (deal == null) {
            throw new InvalidDealException("Deal details cannot be empty.");
        }
        if (!InputValidator.isNotEmpty(deal.getTitle())) {
            throw new InvalidDealException("Deal title cannot be empty.");
        }
        if (!InputValidator.isPositiveAmount(deal.getAmount())) {
            throw new InvalidDealException("Deal amount must be greater than zero. Provided: " + deal.getAmount());
        }

        Customer customer = repository.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID '" + customerId + "'. Check the ID and try again.");
        }

        customer.addDeal(deal);
    }

}
