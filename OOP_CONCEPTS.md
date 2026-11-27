# Object-Oriented Programming (OOP) Concepts Map

**Course:** Object Oriented Techniques using Java (CCSE0355)  
**Project:** Console-Based Customer Relationship Management (CRM) System

This document maps the core OOP concepts directly to the files and classes in this codebase. It is written in simple, plain English to help during project defense and viva examinations.

---

## OOP Concepts Table

| OOP Concept | Where It Is Used (File & Class) | Plain English Explanation |
| :--- | :--- | :--- |
| **Abstraction** | [`Customer.java`](src/com/crm/model/Customer.java) (`abstract class Customer`) | Hides incomplete implementation details and defines abstract methods (`getCustomerType()`, `getDisplayDetails()`) that child classes must fulfill. |
| **Abstraction** | [`Interaction.java`](src/com/crm/model/Interaction.java) (`abstract class Interaction`) | Represents a general communication record with abstract methods `getType()` and `getSummary()` without assuming the communication channel. |
| **Inheritance** | [`IndividualCustomer.java`](src/com/crm/model/IndividualCustomer.java) (`extends Customer`) | Inherits common fields (id, name, email, phone) from `Customer` and reuses the parent constructor using `super(...)`. |
| **Inheritance** | [`BusinessCustomer.java`](src/com/crm/model/BusinessCustomer.java) (`extends Customer`) | Inherits core customer attributes while adding enterprise-specific fields like company name, GST number, and industry sector. |
| **Inheritance** | [`CallInteraction.java`](src/com/crm/model/CallInteraction.java), [`EmailInteraction.java`](src/com/crm/model/EmailInteraction.java), [`MeetingInteraction.java`](src/com/crm/model/MeetingInteraction.java) (`extends Interaction`) | Reuses the base `Interaction` class properties (id, date, notes) while specializing for phone calls, emails, and meetings. |
| **Inheritance** | All classes in [`com.crm.exception`](src/com/crm/exception/) (`extends Exception`) | Extends Java's built-in `Exception` superclass to create custom checked application exceptions. |
| **Polymorphism (Runtime / Method Overriding)** | [`IndividualCustomer.java`](src/com/crm/model/IndividualCustomer.java) & [`BusinessCustomer.java`](src/com/crm/model/BusinessCustomer.java) | Both override `getDisplayDetails()` and `getCustomerType()` to display their own tailored information at runtime. |
| **Polymorphism (Runtime / Dynamic Method Dispatch)** | [`Interaction.java`](src/com/crm/model/Interaction.java) (`toString()` method) | `Interaction.toString()` calls `getType()` and `getSummary()`, which execute the subclass version depending on whether it is a Call, Email, or Meeting. |
| **Polymorphism (Compile-Time / Method Overloading)** | [`ViewCustomerService.java`](src/com/crm/service/ViewCustomerService.java) | Provides two methods with the same name: `viewCustomer(String id)` for one customer and `viewCustomer()` for all customers. |
| **Polymorphism (Compile-Time / Method Overloading)** | [`SearchCustomerService.java`](src/com/crm/service/SearchCustomerService.java) | Overloads `search(String keyword)` for text search and `search(CustomerStatus status)` for status filtering. |
| **Polymorphism (Compile-Time / Method Overloading)** | [`SummaryReportService.java`](src/com/crm/service/SummaryReportService.java) | Overloads `generateReport(String customerId)` for a single customer and `generateReport()` for overall CRM stats. |
| **Encapsulation** | All model classes in [`com.crm.model`](src/com/crm/model/) (`Customer`, `Deal`, `FollowUp`, etc.) | Declares variables as `private` and provides controlled read/write access via `public` getter and setter methods. |
| **Encapsulation** | [`CustomerRepository.java`](src/com/crm/service/CustomerRepository.java) | Hides the internal `HashMap` data structure and exposes only high-level operations (`save`, `findById`, `remove`). |
| **Encapsulation** | [`IdGenerator.java`](src/com/crm/util/IdGenerator.java) | Keeps internal integer counters private so sequential IDs (`C001`, `I001`, etc.) cannot be corrupted by external classes. |
| **Encapsulation** | [`ConsoleHelper.java`](src/com/crm/util/ConsoleHelper.java) | Wraps standard `Scanner` input and formatting logic so caller classes don't handle scanner parsing directly. |
| **Interface** | [`Validatable.java`](src/com/crm/interfaces/Validatable.java) | Defines a contract `validate()` that domain models must implement to ensure data correctness before being stored. |
| **Interface** | [`Searchable.java`](src/com/crm/interfaces/Searchable.java) (`Searchable<T>`) | Defines a generic search contract with `List<T> search(String keyword)` implemented by `SearchCustomerService`. |
| **Interface** | [`Reportable.java`](src/com/crm/interfaces/Reportable.java) | Defines a contract `generateReport(String customerId)` implemented by `SummaryReportService`. |
| **Interface** | [`PendingFollowUpService.java`](src/com/crm/service/PendingFollowUpService.java) (`Comparator<FollowUp>`) | Implements Java's standard `Comparator` interface to sort follow-up reminders by due date. |
| **Composition & Aggregation** | [`Customer.java`](src/com/crm/model/Customer.java) | A `Customer` has-a list of `Interaction`, has-a list of `Deal`, and has-a list of `FollowUp` objects. |
| **Exception Handling** | All classes in [`com.crm.exception`](src/com/crm/exception/) & service layer | Uses `try-catch` blocks and `throws` declarations to handle checked exceptions gracefully without crashing the app. |
| **File I/O & Persistence** | [`FilePersistenceService.java`](src/com/crm/service/FilePersistenceService.java) | Uses standard Java streams (`BufferedReader`, `BufferedWriter`, `FileReader`, `FileWriter`) to save and restore full database state. |

---

## Why These Concepts Were Used

1. **Why Abstract Class for `Customer` instead of an Interface?**  
   Because both `IndividualCustomer` and `BusinessCustomer` share common state (id, name, email, phone, list of deals) and common behavior. An abstract class allows us to share code and avoid duplication, whereas an interface cannot hold instance state.

2. **Why Interfaces (`Validatable`, `Searchable`, `Reportable`)?**  
   Interfaces create clean, decoupled contracts. If tomorrow we add a new searchable entity (like searching Deals), `SearchDealService` can also implement `Searchable<Deal>` without changing existing code.

3. **Why Checked Exceptions?**  
   Checked exceptions force the calling layer (`MenuHandler`) to handle real-world business scenarios (such as an invalid ID entered by a user) and show friendly error messages.
