package com.crm.util;

/**
 * IdGenerator generates sequential, zero-padded unique IDs (like C001, I001, D001, F001).
 * OOP Concept: Encapsulation - keeps static counter variables private so other classes
 * can only obtain newly formatted IDs or synchronize counters without corrupting state.
 */
public class IdGenerator {

    // Internal counters for each type of entity
    private static int customerCounter = 0;
    private static int interactionCounter = 0;
    private static int dealCounter = 0;
    private static int followUpCounter = 0;

    // Generates the next customer ID (for example: C001, C002)
    public static String nextCustomerId() {
        customerCounter++;
        return String.format("C%03d", customerCounter);
    }

    // Generates the next interaction ID (for example: I001, I002)
    public static String nextInteractionId() {
        interactionCounter++;
        return String.format("I%03d", interactionCounter);
    }

    // Generates the next deal ID (for example: D001, D002)
    public static String nextDealId() {
        dealCounter++;
        return String.format("D%03d", dealCounter);
    }

    // Generates the next follow-up ID (for example: F001, F002)
    public static String nextFollowUpId() {
        followUpCounter++;
        return String.format("F%03d", followUpCounter);
    }

    // Ensures customerCounter stays higher than any existing customer ID loaded from file
    public static void syncCustomerId(String id) {
        if (id != null && id.startsWith("C")) {
            try {
                int num = Integer.parseInt(id.substring(1));
                if (num > customerCounter) {
                    customerCounter = num;
                }
            } catch (NumberFormatException ignored) {}
        }
    }

    // Ensures interactionCounter stays higher than any existing interaction ID loaded from file
    public static void syncInteractionId(String id) {
        if (id != null && id.startsWith("I")) {
            try {
                int num = Integer.parseInt(id.substring(1));
                if (num > interactionCounter) {
                    interactionCounter = num;
                }
            } catch (NumberFormatException ignored) {}
        }
    }

    // Ensures dealCounter stays higher than any existing deal ID loaded from file
    public static void syncDealId(String id) {
        if (id != null && id.startsWith("D")) {
            try {
                int num = Integer.parseInt(id.substring(1));
                if (num > dealCounter) {
                    dealCounter = num;
                }
            } catch (NumberFormatException ignored) {}
        }
    }

    // Ensures followUpCounter stays higher than any existing follow-up ID loaded from file
    public static void syncFollowUpId(String id) {
        if (id != null && id.startsWith("F")) {
            try {
                int num = Integer.parseInt(id.substring(1));
                if (num > followUpCounter) {
                    followUpCounter = num;
                }
            } catch (NumberFormatException ignored) {}
        }
    }

}
