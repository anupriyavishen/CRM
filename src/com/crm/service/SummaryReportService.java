package com.crm.service;

import java.util.List;
import com.crm.exception.CustomerNotFoundException;
import com.crm.interfaces.Reportable;
import com.crm.model.Customer;
import com.crm.model.CustomerStatus;
import com.crm.model.Deal;
import com.crm.model.DealStage;
import com.crm.model.FollowUp;
import com.crm.model.Interaction;
import com.crm.model.InteractionType;

/**
 * SummaryReportService compiles formatted analytics reports for single customers and the overall CRM.
 * OOP Concepts:
 * 1. Interface Implementation: Implements Reportable contract for customer reports.
 * 2. Method Overloading: Overloads generateReport(customerId) and generateReport().
 * 3. Polymorphism: Leverages customer.getDisplayDetails() and interaction.getType() dynamically.
 */
public class SummaryReportService implements Reportable {

    private CustomerRepository repository;

    // Receives the shared repository
    public SummaryReportService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Overloaded method 1: Generates a complete 360-degree summary report for one customer
    @Override
    public String generateReport(String customerId) throws CustomerNotFoundException {
        Customer customer = repository.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID '" + customerId + "'. Check the ID and try again.");
        }

        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("========================================\n");
        reportBuilder.append("      CUSTOMER SUMMARY REPORT           \n");
        reportBuilder.append("========================================\n");
        reportBuilder.append(customer.getDisplayDetails()).append("\n");
        reportBuilder.append("----------------------------------------\n");

        // Count interactions by type using basic for loop
        int callCount = 0;
        int emailCount = 0;
        int meetingCount = 0;
        List<Interaction> interactions = customer.getInteractions();
        if (interactions != null) {
            for (Interaction interaction : interactions) {
                if (interaction.getType() == InteractionType.CALL) {
                    callCount++;
                } else if (interaction.getType() == InteractionType.EMAIL) {
                    emailCount++;
                } else if (interaction.getType() == InteractionType.MEETING) {
                    meetingCount++;
                }
            }
        }
        int totalInteractions = (interactions != null) ? interactions.size() : 0;
        reportBuilder.append("Interactions (Total: ").append(totalInteractions).append("):\n");
        reportBuilder.append("  - Calls    : ").append(callCount).append("\n");
        reportBuilder.append("  - Emails   : ").append(emailCount).append("\n");
        reportBuilder.append("  - Meetings : ").append(meetingCount).append("\n");

        if (interactions != null && !interactions.isEmpty()) {
            Interaction lastInteraction = interactions.get(interactions.size() - 1);
            reportBuilder.append("  Last Interaction: ").append(lastInteraction.toString()).append("\n");
        } else {
            reportBuilder.append("  Last Interaction: None\n");
        }

        reportBuilder.append("----------------------------------------\n");

        // Calculate deal totals
        double totalDealValue = 0.0;
        double totalWonValue = 0.0;
        List<Deal> deals = customer.getDeals();
        int totalDeals = (deals != null) ? deals.size() : 0;
        reportBuilder.append("Deals (Total: ").append(totalDeals).append("):\n");
        if (deals != null && !deals.isEmpty()) {
            for (Deal deal : deals) {
                reportBuilder.append("  * ").append(deal.toString()).append("\n");
                totalDealValue += deal.getAmount();
                if (deal.getStage() == DealStage.WON) {
                    totalWonValue += deal.getAmount();
                }
            }
        } else {
            reportBuilder.append("  No deals recorded.\n");
        }
        reportBuilder.append("  Total Deal Value : Rs. ").append(totalDealValue).append("\n");
        reportBuilder.append("  Total WON Value  : Rs. ").append(totalWonValue).append("\n");

        reportBuilder.append("----------------------------------------\n");

        // Count pending follow-ups
        List<FollowUp> followUps = customer.getFollowUps();
        int pendingCount = 0;
        if (followUps != null) {
            for (FollowUp followUp : followUps) {
                if (!followUp.isCompleted()) {
                    pendingCount++;
                }
            }
        }
        reportBuilder.append("Pending Follow-ups (").append(pendingCount).append("):\n");
        if (followUps != null && !followUps.isEmpty()) {
            for (FollowUp followUp : followUps) {
                if (!followUp.isCompleted()) {
                    reportBuilder.append("  * ").append(followUp.toString()).append("\n");
                }
            }
        }
        if (pendingCount == 0) {
            reportBuilder.append("  No pending follow-ups.\n");
        }
        reportBuilder.append("========================================\n");

        return reportBuilder.toString();
    }

    // Overloaded method 2: Generates high-level metrics across the entire CRM system
    public String generateReport() {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("========================================\n");
        reportBuilder.append("       OVERALL CRM SUMMARY REPORT       \n");
        reportBuilder.append("========================================\n");

        List<Customer> allCustomers = repository.findAll();
        reportBuilder.append("Total Customers: ").append(allCustomers.size()).append("\n\n");

        int leadCount = 0;
        int prospectCount = 0;
        int activeCount = 0;
        int inactiveCount = 0;

        int totalInteractions = 0;
        double totalPipelineValue = 0.0; // Value of active deals that are not WON or LOST
        double totalWonRevenue = 0.0;
        int totalPendingFollowUps = 0;

        for (Customer customer : allCustomers) {
            if (customer.getStatus() == CustomerStatus.LEAD) {
                leadCount++;
            } else if (customer.getStatus() == CustomerStatus.PROSPECT) {
                prospectCount++;
            } else if (customer.getStatus() == CustomerStatus.ACTIVE) {
                activeCount++;
            } else if (customer.getStatus() == CustomerStatus.INACTIVE) {
                inactiveCount++;
            }

            if (customer.getInteractions() != null) {
                totalInteractions += customer.getInteractions().size();
            }

            if (customer.getDeals() != null) {
                for (Deal deal : customer.getDeals()) {
                    if (deal.getStage() == DealStage.WON) {
                        totalWonRevenue += deal.getAmount();
                    } else if (deal.getStage() != DealStage.LOST) {
                        totalPipelineValue += deal.getAmount();
                    }
                }
            }

            if (customer.getFollowUps() != null) {
                for (FollowUp followUp : customer.getFollowUps()) {
                    if (!followUp.isCompleted()) {
                        totalPendingFollowUps++;
                    }
                }
            }
        }

        reportBuilder.append("Customers by Status:\n");
        reportBuilder.append("  - LEAD     : ").append(leadCount).append("\n");
        reportBuilder.append("  - PROSPECT : ").append(prospectCount).append("\n");
        reportBuilder.append("  - ACTIVE   : ").append(activeCount).append("\n");
        reportBuilder.append("  - INACTIVE : ").append(inactiveCount).append("\n\n");

        reportBuilder.append("Total Interactions Logged: ").append(totalInteractions).append("\n");
        reportBuilder.append("Total Active Pipeline    : Rs. ").append(totalPipelineValue).append("\n");
        reportBuilder.append("Total Won Revenue        : Rs. ").append(totalWonRevenue).append("\n");
        reportBuilder.append("Total Pending Follow-ups : ").append(totalPendingFollowUps).append("\n");
        reportBuilder.append("========================================\n");

        return reportBuilder.toString();
    }

}
