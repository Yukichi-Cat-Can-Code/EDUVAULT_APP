package com.example.eduvault_app.DAO;

import java.util.List;

public interface DAOInterface<T> {
    // Method to add a new record
    int add(T t);

    // Method to update an existing record
    int update(T t);

    // Method to delete a record
    int delete(int id);

    // Method to retrieve a record by ID
    T get(int id);

    // Method to retrieve all records
    List<T> getAll();
}

