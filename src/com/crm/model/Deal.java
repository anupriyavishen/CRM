package com.crm.model;

import java.time.LocalDate;

/**
 * Deal represents a business opportunity or sales deal associated with a customer.
 * OOP Concept: Encapsulation - bundles deal ID, title, monetary amount, stage,
 * and expected closing date within private fields accessible through methods.
 */
public class Deal {

    private String dealId;
    private String title;
    private double amount;
    private DealStage stage;
    private LocalDate closeDate;

    // Default constructor initializing stage to NEW
    public Deal() {
        this.stage = DealStage.NEW;
    }

    // Parameterized constructor
    public Deal(String dealId, String title, double amount, LocalDate closeDate) {
        this.dealId = dealId;
        this.title = title;
        this.amount = amount;
        this.stage = DealStage.NEW;
        this.closeDate = closeDate;
    }

    // Getters and Setters (Encapsulation)

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public DealStage getStage() {
        return stage;
    }

    public void setStage(DealStage stage) {
        this.stage = stage;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    // Returns a readable single-line overview of the deal
    @Override
    public String toString() {
        return dealId + " | " + title + " | Rs. " + amount + " | " + stage + " | " + closeDate;
    }

}
