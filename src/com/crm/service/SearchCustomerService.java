package com.crm.service;

import java.util.ArrayList;
import java.util.List;
import com.crm.interfaces.Searchable;
import com.crm.model.Customer;
import com.crm.model.CustomerStatus;

/**
 * SearchCustomerService provides flexible customer search capabilities.
 * OOP Concepts:
 * 1. Interface Implementation: Implements Searchable<Customer> for contract-driven design.
 * 2. Method Overloading: Provides two search methods (by keyword String and by CustomerStatus enum).
 */
public class SearchCustomerService implements Searchable<Customer> {

    private CustomerRepository repository;

    // Receives the shared repository
    public SearchCustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Searches customers whose name, email, or phone number contains the keyword (case-insensitive)
    @Override
    public List<Customer> search(String keyword) {
        List<Customer> matchingCustomers = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return matchingCustomers;
        }

        String searchLower = keyword.trim().toLowerCase();
        for (Customer customer : repository.findAll()) {
            boolean matched = false;

            if (customer.getName() != null && customer.getName().toLowerCase().contains(searchLower)) {
                matched = true;
            } else if (customer.getEmail() != null && customer.getEmail().toLowerCase().contains(searchLower)) {
                matched = true;
            } else if (customer.getPhone() != null && customer.getPhone().contains(searchLower)) {
                matched = true;
            }

            if (matched) {
                matchingCustomers.add(customer);
            }
        }
        return matchingCustomers;
    }

    // Filters customers matching the specified lifecycle status (LEAD, PROSPECT, ACTIVE, INACTIVE)
    public List<Customer> search(CustomerStatus status) {
        List<Customer> matchingCustomers = new ArrayList<>();
        if (status == null) {
            return matchingCustomers;
        }

        for (Customer customer : repository.findAll()) {
            if (customer.getStatus() == status) {
                matchingCustomers.add(customer);
            }
        }
        return matchingCustomers;
    }

}
