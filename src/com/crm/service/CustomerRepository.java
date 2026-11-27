package com.crm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.crm.model.Customer;

/**
 * CustomerRepository manages in-memory storage of all customers using a HashMap.
 * OOP Concept: Encapsulation - hides the internal Map data structure so other services
 * perform storage and retrieval only through well-defined repository methods.
 * Viva Tip: A HashMap is used because it provides average O(1) constant-time lookup by ID.
 */
public class CustomerRepository {

    // Internal in-memory store mapping customerId -> Customer object
    private Map<String, Customer> customers;

    // Initializes the repository with an empty HashMap
    public CustomerRepository() {
        this.customers = new HashMap<>();
    }

    // Saves or updates a customer in the map
    public void save(Customer customer) {
        if (customer != null && customer.getCustomerId() != null) {
            this.customers.put(customer.getCustomerId(), customer);
        }
    }

    // Retrieves a customer by their unique ID, or null if not found
    public Customer findById(String customerId) {
        return this.customers.get(customerId);
    }

    // Returns a new list containing all customer objects
    public List<Customer> findAll() {
        return new ArrayList<>(this.customers.values());
    }

    // Checks if a customer with the given ID exists in the map
    public boolean exists(String customerId) {
        return this.customers.containsKey(customerId);
    }

    // Removes a customer by ID and returns the removed customer, or null if absent
    public Customer remove(String customerId) {
        return this.customers.remove(customerId);
    }

    // Returns the total number of customers currently stored
    public int count() {
        return this.customers.size();
    }

}
