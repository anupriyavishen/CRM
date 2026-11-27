package com.crm.service;

import java.util.List;
import com.crm.exception.CustomerNotFoundException;
import com.crm.model.Customer;

/**
 * ViewCustomerService retrieves customer records for display.
 * OOP Concepts:
 * 1. Single Responsibility Principle: Dedicated purely to viewing single or multiple customers.
 * 2. Method Overloading (Compile-time Polymorphism): Uses two viewCustomer() methods with different parameter lists.
 */
public class ViewCustomerService {

    private CustomerRepository repository;

    // Receives the shared repository
    public ViewCustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Overloaded method 1: Retrieves a single customer by ID or throws CustomerNotFoundException
    public Customer viewCustomer(String customerId) throws CustomerNotFoundException {
        Customer customer = repository.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID '" + customerId + "'. Check the ID and try again.");
        }
        return customer;
    }

    // Overloaded method 2: Retrieves the complete list of all registered customers
    public List<Customer> viewCustomer() {
        return repository.findAll();
    }

}
