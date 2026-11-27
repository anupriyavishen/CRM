package com.crm.interfaces;

import com.crm.exception.InvalidInputException;

/**
 * Validatable defines a contract for domain entities that can check their own field validity.
 * OOP Concept: Interface & Abstraction - guarantees that any class implementing Validatable
 * has a validate() method, enabling polymorphic validation before saving or processing.
 */
public interface Validatable {

    // Validates the internal state of the entity; throws InvalidInputException if any field is invalid
    void validate() throws InvalidInputException;

}
