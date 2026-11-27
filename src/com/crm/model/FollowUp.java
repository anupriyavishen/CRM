package com.crm.model;

import java.time.LocalDate;

/**
 * FollowUp represents a scheduled task or reminder related to a customer.
 * OOP Concept: Encapsulation - stores follow-up ID, due date, description, and completion state,
 * with convenience methods like markCompleted() and isOverdue() to encapsulate business logic.
 */
public class FollowUp {

    private String followUpId;
    private LocalDate dueDate;
    private String description;
    private boolean completed;

    // Default constructor
    public FollowUp() {
        this.completed = false;
    }

    // Parameterized constructor
    public FollowUp(String followUpId, LocalDate dueDate, String description) {
        this.followUpId = followUpId;
        this.dueDate = dueDate;
        this.description = description;
        this.completed = false;
    }

    // Getters and Setters (Encapsulation)

    public String getFollowUpId() {
        return followUpId;
    }

    public void setFollowUpId(String followUpId) {
        this.followUpId = followUpId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // Checks whether the follow-up is not completed and past its due date
    public boolean isOverdue() {
        return !completed && dueDate != null && dueDate.isBefore(LocalDate.now());
    }

    // Marks the follow-up as completed
    public void markCompleted() {
        this.completed = true;
    }

    // Returns a readable overview with status tag [PENDING], [OVERDUE], or [DONE]
    @Override
    public String toString() {
        String status;
        if (completed) {
            status = "DONE";
        } else if (isOverdue()) {
            status = "OVERDUE";
        } else {
            status = "PENDING";
        }
        return followUpId + " | Due: " + dueDate + " | " + description + " [" + status + "]";
    }

}
