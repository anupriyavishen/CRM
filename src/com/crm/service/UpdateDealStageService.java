package com.crm.service;

import com.crm.exception.CustomerNotFoundException;
import com.crm.exception.InvalidDealException;
import com.crm.model.Customer;
import com.crm.model.Deal;
import com.crm.model.DealStage;

/**
 * UpdateDealStageService manages the transition of a deal across sales pipeline stages.
 * OOP Concepts: Single Responsibility Principle & Exception Handling - enforces business rules
 * (for example, deals already WON or LOST cannot be edited further).
 */
public class UpdateDealStageService {

    private CustomerRepository repository;

    // Receives the shared repository
    public UpdateDealStageService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Finds the customer deal and advances its stage unless already closed
    public void updateStage(String customerId, String dealId, DealStage newStage)
            throws CustomerNotFoundException, InvalidDealException {

        Customer customer = repository.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID '" + customerId + "'. Check the ID and try again.");
        }

        // Search the customer's deals for the specified dealId
        Deal targetDeal = null;
        if (customer.getDeals() != null) {
            for (Deal deal : customer.getDeals()) {
                if (deal.getDealId() != null && deal.getDealId().equalsIgnoreCase(dealId)) {
                    targetDeal = deal;
                    break;
                }
            }
        }

        if (targetDeal == null) {
            throw new InvalidDealException("Deal with ID '" + dealId + "' not found for customer '" + customerId + "'.");
        }

        // Business Rule: Closed deals cannot be modified
        if (targetDeal.getStage() == DealStage.WON || targetDeal.getStage() == DealStage.LOST) {
            throw new InvalidDealException("Cannot modify deal '" + dealId + "' because it is already in final closed state: " + targetDeal.getStage());
        }

        targetDeal.setStage(newStage);
    }

}
