package com.crm.model;

/**
 * CustomerStatus represents the lead lifecycle stages of a customer.
 * OOP Concept: Enum / Type Safety - guarantees only valid lifecycle states (LEAD, PROSPECT, ACTIVE, INACTIVE)
 * can be assigned to a customer.
 */
public enum CustomerStatus {
    LEAD, PROSPECT, ACTIVE, INACTIVE
}
