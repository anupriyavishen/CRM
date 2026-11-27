package com.crm.app;

import com.crm.service.CustomerRepository;
import com.crm.util.ConsoleHelper;

/**
 * Main is the entry point of the CRM application.
 * OOP Concept: Program Initialization & Dependency Injection - instantiates the
 * shared CustomerRepository and ConsoleHelper, passing them into MenuHandler to begin execution.
 */
public class Main {

    // Main execution method called by the JVM
    public static void main(String[] args) {
        CustomerRepository repository = new CustomerRepository();
        ConsoleHelper console = new ConsoleHelper();
        MenuHandler menuHandler = new MenuHandler(repository, console);
        menuHandler.showMainMenu();
    }

}
