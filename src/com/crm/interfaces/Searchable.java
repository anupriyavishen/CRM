package com.crm.interfaces;

import java.util.List;

/**
 * Searchable defines a generic contract for searching entities by a text keyword.
 * OOP Concept: Interface & Generics - provides a reusable template Searchable<T>
 * where T is the type of object being searched and returned.
 */
public interface Searchable<T> {

    // Searches for entities matching the provided text keyword
    List<T> search(String keyword);

}
