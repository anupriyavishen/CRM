package com.crm.model;

/**
 * IndividualCustomer represents an individual consumer / retail customer.
 * OOP Concepts:
 * 1. Inheritance: Extends the abstract Customer class, inheriting common properties.
 * 2. Polymorphism: Overrides getCustomerType() and getDisplayDetails() with individual-specific data.
 * 3. Encapsulation: Stores address and age in private fields with getters and setters.
 */
public class IndividualCustomer extends Customer {

    private String address;
    private int age;

    // Default constructor
    public IndividualCustomer() {
        super();
    }

    // Parameterized constructor invoking superclass constructor via super(...)
    public IndividualCustomer(String customerId, String name, String email, String phone, String address, int age) {
        super(customerId, name, email, phone);
        this.address = address;
        this.age = age;
    }

    // Getters and Setters

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Overridden method returning the specific customer category string (Polymorphism)
    @Override
    public String getCustomerType() {
        return "Individual";
    }

    // Overridden method returning formatted individual customer details (Polymorphism)
    @Override
    public String getDisplayDetails() {
        return "Customer ID : " + getCustomerId() + "\n"
                + "Type        : " + getCustomerType() + "\n"
                + "Name        : " + getName() + "\n"
                + "Email       : " + getEmail() + "\n"
                + "Phone       : " + getPhone() + "\n"
                + "Status      : " + getStatus() + "\n"
                + "Category    : " + getCategory() + "\n"
                + "Address     : " + (address != null ? address : "N/A") + "\n"
                + "Age         : " + age;
    }

}
