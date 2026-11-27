package com.crm.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * MeetingInteraction records in-person or virtual meetings with a customer.
 * OOP Concepts:
 * 1. Inheritance: Extends Interaction to inherit ID, date, and notes.
 * 2. Polymorphism: Implements getType() returning MEETING and custom getSummary().
 * 3. Encapsulation: Stores meeting location and attendee list in private fields.
 */
public class MeetingInteraction extends Interaction {

    private String location;
    private List<String> attendees;

    // Default constructor
    public MeetingInteraction() {
        super();
        this.attendees = new ArrayList<>();
    }

    // Parameterized constructor invoking super(...)
    public MeetingInteraction(String interactionId, LocalDate date, String notes, String location, List<String> attendees) {
        super(interactionId, date, notes);
        this.location = location;
        this.attendees = (attendees != null) ? attendees : new ArrayList<>();
    }

    // Getters and Setters

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<String> attendees) {
        this.attendees = (attendees != null) ? attendees : new ArrayList<>();
    }

    // Identifies this interaction type as MEETING (Polymorphism)
    @Override
    public InteractionType getType() {
        return InteractionType.MEETING;
    }

    // Provides a formatted summary of meeting venue and attendees count (Polymorphism)
    @Override
    public String getSummary() {
        int count = (attendees != null) ? attendees.size() : 0;
        return "Meeting at " + location + " with " + count + " attendee(s): " + getNotes();
    }

}
