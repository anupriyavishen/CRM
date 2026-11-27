package com.crm.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.crm.model.Customer;
import com.crm.model.FollowUp;

/**
 * PendingFollowUpService finds and sorts all uncompleted follow-ups across all customers.
 * OOP Concepts:
 * 1. Single Responsibility Principle: Focuses solely on finding pending follow-up reminders.
 * 2. Comparator Interface: Uses standard Java Collections.sort() with a Comparator to order by due date.
 */
public class PendingFollowUpService {

    private CustomerRepository repository;

    // Receives the shared repository
    public PendingFollowUpService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Scans all customers, collects uncompleted follow-ups, and returns them sorted by due date
    public List<FollowUp> getPendingFollowUps() {
        List<FollowUp> pendingList = new ArrayList<>();

        // Loop through all customers using a simple for-each loop
        for (Customer customer : repository.findAll()) {
            if (customer.getFollowUps() != null) {
                for (FollowUp followUp : customer.getFollowUps()) {
                    if (!followUp.isCompleted()) {
                        pendingList.add(followUp);
                    }
                }
            }
        }

        // Sort pending follow-ups in chronological order of due date
        Collections.sort(pendingList, new Comparator<FollowUp>() {
            @Override
            public int compare(FollowUp first, FollowUp second) {
                if (first.getDueDate() == null && second.getDueDate() == null) {
                    return 0;
                }
                if (first.getDueDate() == null) {
                    return 1;
                }
                if (second.getDueDate() == null) {
                    return -1;
                }
                return first.getDueDate().compareTo(second.getDueDate());
            }
        });

        return pendingList;
    }

}
