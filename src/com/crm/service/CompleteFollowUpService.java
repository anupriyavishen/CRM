package com.crm.service;

import com.crm.exception.CustomerNotFoundException;
import com.crm.exception.InvalidInputException;
import com.crm.model.Customer;
import com.crm.model.FollowUp;

/**
 * CompleteFollowUpService marks an existing customer follow-up as completed.
 * OOP Concepts: Single Responsibility Principle & Exception Handling - verifies the
 * existence of the customer and the follow-up, then updates its internal completion status.
 */
public class CompleteFollowUpService {

    private CustomerRepository repository;

    // Receives the shared repository
    public CompleteFollowUpService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Finds the customer and follow-up by ID, and marks the follow-up completed
    public void completeFollowUp(String customerId, String followUpId) throws CustomerNotFoundException, InvalidInputException {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new InvalidInputException("Customer ID cannot be empty.");
        }
        if (followUpId == null || followUpId.trim().isEmpty()) {
            throw new InvalidInputException("Follow-up ID cannot be empty.");
        }

        Customer customer = repository.findById(customerId.trim());
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID '" + customerId + "'. Check the ID and try again.");
        }

        // Search the customer's follow-up list using a simple loop
        FollowUp targetFollowUp = null;
        if (customer.getFollowUps() != null) {
            for (FollowUp followUp : customer.getFollowUps()) {
                if (followUp.getFollowUpId() != null && followUp.getFollowUpId().equalsIgnoreCase(followUpId.trim())) {
                    targetFollowUp = followUp;
                    break;
                }
            }
        }

        if (targetFollowUp == null) {
            throw new InvalidInputException("Follow-up '" + followUpId + "' not found for customer '" + customerId + "'.");
        }

        if (targetFollowUp.isCompleted()) {
            throw new InvalidInputException("Follow-up '" + followUpId + "' is already marked as completed.");
        }

        targetFollowUp.markCompleted();
    }

}
