package com.crm.model;

/**
 * BusinessCustomer represents a corporate or enterprise B2B customer.
 * OOP Concepts:
 * 1. Inheritance: Extends Customer to inherit shared attributes while adding B2B fields.
 * 2. Polymorphism: Implements specialized versions of getCustomerType() and getDisplayDetails().
 * 3. Encapsulation: Encapsulates company name, GST registration number, and industry sector.
 */
public class BusinessCustomer extends Customer {

    private String companyName;
    private String gstNumber;
    private String industry;

    // Default constructor
    public BusinessCustomer() {
        super();
    }

    // Parameterized constructor invoking Customer superconstructor
    public BusinessCustomer(String customerId, String name, String email, String phone, String companyName, String gstNumber, String industry) {
        super(customerId, name, email, phone);
        this.companyName = companyName;
        this.gstNumber = gstNumber;
        this.industry = industry;
    }

    // Getters and Setters

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    // Overridden method identifying the customer as Business (Polymorphism)
    @Override
    public String getCustomerType() {
        return "Business";
    }

    // Overridden method providing formatted B2B customer view (Polymorphism)
    @Override
    public String getDisplayDetails() {
        return "Customer ID  : " + getCustomerId() + "\n"
                + "Type         : " + getCustomerType() + "\n"
                + "Contact Name : " + getName() + "\n"
                + "Email        : " + getEmail() + "\n"
                + "Phone        : " + getPhone() + "\n"
                + "Status       : " + getStatus() + "\n"
                + "Category     : " + getCategory() + "\n"
                + "Company Name : " + (companyName != null ? companyName : "N/A") + "\n"
                + "GST Number   : " + (gstNumber != null ? gstNumber : "N/A") + "\n"
                + "Industry     : " + (industry != null ? industry : "N/A");
    }

}
