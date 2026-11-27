package com.crm.model;

import java.util.ArrayList;
import java.util.List;
import com.crm.interfaces.Validatable;
import com.crm.exception.InvalidInputException;
import com.crm.util.InputValidator;

/**
 * Customer is an abstract base class representing any customer in the CRM.
 * OOP Concepts:
 * 1. Abstraction: Cannot be instantiated directly; defines abstract methods getCustomerType()
 *    and getDisplayDetails() that subclasses must implement.
 * 2. Encapsulation: Private member fields accessible through public getters and setters.
 * 3. Interface Implementation: Implements Validatable to enforce data validity.
 */
public abstract class Customer implements Validatable {

    // Encapsulated customer properties
    private String customerId;
    private String name;
    private String email;
    private String phone;
    private CustomerStatus status;
    private CustomerCategory category;

    // Associated records for this customer (Composition)
    private List<Interaction> interactions;
    private List<Deal> deals;
    private List<FollowUp> followUps;

    // Default constructor initializing empty collections and default status/category
    public Customer() {
        this.interactions = new ArrayList<>();
        this.deals = new ArrayList<>();
        this.followUps = new ArrayList<>();
        this.status = CustomerStatus.LEAD;
        this.category = CustomerCategory.NEW;
    }

    // Parameterized constructor for basic contact details
    public Customer(String customerId, String name, String email, String phone) {
        this();
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters (Encapsulation)

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public CustomerCategory getCategory() {
        return category;
    }

    public void setCategory(CustomerCategory category) {
        this.category = category;
    }

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }

    public List<FollowUp> getFollowUps() {
        return followUps;
    }

    public void setFollowUps(List<FollowUp> followUps) {
        this.followUps = followUps;
    }

    // Validates customer basic contact details using InputValidator
    @Override
    public void validate() throws InvalidInputException {
        if (!InputValidator.isNotEmpty(name)) {
            throw new InvalidInputException("Customer name cannot be empty.");
        }
        if (!InputValidator.isValidEmail(email)) {
            throw new InvalidInputException("Invalid customer email address: " + email);
        }
        if (!InputValidator.isValidPhone(phone)) {
            throw new InvalidInputException("Invalid customer phone number (must be 10 digits): " + phone);
        }
    }

    // Adds a communication interaction to this customer's history
    public void addInteraction(Interaction interaction) {
        if (interaction != null) {
            this.interactions.add(interaction);
        }
    }

    // Adds a business deal to this customer's deal pipeline
    public void addDeal(Deal deal) {
        if (deal != null) {
            this.deals.add(deal);
        }
    }

    // Adds a scheduled follow-up reminder for this customer
    public void addFollowUp(FollowUp followUp) {
        if (followUp != null) {
            this.followUps.add(followUp);
        }
    }

    // Returns a concise single-line summary of the customer
    @Override
    public String toString() {
        return customerId + " - " + name + " (" + status + ")";
    }

    // Abstract method: returns either "Individual" or "Business" (Polymorphism)
    public abstract String getCustomerType();

    // Abstract method: returns a multi-line formatted string of all customer details (Polymorphism)
    public abstract String getDisplayDetails();

}
