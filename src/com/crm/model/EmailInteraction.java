package com.crm.model;

import java.time.LocalDate;

/**
 * EmailInteraction records email communications sent to or received from a customer.
 * OOP Concepts:
 * 1. Inheritance: Extends Interaction to inherit ID, date, and notes.
 * 2. Polymorphism: Implements getType() returning EMAIL and custom getSummary().
 * 3. Encapsulation: Protects email subject and recipient address in private fields.
 */
public class EmailInteraction extends Interaction {

    private String subject;
    private String emailAddress;

    // Default constructor
    public EmailInteraction() {
        super();
    }

    // Parameterized constructor invoking super(...)
    public EmailInteraction(String interactionId, LocalDate date, String notes, String subject, String emailAddress) {
        super(interactionId, date, notes);
        this.subject = subject;
        this.emailAddress = emailAddress;
    }

    // Getters and Setters

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    // Identifies this interaction type as EMAIL (Polymorphism)
    @Override
    public InteractionType getType() {
        return InteractionType.EMAIL;
    }

    // Provides a formatted summary of the email details (Polymorphism)
    @Override
    public String getSummary() {
        return "Email '" + subject + "' to " + emailAddress + ": " + getNotes();
    }

}
