package com.crm.model;

import java.time.LocalDate;

/**
 * Interaction is an abstract base class representing any communication with a customer.
 * OOP Concepts:
 * 1. Abstraction: Captures common interaction details while leaving getType() and getSummary() abstract.
 * 2. Encapsulation: Private fields for interactionId, date, and notes with accessor methods.
 * 3. Polymorphism: toString() invokes getType() and getSummary() dynamically resolved at runtime.
 */
public abstract class Interaction {

    private String interactionId;
    private LocalDate date;
    private String notes;

    // Default constructor setting the interaction date to today
    public Interaction() {
        this.date = LocalDate.now();
    }

    // Parameterized constructor
    public Interaction(String interactionId, LocalDate date, String notes) {
        this.interactionId = interactionId;
        this.date = date;
        this.notes = notes;
    }

    // Getters and Setters

    public String getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(String interactionId) {
        this.interactionId = interactionId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // Formats interaction details using polymorphic getType() and getSummary()
    @Override
    public String toString() {
        return date + " [" + getType() + "] " + getSummary();
    }

    // Abstract method returning the InteractionType enum value (Polymorphism)
    public abstract InteractionType getType();

    // Abstract method returning a readable summary of the specific interaction (Polymorphism)
    public abstract String getSummary();

}
