CRM System using Object-Oriented Java
Course: Object Oriented Techniques using Java (CCSE0355) | Group 20
Academic / Student Project (Pure Core Java, JDK 17+)

Architecture: Single-class / Single-feature per file:
  src/com/crm/app        Main, MenuHandler
  src/com/crm/model      Customer (abstract), IndividualCustomer, BusinessCustomer,
                         Interaction (abstract), CallInteraction, EmailInteraction, MeetingInteraction,
                         Deal, FollowUp, Enums (CustomerStatus, CustomerCategory, DealStage, InteractionType)
  src/com/crm/interfaces Reportable, Searchable<T>, Validatable
  src/com/crm/exception  CustomerNotFoundException, DuplicateCustomerException,
                         InvalidDealException, InvalidInputException
  src/com/crm/service    CustomerRepository (HashMap) + 16 decoupled feature services:
                         AddCustomer, ViewCustomer, SearchCustomer, UpdateCustomer,
                         DeleteCustomer, LogInteraction, InteractionHistory, TagCustomer,
                         ScheduleFollowUp, PendingFollowUp, CompleteFollowUp,
                         AddDeal, UpdateDealStage, SummaryReport, FilePersistence, DataSeed
  src/com/crm/util       InputValidator, ConsoleHelper, IdGenerator (with sync)
  src/com/crm/test       CRMVerificationTest (Automated test suite)
  data/                  Default persistent storage (crm_data.txt)

Documentation:
  README.md              Project guide, UML Mermaid diagram, walkthrough
  OOP_CONCEPTS.md        Table linking OOP concepts to code
  VIVA_QA.md             25 viva questions and 3-line answers

Compile:  javac -d out $(find src -name "*.java")
Run:      java -cp out com.crm.app.Main
Test:     java -cp out com.crm.test.CRMVerificationTest
