package com.crm.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.crm.model.InteractionType;
import com.crm.model.MeetingInteraction;
import com.crm.util.IdGenerator;

/**
 * FilePersistenceService saves and loads the complete CRM database to and from a local text file.
 * OOP Concepts:
 * 1. File I/O: Uses standard Java FileReader, FileWriter, BufferedReader, and BufferedWriter.
 * 2. Encapsulation: Hides serialization / deserialization details from the rest of the application.
 * 3. Exception Handling: Propagates checked IOExceptions so callers can inform the user gracefully.
 * Viva Tip: We escape newlines (\n) and equals signs (=) to make the text storage format safe and robust.
 */
public class FilePersistenceService {

    // Default file location used when the user presses Enter
    public static final String DEFAULT_FILE_PATH = "data/crm_data.txt";
    private CustomerRepository repository;

    // Receives the shared repository
    public FilePersistenceService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Helper: Escapes special characters (\, \n, =) so values do not corrupt the key=value format
    private static String escape(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\\", "\\\\")
                   .replace("\n", "\\n")
                   .replace("\r", "")
                   .replace("=", "\\=");
    }

    // Helper: Restores original characters from their escape sequences
    private static String unescape(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\\=", "=")
                   .replace("\\n", "\n")
                   .replace("\\\\", "\\");
    }

    // Saves all customers, interactions, deals, and follow-ups to a structured text file
    public int saveToFile(String filePath) throws IOException {
        String targetPath = (filePath != null && !filePath.trim().isEmpty()) ? filePath.trim() : DEFAULT_FILE_PATH;
        File file = new File(targetPath);

        // Ensure parent directories exist
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        List<Customer> customerList = repository.findAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("# CRM Data File - Core Java CRM Storage\n");
            writer.write("# Generated: " + LocalDate.now() + "\n\n");

            for (Customer customer : customerList) {
                writer.write("[CUSTOMER]\n");
                writer.write("type=" + customer.getCustomerType().toUpperCase() + "\n");
                writer.write("id=" + customer.getCustomerId() + "\n");
                writer.write("name=" + escape(customer.getName()) + "\n");
                writer.write("email=" + escape(customer.getEmail()) + "\n");
                writer.write("phone=" + escape(customer.getPhone()) + "\n");
                writer.write("status=" + customer.getStatus().name() + "\n");
                writer.write("category=" + customer.getCategory().name() + "\n");

                if (customer instanceof IndividualCustomer) {
                    IndividualCustomer ind = (IndividualCustomer) customer;
                    writer.write("address=" + escape(ind.getAddress()) + "\n");
                    writer.write("age=" + ind.getAge() + "\n");
                } else if (customer instanceof BusinessCustomer) {
                    BusinessCustomer bus = (BusinessCustomer) customer;
                    writer.write("companyName=" + escape(bus.getCompanyName()) + "\n");
                    writer.write("gstNumber=" + escape(bus.getGstNumber()) + "\n");
                    writer.write("industry=" + escape(bus.getIndustry()) + "\n");
                }
                writer.write("\n");

                // Save Interactions
                if (customer.getInteractions() != null) {
                    for (Interaction inter : customer.getInteractions()) {
                        writer.write("[INTERACTION]\n");
                        writer.write("type=" + inter.getType().name() + "\n");
                        writer.write("id=" + inter.getInteractionId() + "\n");
                        writer.write("date=" + inter.getDate() + "\n");
                        writer.write("notes=" + escape(inter.getNotes()) + "\n");

                        if (inter instanceof CallInteraction) {
                            CallInteraction call = (CallInteraction) inter;
                            writer.write("duration=" + call.getDurationMinutes() + "\n");
                            writer.write("callPhone=" + escape(call.getPhoneNumber()) + "\n");
                        } else if (inter instanceof EmailInteraction) {
                            EmailInteraction email = (EmailInteraction) inter;
                            writer.write("subject=" + escape(email.getSubject()) + "\n");
                            writer.write("recipientEmail=" + escape(email.getEmailAddress()) + "\n");
                        } else if (inter instanceof MeetingInteraction) {
                            MeetingInteraction meet = (MeetingInteraction) inter;
                            writer.write("location=" + escape(meet.getLocation()) + "\n");
                            String attendees = (meet.getAttendees() != null) ? String.join(",", meet.getAttendees()) : "";
                            writer.write("attendees=" + escape(attendees) + "\n");
                        }
                        writer.write("\n");
                    }
                }

                // Save Deals
                if (customer.getDeals() != null) {
                    for (Deal deal : customer.getDeals()) {
                        writer.write("[DEAL]\n");
                        writer.write("id=" + deal.getDealId() + "\n");
                        writer.write("title=" + escape(deal.getTitle()) + "\n");
                        writer.write("amount=" + deal.getAmount() + "\n");
                        writer.write("stage=" + deal.getStage().name() + "\n");
                        writer.write("closeDate=" + deal.getCloseDate() + "\n");
                        writer.write("\n");
                    }
                }

                // Save Follow-ups
                if (customer.getFollowUps() != null) {
                    for (FollowUp followUp : customer.getFollowUps()) {
                        writer.write("[FOLLOWUP]\n");
                        writer.write("id=" + followUp.getFollowUpId() + "\n");
                        writer.write("dueDate=" + followUp.getDueDate() + "\n");
                        writer.write("description=" + escape(followUp.getDescription()) + "\n");
                        writer.write("completed=" + followUp.isCompleted() + "\n");
                        writer.write("\n");
                    }
                }
            }
        }

        return customerList.size();
    }

    // Reads the file line by line, reconstructs domain objects, and populates the repository
    public int loadFromFile(String filePath) throws IOException {
        String targetPath = (filePath != null && !filePath.trim().isEmpty()) ? filePath.trim() : DEFAULT_FILE_PATH;
        File file = new File(targetPath);
        if (!file.exists()) {
            throw new IOException("Data file not found: " + targetPath);
        }

        // Clear existing repository records before loading
        List<Customer> current = repository.findAll();
        for (Customer c : current) {
            repository.remove(c.getCustomerId());
        }

        Customer currentCustomer = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String currentSection = null;
            Map<String, String> sectionData = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Skip empty lines and comment lines
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                if (line.startsWith("[") && line.endsWith("]")) {
                    // Flush the previous section before starting a new one
                    if (currentSection != null) {
                        currentCustomer = processSection(currentSection, sectionData, currentCustomer);
                    }
                    currentSection = line.substring(1, line.length() - 1);
                    sectionData.clear();
                } else if (line.contains("=")) {
                    int eqIndex = line.indexOf('=');
                    String key = line.substring(0, eqIndex).trim();
                    String rawValue = line.substring(eqIndex + 1).trim();
                    sectionData.put(key, unescape(rawValue));
                }
            }

            // Flush the very last section in the file
            if (currentSection != null) {
                processSection(currentSection, sectionData, currentCustomer);
            }
        }

        return repository.findAll().size();
    }

    // Rebuilds and attaches each domain object (Customer, Interaction, Deal, FollowUp)
    private Customer processSection(String section, Map<String, String> data, Customer currentCustomer) {
        if ("CUSTOMER".equalsIgnoreCase(section)) {
            String type = data.getOrDefault("type", "INDIVIDUAL");
            String id = data.get("id");
            String name = data.get("name");
            String email = data.get("email");
            String phone = data.get("phone");

            Customer customer;
            if ("BUSINESS".equalsIgnoreCase(type)) {
                String company = data.get("companyName");
                String gst = data.get("gstNumber");
                String industry = data.get("industry");
                customer = new BusinessCustomer(id, name, email, phone, company, gst, industry);
            } else {
                String address = data.get("address");
                int age = 0;
                try {
                    age = Integer.parseInt(data.getOrDefault("age", "0"));
                } catch (NumberFormatException ignored) {}
                customer = new IndividualCustomer(id, name, email, phone, address, age);
            }

            String statusStr = data.get("status");
            if (statusStr != null) {
                try {
                    customer.setStatus(CustomerStatus.valueOf(statusStr));
                } catch (IllegalArgumentException ignored) {}
            }

            String categoryStr = data.get("category");
            if (categoryStr != null) {
                try {
                    customer.setCategory(CustomerCategory.valueOf(categoryStr));
                } catch (IllegalArgumentException ignored) {}
            }

            repository.save(customer);
            IdGenerator.syncCustomerId(id);
            return customer;

        } else if ("INTERACTION".equalsIgnoreCase(section) && currentCustomer != null) {
            String typeStr = data.getOrDefault("type", "CALL");
            String id = data.get("id");
            String dateStr = data.get("date");
            LocalDate date = (dateStr != null && !dateStr.isEmpty()) ? LocalDate.parse(dateStr) : LocalDate.now();
            String notes = data.getOrDefault("notes", "");

            Interaction interaction = null;
            InteractionType type = InteractionType.valueOf(typeStr);

            if (type == InteractionType.CALL) {
                int duration = 0;
                try {
                    duration = Integer.parseInt(data.getOrDefault("duration", "0"));
                } catch (NumberFormatException ignored) {}
                String phone = data.get("callPhone");
                interaction = new CallInteraction(id, date, notes, duration, phone);

            } else if (type == InteractionType.EMAIL) {
                String subject = data.get("subject");
                String recipient = data.get("recipientEmail");
                interaction = new EmailInteraction(id, date, notes, subject, recipient);

            } else if (type == InteractionType.MEETING) {
                String location = data.get("location");
                String attendeesStr = data.get("attendees");
                List<String> attendees = new ArrayList<>();
                if (attendeesStr != null && !attendeesStr.isEmpty()) {
                    attendees.addAll(Arrays.asList(attendeesStr.split(",")));
                }
                interaction = new MeetingInteraction(id, date, notes, location, attendees);
            }

            if (interaction != null) {
                currentCustomer.addInteraction(interaction);
                IdGenerator.syncInteractionId(id);
            }
            return currentCustomer;

        } else if ("DEAL".equalsIgnoreCase(section) && currentCustomer != null) {
            String id = data.get("id");
            String title = data.get("title");
            double amount = 0.0;
            try {
                amount = Double.parseDouble(data.getOrDefault("amount", "0.0"));
            } catch (NumberFormatException ignored) {}

            String stageStr = data.get("stage");
            DealStage stage = DealStage.NEW;
            if (stageStr != null) {
                try {
                    stage = DealStage.valueOf(stageStr);
                } catch (IllegalArgumentException ignored) {}
            }

            String closeDateStr = data.get("closeDate");
            LocalDate closeDate = (closeDateStr != null && !closeDateStr.isEmpty()) ? LocalDate.parse(closeDateStr) : LocalDate.now();

            Deal deal = new Deal(id, title, amount, closeDate);
            deal.setStage(stage);
            currentCustomer.addDeal(deal);
            IdGenerator.syncDealId(id);
            return currentCustomer;

        } else if ("FOLLOWUP".equalsIgnoreCase(section) && currentCustomer != null) {
            String id = data.get("id");
            String dueDateStr = data.get("dueDate");
            LocalDate dueDate = (dueDateStr != null && !dueDateStr.isEmpty()) ? LocalDate.parse(dueDateStr) : LocalDate.now();
            String description = data.get("description");
            boolean completed = Boolean.parseBoolean(data.getOrDefault("completed", "false"));

            FollowUp followUp = new FollowUp(id, dueDate, description);
            if (completed) {
                followUp.markCompleted();
            }
            currentCustomer.addFollowUp(followUp);
            IdGenerator.syncFollowUpId(id);
            return currentCustomer;
        }

        return currentCustomer;
    }

}
