# Viva Preparation - 25 Likely Questions & Simple Answers

**Course:** Object Oriented Techniques using Java (CCSE0355)  
**Project:** Core Java Console-Based CRM System

This guide prepares you for common questions asked by examiners during project evaluation and viva voce. Each answer is kept to approximately 3 lines for quick recall and crisp delivery.

---

### Q1: What is the purpose of this project?
**Answer:**  
It is a modular, console-based Customer Relationship Management (CRM) system written in pure Core Java. It allows small businesses to manage customer profiles, log communications (calls, emails, meetings), track sales deals, and schedule follow-up tasks.

---

### Q2: What core OOP concepts are demonstrated in this project?
**Answer:**  
The project demonstrates all fundamental OOP pillars: Abstraction (abstract Customer and Interaction classes), Inheritance (Individual and Business customers), Polymorphism (overriding display details and overloading search/report methods), and Encapsulation (private fields with getters/setters).

---

### Q3: Why did you use an abstract class for Customer instead of an interface?
**Answer:**  
`Customer` contains shared state (like `customerId`, `name`, `email`, `phone`, and lists of deals/interactions) and common methods. In Java, interfaces cannot store instance state; an abstract class allows child classes to share this common code without duplication.

---

### Q4: How is Inheritance used in this project?
**Answer:**  
`IndividualCustomer` and `BusinessCustomer` inherit from the abstract `Customer` class using the `extends` keyword. They reuse base fields and call `super(...)` in their constructors while adding their own specific fields like `address` or `gstNumber`.

---

### Q5: Where is Runtime Polymorphism (Method Overriding) used?
**Answer:**  
Both `IndividualCustomer` and `BusinessCustomer` override the abstract `getDisplayDetails()` and `getCustomerType()` methods. At runtime, Java invokes the specific subclass implementation depending on the actual customer object stored in the repository.

---

### Q6: Where is Compile-Time Polymorphism (Method Overloading) used?
**Answer:**  
In `ViewCustomerService`, `viewCustomer(String id)` retrieves one customer, while `viewCustomer()` retrieves all customers. Similarly, `SearchCustomerService` overloads `search(String keyword)` and `search(CustomerStatus status)`.

---

### Q7: What is Encapsulation, and how is it applied here?
**Answer:**  
Encapsulation means bundling data and methods together while restricting direct access to object internals. All model variables are declared `private` and accessed exclusively through `public` getters, setters, and validation methods.

---

### Q8: Why did you choose HashMap in CustomerRepository?
**Answer:**  
`HashMap<String, Customer>` stores customers keyed by their unique `customerId`. It provides an average constant-time $O(1)$ performance for lookup, insertion, and deletion, making customer retrieval instantaneous.

---

### Q9: Why did you follow a "one-feature-per-service" architecture?
**Answer:**  
It follows the Single Responsibility Principle (SRP) from SOLID design. Each service class (e.g., `AddCustomerService`, `DeleteCustomerService`) has only one reason to change, making the code clean, easy to test, and simple for students to explain.

---

### Q10: How does MenuHandler interact with the service classes?
**Answer:**  
`MenuHandler` uses Constructor Dependency Injection to receive `CustomerRepository` and `ConsoleHelper`. It acts as an orchestrator, reading user input from the console and delegating the business logic to the appropriate service class.

---

### Q11: Why did you create custom checked exceptions instead of using RuntimeException?
**Answer:**  
Custom exceptions like `CustomerNotFoundException` and `DuplicateCustomerException` make domain errors explicit and self-documenting. Being checked exceptions (extending `Exception`), Java forces callers to handle them gracefully with `try-catch`.

---

### Q12: How does the system prevent duplicate customers?
**Answer:**  
In `AddCustomerService`, the system first checks `repository.exists(customerId)` to reject duplicate IDs. Then, it iterates through all registered customers to ensure that no two customers share the same email address (case-insensitive).

---

### Q13: What is the purpose of the Validatable interface?
**Answer:**  
`Validatable` defines a contract with a `void validate()` method. Classes like `Customer` implement it to verify their own fields (non-empty name, 10-digit phone, valid email) before the record is saved to memory or disk.

---

### Q14: What is the Searchable<T> interface and why is it generic?
**Answer:**  
`Searchable<T>` defines a generic search contract with `List<T> search(String keyword)`. Using generics allows the interface to be reused for searching any entity type (customers, deals, or interactions) without code duplication.

---

### Q15: How are customer interactions modeled?
**Answer:**  
`Interaction` is an abstract class with concrete subclasses `CallInteraction`, `EmailInteraction`, and `MeetingInteraction`. This allows `Customer` to maintain a single `List<Interaction>` polymorphically storing any type of communication.

---

### Q16: How does the Deal feature enforce business rules?
**Answer:**  
`AddDealService` verifies that deal titles are not empty and amounts are strictly positive ($> 0$). Additionally, `UpdateDealStageService` blocks any modifications if a deal has already reached a final closed stage (`WON` or `LOST`).

---

### Q17: How are pending follow-ups sorted?
**Answer:**  
`PendingFollowUpService` collects all incomplete follow-ups across all customers and sorts them using `Collections.sort()` with an anonymous `Comparator<FollowUp>`. This orders reminders chronologically by their `dueDate`.

---

### Q18: How does FilePersistenceService save and load data?
**Answer:**  
It reads and writes text using Java's standard `BufferedReader` and `BufferedWriter`. It formats records under clear section headers like `[CUSTOMER]`, `[INTERACTION]`, and `[DEAL]`, reconstructing objects without external libraries.

---

### Q19: Why do you escape newlines and '=' characters during file persistence?
**Answer:**  
The file storage uses a `key=value` format where each line represents one field. If user notes contain `=` or newline `\n` characters, it could corrupt file parsing; escaping them ensures notes can contain any characters safely.

---

### Q20: How does IdGenerator avoid generating duplicate IDs after loading a file?
**Answer:**  
`IdGenerator` maintains static integer counters for `C`, `I`, `D`, and `F` prefixes. Whenever records are loaded from a file or seeded, helper methods like `syncCustomerId()` parse the numeric part and raise the counter so new IDs never clash.

---

### Q21: How is email validation implemented in InputValidator?
**Answer:**  
`InputValidator.isValidEmail()` checks that the email has no spaces, contains exactly one `@` symbol not at the edges, and has a dot with at least two characters after it for a valid domain extension (e.g., `.com`).

---

### Q22: What is the role of DataSeedService?
**Answer:**  
`DataSeedService` provides realistic sample data (2 individual customers, 2 business customers, multiple interactions, deals, and follow-ups). It allows quick demonstration and testing during viva without typing everything manually.

---

### Q23: How does ConsoleHelper make the console user-friendly?
**Answer:**  
It handles `Scanner` input inside `while(true)` loops and catches `NumberFormatException`, so entering letters where numbers are expected does not crash the program. It also adds a "Press Enter to continue" pause after long outputs.

---

### Q24: What is the difference between Composition and Inheritance in this project?
**Answer:**  
Inheritance represents an "is-a" relationship (e.g., `IndividualCustomer` is-a `Customer`). Composition represents a "has-a" relationship (e.g., `Customer` has-a `List<Deal>` and has-a `List<Interaction>`).

---

### Q25: If you had more time, what would you improve in this project?
**Answer:**  
I would replace the text file persistence with a relational database (like SQLite or PostgreSQL using JDBC), build a graphical user interface (GUI) using JavaFX or a web dashboard, and add role-based authentication (Admin vs Sales Agent).
