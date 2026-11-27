package com.crm.model;

import java.time.LocalDate;

/**
 * CallInteraction records phone call communications.
 * OOP Concepts:
 * 1. Inheritance: Extends Interaction to inherit common date, ID, and notes.
 * 2. Polymorphism: Implements getType() returning CALL and custom getSummary().
 * 3. Encapsulation: Stores call duration and phone number in private fields.
 */
public class CallInteraction extends Interaction {

    private int durationMinutes;
    private String phoneNumber;

    // Default constructor
    public CallInteraction() {
        super();
    }

    // Parameterized constructor invoking super(...)
    public CallInteraction(String interactionId, LocalDate date, String notes, int durationMinutes, String phoneNumber) {
        super(interactionId, date, notes);
        this.durationMinutes = durationMinutes;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Identifies this interaction type as CALL (Polymorphism)
    @Override
    public InteractionType getType() {
        return InteractionType.CALL;
    }

    // Provides a formatted summary of the call details (Polymorphism)
    @Override
    public String getSummary() {
        return "Call (" + durationMinutes + " mins) to " + phoneNumber + ": " + getNotes();
    }

}
