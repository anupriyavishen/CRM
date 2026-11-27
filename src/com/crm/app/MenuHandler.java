package com.crm.app;

import java.time.LocalDate;
import java.util.ArrayList;
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
import com.crm.util.ConsoleHelper;
import com.crm.util.IdGenerator;
import com.crm.util.InputValidator;

/**
 * MenuHandler coordinates the console user interface and delegates user actions to feature services.
 * OOP Concepts:
 * 1. Single Responsibility Principle: Orchestrates menus and user I/O flows; does NOT store or process business data itself.
 * 2. Dependency Injection: Receives CustomerRepository and ConsoleHelper through constructor.
 * 3. Service Delegation: Delegates all business tasks to decoupled, single-feature service classes.
 */
public class MenuHandler {

    private CustomerRepository repository;
    private ConsoleHelper console;

    // Decoupled feature services
    private AddCustomerService addCustomerService;
    private ViewCustomerService viewCustomerService;
    private SearchCustomerService searchCustomerService;
    private UpdateCustomerService updateCustomerService;
    private DeleteCustomerService deleteCustomerService;
    private LogInteractionService logInteractionService;
    private InteractionHistoryService interactionHistoryService;
    private TagCustomerService tagCustomerService;
    private ScheduleFollowUpService scheduleFollowUpService;
    private PendingFollowUpService pendingFollowUpService;
    private CompleteFollowUpService completeFollowUpService;
    private AddDealService addDealService;
    private UpdateDealStageService updateDealStageService;
    private SummaryReportService summaryReportService;
    private DataSeedService dataSeedService;
    private FilePersistenceService filePersistenceService;

    // Initializes MenuHandler and instantiates each feature service once
    public MenuHandler(CustomerRepository repository, ConsoleHelper console) {
        this.repository = repository;
        this.console = console;

        this.addCustomerService = new AddCustomerService(repository);
        this.viewCustomerService = new ViewCustomerService(repository);
        this.searchCustomerService = new SearchCustomerService(repository);
        this.updateCustomerService = new UpdateCustomerService(repository);
        this.deleteCustomerService = new DeleteCustomerService(repository);
        this.logInteractionService = new LogInteractionService(repository);
        this.interactionHistoryService = new InteractionHistoryService(repository);
        this.tagCustomerService = new TagCustomerService(repository);
        this.scheduleFollowUpService = new ScheduleFollowUpService(repository);
        this.pendingFollowUpService = new PendingFollowUpService(repository);
        this.completeFollowUpService = new CompleteFollowUpService(repository);
        this.addDealService = new AddDealService(repository);
        this.updateDealStageService = new UpdateDealStageService(repository);
        this.summaryReportService = new SummaryReportService(repository);
        this.dataSeedService = new DataSeedService(repository);
        this.filePersistenceService = new FilePersistenceService(repository);
    }

    // Displays the main menu loop until the user selects option 0 (Exit)
    public void showMainMenu() {
        boolean running = true;
        while (running) {
            console.printLine("\n======================== CRM SYSTEM ========================");
            console.printLine("  1. Add Customer                 9. Schedule Follow-up");
            console.printLine("  2. View Customer(s)            10. View Pending Follow-ups");
            console.printLine("  3. Search Customer             11. Complete Follow-up");
            console.printLine("  4. Update Customer             12. Add Deal");
            console.printLine("  5. Delete Customer             13. Update Deal Stage");
            console.printLine("  6. Log Interaction             14. Summary Report");
            console.printLine("  7. View Interaction History    15. Save Data to File");
            console.printLine("  8. Tag Status / Category       16. Load Data from File");
            console.printLine("                                 17. Load Demo Seed Data");
            console.printLine("                                  0. Exit");
            console.printLine("============================================================");

            int choice = console.readInt("Enter your choice (0-17): ");
            if (choice == 0) {
                // If customers exist, offer a friendly auto-save option
                if (repository.count() > 0) {
                    String ans = console.readString("Auto-save current data before exiting? (y/n): ");
                    if ("y".equalsIgnoreCase(ans) || "yes".equalsIgnoreCase(ans)) {
                        try {
                            filePersistenceService.saveToFile(FilePersistenceService.DEFAULT_FILE_PATH);
                            console.printLine("Data auto-saved to " + FilePersistenceService.DEFAULT_FILE_PATH);
                        } catch (Exception e) {
                            console.printLine("Warning: Could not auto-save data: " + e.getMessage());
                        }
                    }
                }
                console.printLine("Exiting CRM System. Thank you for using the system!");
                running = false;
            } else {
                handleChoice(choice);
            }
        }
    }

    // Routes the user's choice to the corresponding feature flow method
    public void handleChoice(int choice) {
        switch (choice) {
            case 1:
                addCustomerFlow();
                break;
            case 2:
                viewCustomerFlow();
                break;
            case 3:
                searchCustomerFlow();
                break;
            case 4:
                updateCustomerFlow();
                break;
            case 5:
                deleteCustomerFlow();
                break;
            case 6:
                logInteractionFlow();
                break;
            case 7:
                interactionHistoryFlow();
                break;
            case 8:
                tagCustomerFlow();
                break;
            case 9:
                scheduleFollowUpFlow();
                break;
            case 10:
                pendingFollowUpFlow();
                break;
            case 11:
                completeFollowUpFlow();
                break;
            case 12:
                addDealFlow();
                break;
            case 13:
                updateDealStageFlow();
                break;
            case 14:
                summaryReportFlow();
                break;
            case 15:
                saveDataFlow();
                break;
            case 16:
                loadDataFlow();
                break;
            case 17:
                seedDataFlow();
                break;
            default:
                console.printLine("Invalid choice. Please enter a number between 0 and 17.");
                break;
        }
    }

    // Handles the step-by-step registration of an Individual or Business customer
    private void addCustomerFlow() {
        console.printLine("\n--- Add Customer ---");
        console.printLine("Select Customer Type:");
        console.printLine("1. Individual Customer");
        console.printLine("2. Business / Corporate Customer");
        int typeChoice = console.readInt("Enter choice (1-2): ");

        if (typeChoice != 1 && typeChoice != 2) {
            console.printLine("Invalid choice. Please choose 1 for Individual or 2 for Business.");
            return;
        }

        String name = console.readString("Enter Customer Name: ");
        String email = console.readString("Enter email address (for example, user@example.com): ");
        String phone = console.readString("Enter phone number (10 digits): ");
        String customerId = IdGenerator.nextCustomerId();

        Customer customer = null;
        if (typeChoice == 1) {
            String address = console.readString("Enter Address: ");
            int age = console.readInt("Enter Age: ");
            customer = new IndividualCustomer(customerId, name, email, phone, address, age);
        } else {
            String companyName = console.readString("Enter Company Name: ");
            String gstNumber = console.readString("Enter GST Number: ");
            String industry = console.readString("Enter Industry Sector: ");
            customer = new BusinessCustomer(customerId, name, email, phone, companyName, gstNumber, industry);
        }

        try {
            addCustomerService.addCustomer(customer);
            console.printLine("Success! Customer registered with ID: " + customerId);
        } catch (DuplicateCustomerException | InvalidInputException e) {
            console.printLine("Error: " + e.getMessage());
        } catch (Exception e) {
            console.printLine("Unexpected error: " + e.getMessage());
        }
    }

    // Displays a single customer by ID or lists all registered customers
    private void viewCustomerFlow() {
        console.printLine("\n--- View Customer(s) ---");
        console.printLine("1. View Single Customer by ID");
        console.printLine("2. View All Customers");
        int choice = console.readInt("Enter choice (1-2): ");

        if (choice == 1) {
            String id = console.readString("Enter Customer ID (for example, C001): ");
            try {
                Customer c = viewCustomerService.viewCustomer(id);
                console.printLine("\n" + c.getDisplayDetails());
            } catch (CustomerNotFoundException e) {
                console.printLine("Error: " + e.getMessage());
            }
            console.pressEnterToContinue();
        } else if (choice == 2) {
            List<Customer> all = viewCustomerService.viewCustomer();
            if (all.isEmpty()) {
                console.printLine("No customers found. (Tip: Use option 16 to load saved file or option 17 for demo data)");
            } else {
                console.printLine("\n--- All Registered Customers (" + all.size() + ") ---");
                for (Customer c : all) {
                    console.printLine(c.toString());
                }
            }
            console.pressEnterToContinue();
        } else {
            console.printLine("Invalid choice. Please choose 1 or 2.");
        }
    }

    // Searches customers by keyword (name, email, phone) or filters by lifecycle status
    private void searchCustomerFlow() {
        console.printLine("\n--- Search Customer ---");
        console.printLine("1. Search by Keyword (Name, Email, Phone)");
        console.printLine("2. Filter by Status (LEAD, PROSPECT, ACTIVE, INACTIVE)");
        int choice = console.readInt("Enter choice (1-2): ");

        if (choice == 1) {
            String keyword = console.readString("Enter search keyword: ");
            List<Customer> results = searchCustomerService.search(keyword);
            if (results.isEmpty()) {
                console.printLine("No matching customers found for keyword: " + keyword);
            } else {
                console.printLine("\nFound " + results.size() + " matching customer(s):");
                for (Customer c : results) {
                    console.printLine(c.toString());
                }
            }
            console.pressEnterToContinue();
        } else if (choice == 2) {
            CustomerStatus[] statuses = CustomerStatus.values();
            console.printLine("Select Status to filter by:");
            for (int i = 0; i < statuses.length; i++) {
                console.printLine((i + 1) + ". " + statuses[i]);
            }
            int statusChoice = console.readInt("Enter choice (1-" + statuses.length + "): ");
            if (statusChoice < 1 || statusChoice > statuses.length) {
                console.printLine("Invalid status choice. Check the list and try again.");
                return;
            }
            CustomerStatus selectedStatus = statuses[statusChoice - 1];
            List<Customer> results = searchCustomerService.search(selectedStatus);
            if (results.isEmpty()) {
                console.printLine("No customers found with status: " + selectedStatus);
            } else {
                console.printLine("\nFound " + results.size() + " customer(s) with status " + selectedStatus + ":");
                for (Customer c : results) {
                    console.printLine(c.toString());
                }
            }
            console.pressEnterToContinue();
        } else {
            console.printLine("Invalid choice. Please choose 1 or 2.");
        }
    }

    // Updates name, email, or phone for an existing customer
    private void updateCustomerFlow() {
        console.printLine("\n--- Update Customer ---");
        String id = console.readString("Enter Customer ID to update (for example, C001): ");
        console.printLine("Note: Press Enter without entering text to keep the existing value unchanged.");
        String name = console.readString("Enter New Name: ");
        String email = console.readString("Enter New Email: ");
        String phone = console.readString("Enter New Phone: ");

        try {
            updateCustomerService.updateCustomer(id, name, email, phone);
            console.printLine("Success! Customer " + id + " updated successfully.");
        } catch (CustomerNotFoundException | InvalidInputException e) {
            console.printLine("Error: " + e.getMessage());
        } catch (Exception e) {
            console.printLine("Unexpected error: " + e.getMessage());
        }
    }

    // Removes a customer record after confirming ID
    private void deleteCustomerFlow() {
        console.printLine("\n--- Delete Customer ---");
        String id = console.readString("Enter Customer ID to delete (for example, C001): ");
        try {
            deleteCustomerService.deleteCustomer(id);
            console.printLine("Success! Customer " + id + " deleted successfully.");
        } catch (CustomerNotFoundException e) {
            console.printLine("Error: " + e.getMessage());
        } catch (Exception e) {
            console.printLine("Unexpected error: " + e.getMessage());
        }
    }

    // Records a phone call, email, or meeting interaction for a customer
    private void logInteractionFlow() {
        console.printLine("\n--- Log Interaction ---");
        String id = console.readString("Enter Customer ID (for example, C001): ");

        console.printLine("Select Interaction Type:");
        console.printLine("1. Phone Call");
        console.printLine("2. Email Message");
        console.printLine("3. In-Person / Online Meeting");
        int typeChoice = console.readInt("Enter choice (1-3): ");

        if (typeChoice < 1 || typeChoice > 3) {
            console.printLine("Invalid interaction type choice. Please select 1, 2, or 3.");
            return;
        }

        String dateInput = console.readString("Enter Date (yyyy-MM-dd, or press Enter for today): ");
        LocalDate interactionDate = LocalDate.now();
        if (InputValidator.isNotEmpty(dateInput)) {
            if (InputValidator.isValidDate(dateInput)) {
                interactionDate = LocalDate.parse(dateInput.trim());
            } else {
                console.printLine("Warning: Invalid date format. Defaulting to today's date (" + interactionDate + ").");
            }
        }

        String notes = console.readString("Enter Discussion Notes: ");
        String interactionId = IdGenerator.nextInteractionId();

        Interaction interaction = null;
        if (typeChoice == 1) {
            int duration = console.readInt("Enter Call Duration (minutes): ");
            String phone = console.readString("Enter Phone Number (10 digits): ");
            interaction = new CallInteraction(interactionId, interactionDate, notes, duration, phone);
        } else if (typeChoice == 2) {
            String subject = console.readString("Enter Email Subject: ");
            String emailAddr = console.readString("Enter Recipient Email Address: ");
            interaction = new EmailInteraction(interactionId, interactionDate, notes, subject, emailAddr);
        } else if (typeChoice == 3) {
            String location = console.readString("Enter Meeting Location: ");
            String attendeesStr = console.readString("Enter Attendees (separated by commas): ");
            List<String> attendeesList = new ArrayList<>();
            if (attendeesStr != null && !attendeesStr.trim().isEmpty()) {
                String[] parts = attendeesStr.split(",");
                for (String part : parts) {
                    if (!part.trim().isEmpty()) {
                        attendeesList.add(part.trim());
                    }
                }
            }
            interaction = new MeetingInteraction(interactionId, interactionDate, notes, location, attendeesList);
        }

        try {
            logInteractionService.logInteraction(id, interaction);
            console.printLine("Success! Interaction logged with ID: " + interactionId);
        } catch (CustomerNotFoundException e) {
            console.printLine("Error: " + e.getMessage());
        } catch (Exception e) {
            console.printLine("Unexpected error: " + e.getMessage());
        }
    }

    // Displays the complete communication history for a specific customer
    private void interactionHistoryFlow() {
        console.printLine("\n--- View Interaction History ---");
        String id = console.readString("Enter Customer ID (for example, C001): ");

        try {
            List<Interaction> history = interactionHistoryService.viewHistory(id);
            if (history.isEmpty()) {
                console.printLine("No interactions recorded yet for customer " + id);
            } else {
                console.printLine("\n--- Interaction History for Customer " + id + " (" + history.size() + ") ---");
                for (Interaction inter : history) {
                    console.printLine(inter.toString());
                }
            }
        } catch (CustomerNotFoundException e) {
            console.printLine("Error: " + e.getMessage());
        }
        console.pressEnterToContinue();
    }

    // Updates the customer lifecycle status or business category tier
    private void tagCustomerFlow() {
        console.printLine("\n--- Tag Customer Status / Category ---");
        String id = console.readString("Enter Customer ID (for example, C001): ");

        console.printLine("1. Update Lifecycle Status");
        console.printLine("2. Update Category Tier");
        int choice = console.readInt("Enter choice (1-2): ");

        if (choice == 1) {
            CustomerStatus[] statuses = CustomerStatus.values();
            console.printLine("Select New Status:");
            for (int i = 0; i < statuses.length; i++) {
                console.printLine((i + 1) + ". " + statuses[i]);
            }
            int statusChoice = console.readInt("Enter choice (1-" + statuses.length + "): ");
            if (statusChoice < 1 || statusChoice > statuses.length) {
                console.printLine("Invalid status choice.");
                return;
            }
            CustomerStatus selectedStatus = statuses[statusChoice - 1];
            try {
                tagCustomerService.setStatus(id, selectedStatus);
                console.printLine("Success! Customer status updated to " + selectedStatus);
            } catch (CustomerNotFoundException e) {
                console.printLine("Error: " + e.getMessage());
            }
        } else if (choice == 2) {
            CustomerCategory[] categories = CustomerCategory.values();
            console.printLine("Select New Category:");
            for (int i = 0; i < categories.length; i++) {
                console.printLine((i + 1) + ". " + categories[i]);
            }
            int catChoice = console.readInt("Enter choice (1-" + categories.length + "): ");
            if (catChoice < 1 || catChoice > categories.length) {
                console.printLine("Invalid category choice.");
                return;
            }
            CustomerCategory selectedCategory = categories[catChoice - 1];
            try {
                tagCustomerService.setCategory(id, selectedCategory);
                console.printLine("Success! Customer category updated to " + selectedCategory);
            } catch (CustomerNotFoundException e) {
                console.printLine("Error: " + e.getMessage());
            }
        } else {
            console.printLine("Invalid choice. Please choose 1 or 2.");
        }
    }

    // Schedules a new follow-up reminder with a future due date
    private void scheduleFollowUpFlow() {
        console.printLine("\n--- Schedule Follow-Up ---");
        String id = console.readString("Enter Customer ID (for example, C001): ");
        LocalDate dueDate = console.readDate("Enter Due Date (yyyy-MM-dd): ");
        String description = console.readString("Enter Follow-Up Description: ");
        String followUpId = IdGenerator.nextFollowUpId();

        FollowUp followUp = new FollowUp(followUpId, dueDate, description);

        try {
            scheduleFollowUpService.scheduleFollowUp(id, followUp);
            console.printLine("Success! Follow-up scheduled with ID: " + followUpId);
        } catch (CustomerNotFoundException | InvalidInputException e) {
            console.printLine("Error: " + e.getMessage());
        } catch (Exception e) {
            console.printLine("Unexpected error: " + e.getMessage());
        }
    }

    // Lists all incomplete follow-ups across all customers, sorted by due date
    private void pendingFollowUpFlow() {
        console.printLine("\n--- View Pending Follow-ups ---");
        List<FollowUp> pending = pendingFollowUpService.getPendingFollowUps();
        if (pending.isEmpty()) {
            console.printLine("No pending follow-ups found.");
        } else {
            console.printLine("Found " + pending.size() + " pending follow-up(s) sorted by date:");
            for (FollowUp f : pending) {
                console.printLine(f.toString());
            }
        }
        console.pressEnterToContinue();
    }

    // Marks an active follow-up as completed
    private void completeFollowUpFlow() {
        console.printLine("\n--- Complete Follow-Up ---");
        String id = console.readString("Enter Customer ID (for example, C001): ");
        String followUpId = console.readString("Enter Follow-Up ID to mark completed (for example, F001): ");
        try {
            completeFollowUpService.completeFollowUp(id, followUpId);
            console.printLine("Success! Follow-up " + followUpId + " marked as completed for customer " + id + ".");
        } catch (CustomerNotFoundException | InvalidInputException e) {
            console.printLine("Error: " + e.getMessage());
        } catch (Exception e) {
            console.printLine("Unexpected error: " + e.getMessage());
        }
    }

    // Creates a new business deal with an expected close date
    private void addDealFlow() {
        console.printLine("\n--- Add Deal ---");
        String id = console.readString("Enter Customer ID (for example, C001): ");
        String title = console.readString("Enter Deal Title: ");
        double amount = console.readDouble("Enter Deal Amount (in Rs.): ");
        LocalDate closeDate = console.readDate("Enter Expected Close Date (yyyy-MM-dd): ");
        String dealId = IdGenerator.nextDealId();

        Deal deal = new Deal(dealId, title, amount, closeDate);

        try {
            addDealService.addDeal(id, deal);
            console.printLine("Success! Deal created with ID: " + dealId);
        } catch (CustomerNotFoundException | InvalidDealException e) {
            console.printLine("Error: " + e.getMessage());
        } catch (Exception e) {
            console.printLine("Unexpected error: " + e.getMessage());
        }
    }

    // Updates the sales stage of an existing deal (cannot edit closed deals)
    private void updateDealStageFlow() {
        console.printLine("\n--- Update Deal Stage ---");
        String id = console.readString("Enter Customer ID (for example, C001): ");
        String dealId = console.readString("Enter Deal ID (for example, D001): ");

        DealStage[] stages = DealStage.values();
        console.printLine("Select New Stage:");
        for (int i = 0; i < stages.length; i++) {
            console.printLine((i + 1) + ". " + stages[i]);
        }
        int stageChoice = console.readInt("Enter choice (1-" + stages.length + "): ");

        if (stageChoice < 1 || stageChoice > stages.length) {
            console.printLine("Invalid stage choice.");
            return;
        }

        DealStage stage = stages[stageChoice - 1];

        try {
            updateDealStageService.updateStage(id, dealId, stage);
            console.printLine("Success! Deal " + dealId + " stage updated to " + stage);
        } catch (CustomerNotFoundException | InvalidDealException e) {
            console.printLine("Error: " + e.getMessage());
        } catch (Exception e) {
            console.printLine("Unexpected error: " + e.getMessage());
        }
    }

    // Generates customer-specific or overall CRM summary reports
    private void summaryReportFlow() {
        console.printLine("\n--- Summary Report ---");
        console.printLine("1. Customer Summary Report (Detailed view of one customer)");
        console.printLine("2. Overall Summary Report (System-wide pipeline & stats)");
        int choice = console.readInt("Enter choice (1-2): ");

        if (choice == 1) {
            String id = console.readString("Enter Customer ID (for example, C001): ");
            try {
                String report = summaryReportService.generateReport(id);
                console.printLine("\n" + report);
            } catch (CustomerNotFoundException e) {
                console.printLine("Error: " + e.getMessage());
            }
            console.pressEnterToContinue();
        } else if (choice == 2) {
            String report = summaryReportService.generateReport();
            console.printLine("\n" + report);
            console.pressEnterToContinue();
        } else {
            console.printLine("Invalid choice. Please choose 1 or 2.");
        }
    }

    // Saves the complete CRM database to a text file
    private void saveDataFlow() {
        console.printLine("\n--- Save Data to File ---");
        String path = console.readString("Enter file path (or press Enter for default '" + FilePersistenceService.DEFAULT_FILE_PATH + "'): ");
        if (path.isEmpty()) {
            path = FilePersistenceService.DEFAULT_FILE_PATH;
        }
        try {
            int count = filePersistenceService.saveToFile(path);
            console.printLine("Success! Saved " + count + " customer(s) and their records to " + path);
        } catch (Exception e) {
            console.printLine("Error saving data: " + e.getMessage());
        }
    }

    // Loads the CRM database from a saved text file
    private void loadDataFlow() {
        console.printLine("\n--- Load Data from File ---");
        String path = console.readString("Enter file path (or press Enter for default '" + FilePersistenceService.DEFAULT_FILE_PATH + "'): ");
        if (path.isEmpty()) {
            path = FilePersistenceService.DEFAULT_FILE_PATH;
        }
        try {
            int count = filePersistenceService.loadFromFile(path);
            console.printLine("Success! Loaded " + count + " customer(s) from " + path);
        } catch (Exception e) {
            console.printLine("Error loading data: " + e.getMessage());
        }
    }

    // Populates the database with sample demonstration records
    private void seedDataFlow() {
        console.printLine("\n--- Load Demo Seed Data ---");
        console.printLine("1. Add demo data (keep existing customers)");
        console.printLine("2. Reset repository and load fresh demo data");
        int choice = console.readInt("Enter choice (1-2): ");
        boolean clearExisting = (choice == 2);
        int count = dataSeedService.seedDemoData(clearExisting);
        console.printLine("Success! " + count + " demo customers loaded with full interaction, deal, and follow-up records.");
    }

}
