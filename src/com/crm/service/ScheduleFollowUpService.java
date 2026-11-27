package com.crm.service;

import java.time.LocalDate;
import com.crm.exception.CustomerNotFoundException;
import com.crm.exception.InvalidInputException;
import com.crm.model.Customer;
import com.crm.model.FollowUp;

/**
 * ScheduleFollowUpService creates and schedules reminder tasks for a customer.
 * OOP Concepts: Single Responsibility Principle & Exception Handling - validates that
 * the due date is not in the past and adds the follow-up to the customer's record.
 */
public class ScheduleFollowUpService {

    private CustomerRepository repository;

    // Receives the shared repository
    public ScheduleFollowUpService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Validates due date and attaches the new follow-up to the customer
    public void scheduleFollowUp(String customerId, FollowUp followUp) throws CustomerNotFoundException, InvalidInputException {
        if (followUp == null || followUp.getDueDate() == null) {
            throw new InvalidInputException("Follow-up due date cannot be empty.");
        }
        if (followUp.getDueDate().isBefore(LocalDate.now())) {
            throw new InvalidInputException("Follow-up due date cannot be in the past (" + followUp.getDueDate() + ").");
        }

        Customer customer = repository.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID '" + customerId + "'. Check the ID and try again.");
        }

        customer.addFollowUp(followUp);
    }

}
