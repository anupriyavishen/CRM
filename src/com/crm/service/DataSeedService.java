package com.crm.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.crm.model.BusinessCustomer;
import com.crm.model.CallInteraction;
import com.crm.model.Customer;
import com.crm.model.CustomerCategory;
import com.crm.model.CustomerStatus;
import com.crm.model.Deal;
import com.crm.model.DealStage;
import com.crm.model.EmailInteraction;
import com.crm.model.FollowUp;
import com.crm.model.IndividualCustomer;
import com.crm.model.MeetingInteraction;
import com.crm.util.IdGenerator;

/**
 * DataSeedService populates the CRM with realistic demonstration data.
 * OOP Concepts:
 * 1. Single Responsibility Principle: Separates sample test data generation from business logic.
 * 2. Polymorphism & Object Composition: Instantiates both IndividualCustomer and BusinessCustomer,
 *    attaching diverse Interaction types, deals, and follow-ups.
 */
public class DataSeedService {

    private CustomerRepository repository;

    // Receives the shared repository
    public DataSeedService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Loads 4 realistic customer records; optionally clears existing records first
    public int seedDemoData(boolean clearExisting) {
        if (clearExisting) {
            List<Customer> currentCustomers = repository.findAll();
            for (Customer customer : currentCustomers) {
                repository.remove(customer.getCustomerId());
            }
        }

        // 1. Individual Customer: Aarav Sharma
        IndividualCustomer customer1 = new IndividualCustomer(
                "C001",
                "Aarav Sharma",
                "aarav.sharma@example.com",
                "9876543210",
                "42 MG Road, Bengaluru",
                29
        );
        customer1.setStatus(CustomerStatus.ACTIVE);
        customer1.setCategory(CustomerCategory.VIP);

        customer1.addInteraction(new MeetingInteraction(
                "I001",
                LocalDate.now().minusDays(10),
                "Discussed enterprise product licensing requirements",
                "Bangalore Regional Office",
                new ArrayList<>(Arrays.asList("Aarav Sharma", "Tech Sales Lead"))
        ));
        customer1.addInteraction(new CallInteraction(
                "I002",
                LocalDate.now().minusDays(5),
                "Follow-up discussion on customized quote and deployment timeline",
                25,
                "9876543210"
        ));

        Deal deal1 = new Deal("D001", "Cloud Migration Contract", 150000.0, LocalDate.now().plusDays(15));
        deal1.setStage(DealStage.NEGOTIATION);
        customer1.addDeal(deal1);

        FollowUp followUp1 = new FollowUp("F001", LocalDate.now().plusDays(3), "Send finalized SLA contract and legal terms");
        FollowUp followUp2 = new FollowUp("F002", LocalDate.now().minusDays(2), "Initial discovery questionnaire review");
        followUp2.markCompleted();
        customer1.addFollowUp(followUp1);
        customer1.addFollowUp(followUp2);

        repository.save(customer1);

        // 2. Business Customer: Nexus Tech Solutions
        BusinessCustomer customer2 = new BusinessCustomer(
                "C002",
                "Priya Nair",
                "priya.nair@nexustech.com",
                "9123456780",
                "Nexus Tech Solutions Pvt Ltd",
                "29AABCN1234F1Z5",
                "Information Technology"
        );
        customer2.setStatus(CustomerStatus.PROSPECT);
        customer2.setCategory(CustomerCategory.REGULAR);

        customer2.addInteraction(new EmailInteraction(
                "I003",
                LocalDate.now().minusDays(8),
                "Sent introductory portfolio deck and case studies",
                "Enterprise Solutions Portfolio",
                "priya.nair@nexustech.com"
        ));
        customer2.addInteraction(new CallInteraction(
                "I004",
                LocalDate.now().minusDays(2),
                "Reviewed cloud infrastructure requirements and budget constraints",
                40,
                "9123456780"
        ));

        Deal deal2 = new Deal("D002", "AI Analytics Suite Implementation", 420000.0, LocalDate.now().plusDays(20));
        deal2.setStage(DealStage.NEGOTIATION);
        Deal deal3 = new Deal("D003", "Legacy Support Renewal", 75000.0, LocalDate.now().minusDays(15));
        deal3.setStage(DealStage.WON);
        customer2.addDeal(deal2);
        customer2.addDeal(deal3);

        FollowUp followUp3 = new FollowUp("F003", LocalDate.now().plusDays(5), "Schedule technical demo with CTO and engineering leads");
        customer2.addFollowUp(followUp3);

        repository.save(customer2);

        // 3. Individual Customer: Rohan Verma
        IndividualCustomer customer3 = new IndividualCustomer(
                "C003",
                "Rohan Verma",
                "rohan.verma@example.com",
                "9988776655",
                "15 Park Street, Kolkata",
                34
        );
        customer3.setStatus(CustomerStatus.LEAD);
        customer3.setCategory(CustomerCategory.NEW);

        customer3.addInteraction(new CallInteraction(
                "I005",
                LocalDate.now().minusDays(1),
                "Inbound inquiry regarding SaaS subscription plans",
                15,
                "9988776655"
        ));

        Deal deal4 = new Deal("D004", "Annual SaaS Pro Tier", 24000.0, LocalDate.now().plusDays(30));
        deal4.setStage(DealStage.NEW);
        customer3.addDeal(deal4);

        FollowUp followUp4 = new FollowUp("F004", LocalDate.now().plusDays(2), "Send subscription pricing tier comparison chart");
        customer3.addFollowUp(followUp4);

        repository.save(customer3);

        // 4. Business Customer: Apex Global Logistics
        BusinessCustomer customer4 = new BusinessCustomer(
                "C004",
                "Vikram Malhotra",
                "vikram@apexlogistics.in",
                "9811223344",
                "Apex Global Logistics Ltd",
                "27AAACA9876E1Z2",
                "Supply Chain & Logistics"
        );
        customer4.setStatus(CustomerStatus.ACTIVE);
        customer4.setCategory(CustomerCategory.VIP);

        customer4.addInteraction(new MeetingInteraction(
                "I006",
                LocalDate.now().minusDays(20),
                "Quarterly account review and supply chain analytics sync",
                "Mumbai HQ Boardroom",
                new ArrayList<>(Arrays.asList("Vikram Malhotra", "Operations VP", "Account Director"))
        ));
        customer4.addInteraction(new EmailInteraction(
                "I007",
                LocalDate.now().minusDays(12),
                "Sent customized fleet tracking API documentation",
                "API Specs & Integration Guide",
                "vikram@apexlogistics.in"
        ));

        Deal deal5 = new Deal("D005", "Fleet Management ERP Integration", 850000.0, LocalDate.now().minusDays(5));
        deal5.setStage(DealStage.WON);
        Deal deal6 = new Deal("D006", "IoT Sensor Expansion Project", 310000.0, LocalDate.now().plusDays(45));
        deal6.setStage(DealStage.NEGOTIATION);
        customer4.addDeal(deal5);
        customer4.addDeal(deal6);

        FollowUp followUp5 = new FollowUp("F005", LocalDate.now().plusDays(10), "Conduct post-deployment customer satisfaction interview");
        customer4.addFollowUp(followUp5);

        repository.save(customer4);

        // Keep ID generator counters synchronized with the highest IDs in the seeded data
        IdGenerator.syncCustomerId("C004");
        IdGenerator.syncInteractionId("I007");
        IdGenerator.syncDealId("D006");
        IdGenerator.syncFollowUpId("F005");

        return 4;
    }

}
