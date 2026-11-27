package com.crm.test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.crm.exception.CustomerNotFoundException;
import com.crm.exception.DuplicateCustomerException;
import com.crm.exception.InvalidDealException;
import com.crm.exception.InvalidInputException;
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
import com.crm.model.Interaction;
import com.crm.model.MeetingInteraction;
import com.crm.service.AddCustomerService;
import com.crm.service.AddDealService;
import com.crm.service.CompleteFollowUpService;
import com.crm.service.CustomerRepository;
import com.crm.service.DataSeedService;
import com.crm.service.DeleteCustomerService;
import com.crm.service.FilePersistenceService;
import com.crm.service.InteractionHistoryService;
import com.crm.service.LogInteractionService;
import com.crm.service.PendingFollowUpService;
import com.crm.service.ScheduleFollowUpService;
import com.crm.service.SearchCustomerService;
import com.crm.service.SummaryReportService;
import com.crm.service.TagCustomerService;
import com.crm.service.UpdateCustomerService;
import com.crm.service.UpdateDealStageService;
import com.crm.service.ViewCustomerService;
import com.crm.util.IdGenerator;
import com.crm.util.InputValidator;

/**
 * CRMVerificationTest is an automated test suite verifying every feature in the CRM.
 * It provides clear, labelled [PASS] / [FAIL] output suitable for non-technical examiners.
 */
public class CRMVerificationTest {

    private static int passedCount = 0;
    private static int failedCount = 0;

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("              CRM SYSTEM - VIVA VERIFICATION TEST SUITE               ");
        System.out.println("======================================================================\n");

        CustomerRepository repo = new CustomerRepository();
        AddCustomerService addService = new AddCustomerService(repo);
        ViewCustomerService viewService = new ViewCustomerService(repo);
        SearchCustomerService searchService = new SearchCustomerService(repo);
        UpdateCustomerService updateService = new UpdateCustomerService(repo);
        DeleteCustomerService deleteService = new DeleteCustomerService(repo);
        LogInteractionService logService = new LogInteractionService(repo);
        InteractionHistoryService historyService = new InteractionHistoryService(repo);
        TagCustomerService tagService = new TagCustomerService(repo);
        ScheduleFollowUpService scheduleService = new ScheduleFollowUpService(repo);
        PendingFollowUpService pendingService = new PendingFollowUpService(repo);
        CompleteFollowUpService completeService = new CompleteFollowUpService(repo);
        AddDealService addDealService = new AddDealService(repo);
        UpdateDealStageService updateDealService = new UpdateDealStageService(repo);
        SummaryReportService reportService = new SummaryReportService(repo);
        FilePersistenceService persistenceService = new FilePersistenceService(repo);
        DataSeedService seedService = new DataSeedService(repo);

        // Test 1: Add Individual Customer
        runTest(1, "Add Individual Customer", () -> {
            IndividualCustomer ind = new IndividualCustomer("C101", "Alice Smith", "alice@example.com", "9876543210", "123 Elm St", 28);
            addService.addCustomer(ind);
            check(repo.exists("C101"), "Customer C101 must exist in repository");
            check("Individual".equals(ind.getCustomerType()), "Customer type must be Individual");
        });

        // Test 2: Add Business Customer
        runTest(2, "Add Business Customer", () -> {
            BusinessCustomer bus = new BusinessCustomer("C102", "Bob Johnson", "bob@acme.com", "9123456780", "Acme Corp", "29ABCDE1234F1Z5", "Retail");
            addService.addCustomer(bus);
            check(repo.exists("C102"), "Customer C102 must exist in repository");
            check("Business".equals(bus.getCustomerType()), "Customer type must be Business");
        });

        // Test 3: Duplicate Customer & Email Check
        runTest(3, "Duplicate Prevention (ID and Email)", () -> {
            boolean caughtDuplicateId = false;
            try {
                addService.addCustomer(new IndividualCustomer("C101", "Clone", "other@example.com", "9000000000", "Addr", 25));
            } catch (DuplicateCustomerException e) {
                caughtDuplicateId = true;
            }
            check(caughtDuplicateId, "Should block duplicate customer ID C101");

            boolean caughtDuplicateEmail = false;
            try {
                addService.addCustomer(new IndividualCustomer("C103", "Other", "alice@example.com", "9000000000", "Addr", 25));
            } catch (DuplicateCustomerException e) {
                caughtDuplicateEmail = true;
            }
            check(caughtDuplicateEmail, "Should block duplicate customer email");
        });

        // Test 4: View Customer (Single & All)
        runTest(4, "View Customer (Single by ID & All)", () -> {
            Customer c = viewService.viewCustomer("C101");
            check("Alice Smith".equals(c.getName()), "Fetched customer name must match");
            List<Customer> all = viewService.viewCustomer();
            check(all.size() >= 2, "Must return all registered customers");
        });

        // Test 5: Search Customer by Keyword & Status
        runTest(5, "Search Customer (Keyword & Status Filter)", () -> {
            List<Customer> keywordResults = searchService.search("Alice");
            check(keywordResults.size() == 1, "Should find 1 customer for keyword 'Alice'");

            List<Customer> statusResults = searchService.search(CustomerStatus.LEAD);
            check(!statusResults.isEmpty(), "Should find customers with LEAD status");
        });

        // Test 6: Update Customer Details
        runTest(6, "Update Customer Details", () -> {
            updateService.updateCustomer("C101", "Alice Wonder", "alice.w@example.com", "9876500000");
            Customer updated = repo.findById("C101");
            check("Alice Wonder".equals(updated.getName()), "Name must be updated");
            check("alice.w@example.com".equals(updated.getEmail()), "Email must be updated");
            check("9876500000".equals(updated.getPhone()), "Phone must be updated");
        });

        // Test 7: Tag Customer Status and Category
        runTest(7, "Tag Customer Status and Category", () -> {
            tagService.setStatus("C101", CustomerStatus.ACTIVE);
            tagService.setCategory("C101", CustomerCategory.VIP);
            Customer c = repo.findById("C101");
            check(c.getStatus() == CustomerStatus.ACTIVE, "Status must be ACTIVE");
            check(c.getCategory() == CustomerCategory.VIP, "Category must be VIP");
        });

        // Test 8: Log Interactions (Call, Email, Meeting)
        runTest(8, "Log Interactions (Call, Email, Meeting)", () -> {
            logService.logInteraction("C101", new CallInteraction("I101", LocalDate.now(), "Intro call", 15, "9876500000"));
            logService.logInteraction("C101", new EmailInteraction("I102", LocalDate.now(), "Sent brochure", "Welcome", "alice.w@example.com"));
            logService.logInteraction("C101", new MeetingInteraction("I103", LocalDate.now(), "Project kickoff", "Office", new ArrayList<>(Arrays.asList("Alice", "Manager"))));

            List<Interaction> history = historyService.viewHistory("C101");
            check(history.size() == 3, "Customer C101 must have 3 logged interactions");
        });

        // Test 9: Add Deal & Pipeline Calculation
        runTest(9, "Add Deal & Validate Business Rules", () -> {
            Deal deal = new Deal("D101", "Website Redesign", 50000.0, LocalDate.now().plusDays(20));
            addDealService.addDeal("C101", deal);
            Customer c = repo.findById("C101");
            check(c.getDeals().size() == 1, "Customer C101 must have 1 deal");

            boolean blockedZeroAmount = false;
            try {
                addDealService.addDeal("C101", new Deal("D102", "Bad Deal", 0.0, LocalDate.now()));
            } catch (InvalidDealException e) {
                blockedZeroAmount = true;
            }
            check(blockedZeroAmount, "Must block deal with non-positive amount");
        });

        // Test 10: Update Deal Stage & Protect Closed Deals
        runTest(10, "Update Deal Stage & Closed Deal Protection", () -> {
            updateDealService.updateStage("C101", "D101", DealStage.WON);
            Customer c = repo.findById("C101");
            check(c.getDeals().get(0).getStage() == DealStage.WON, "Deal stage must be WON");

            boolean blockedClosedEdit = false;
            try {
                updateDealService.updateStage("C101", "D101", DealStage.NEGOTIATION);
            } catch (InvalidDealException e) {
                blockedClosedEdit = true;
            }
            check(blockedClosedEdit, "Must block stage changes on already WON deals");
        });

        // Test 11: Schedule & View Pending Follow-ups
        runTest(11, "Schedule & Sort Pending Follow-ups", () -> {
            scheduleService.scheduleFollowUp("C101", new FollowUp("F101", LocalDate.now().plusDays(5), "Call back about invoice"));
            scheduleService.scheduleFollowUp("C101", new FollowUp("F102", LocalDate.now().plusDays(2), "Send quick update"));

            List<FollowUp> pending = pendingService.getPendingFollowUps();
            check(pending.size() >= 2, "Must find scheduled pending follow-ups");
            // Check chronological order
            check(pending.get(0).getDueDate().isBefore(pending.get(1).getDueDate())
                    || pending.get(0).getDueDate().isEqual(pending.get(1).getDueDate()), "Pending follow-ups must be sorted chronologically");
        });

        // Test 12: Complete Follow-up
        runTest(12, "Complete Follow-up", () -> {
            int beforeCount = pendingService.getPendingFollowUps().size();
            completeService.completeFollowUp("C101", "F101");
            int afterCount = pendingService.getPendingFollowUps().size();
            check(afterCount == beforeCount - 1, "Pending follow-up count must decrease by 1");

            boolean blockedDoubleComplete = false;
            try {
                completeService.completeFollowUp("C101", "F101");
            } catch (InvalidInputException e) {
                blockedDoubleComplete = true;
            }
            check(blockedDoubleComplete, "Must block completing an already completed follow-up");
        });

        // Test 13: Summary Reports (Customer & Overall System)
        runTest(13, "Generate Customer & System Summary Reports", () -> {
            String singleReport = reportService.generateReport("C101");
            check(singleReport.contains("CUSTOMER SUMMARY REPORT"), "Report must include customer header");
            check(singleReport.contains("Alice Wonder"), "Report must include customer name");

            String overallReport = reportService.generateReport();
            check(overallReport.contains("OVERALL CRM SUMMARY REPORT"), "Overall report must include system header");
            check(overallReport.contains("Total Won Revenue"), "Overall report must include total revenue");
        });

        // Test 14: File Persistence (Save & Load with escaped newlines and '=')
        runTest(14, "File Persistence (Escapes '=', newlines, and round-trips)", () -> {
            // Add a special customer with newlines and equals in notes
            IndividualCustomer special = new IndividualCustomer("C199", "Special Customer", "special@test.com", "9998887776", "Line 1\nLine 2", 30);
            repo.save(special);
            special.addInteraction(new CallInteraction("I199", LocalDate.now(), "Equation a=b\nNext line notes", 10, "9998887776"));

            String testFile = "data/automated_test_data.txt";
            int saved = persistenceService.saveToFile(testFile);
            check(saved > 0, "Must save records to file");

            // Load into a fresh repository
            CustomerRepository freshRepo = new CustomerRepository();
            FilePersistenceService freshPersistence = new FilePersistenceService(freshRepo);
            int loaded = freshPersistence.loadFromFile(testFile);
            check(loaded == saved, "Loaded count must match saved count");

            Customer loadedSpecial = freshRepo.findById("C199");
            check(loadedSpecial != null, "Special customer must be restored");
            check(loadedSpecial.getInteractions().size() == 1, "Special customer interaction must be restored");
            check(loadedSpecial.getInteractions().get(0).getNotes().contains("Equation a=b\nNext line notes"), "Notes with '=' and newline must match exactly");

            // Clean up temporary test file
            new File(testFile).delete();
        });

        // Test 15: Delete Customer
        runTest(15, "Delete Customer", () -> {
            deleteService.deleteCustomer("C102");
            check(!repo.exists("C102"), "Customer C102 must no longer exist");

            boolean caughtMissing = false;
            try {
                deleteService.deleteCustomer("C999");
            } catch (CustomerNotFoundException e) {
                caughtMissing = true;
            }
            check(caughtMissing, "Must throw CustomerNotFoundException for missing customer");
        });

        System.out.println("\n======================================================================");
        if (failedCount == 0) {
            System.out.println("  SUMMARY: " + passedCount + " / " + (passedCount + failedCount) + " Tests Passed (0 Failed) - ALL FEATURES WORKING!");
        } else {
            System.out.println("  SUMMARY: " + passedCount + " Passed, " + failedCount + " Failed.");
        }
        System.out.println("======================================================================");

        if (failedCount > 0) {
            System.exit(1);
        }
    }

    // Helper runner that executes a test lambda and prints standard PASS/FAIL
    private static void runTest(int number, String featureName, TestAction action) {
        try {
            action.run();
            System.out.printf("  [PASS] Test %2d: %s\n", number, featureName);
            passedCount++;
        } catch (Throwable t) {
            System.out.printf("  [FAIL] Test %2d: %s -> %s\n", number, featureName, t.getMessage());
            failedCount++;
        }
    }

    // Helper assertion check
    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Assertion failed: " + message);
        }
    }

    // Functional interface for test blocks
    @FunctionalInterface
    interface TestAction {
        void run() throws Throwable;
    }

}
