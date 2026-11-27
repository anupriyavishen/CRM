package com.crm.interfaces;

import com.crm.exception.CustomerNotFoundException;

/**
 * Reportable defines a contract for services that generate formatted textual reports.
 * OOP Concept: Interface & Abstraction - specifies WHAT method must exist (generateReport)
 * without dictating HOW the report is formatted or created.
 */
public interface Reportable {

    // Generates a summary report for a given customer ID
    String generateReport(String customerId) throws CustomerNotFoundException;

}
