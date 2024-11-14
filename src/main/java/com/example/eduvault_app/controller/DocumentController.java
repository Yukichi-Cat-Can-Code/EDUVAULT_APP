package com.example.eduvault_app.controller;

import com.example.eduvault_app.DAO.DocumentDAO;
import com.example.eduvault_app.model.Document;
import java.util.List;

public class DocumentController {
    private final DocumentDAO documentDAO = new DocumentDAO();

    // Method to add a new document
    public int addDocument(Document document) {
        return documentDAO.add(document);
    }

    // Method to update an existing document
    public int updateDocument(Document document) {
        return documentDAO.update(document);
    }

    // Method to delete a document by ID
    public int deleteDocument(int docId) {
        return documentDAO.delete(docId);
    }

    // Method to get a document by ID
    public Document getDocument(int docId) {
        return documentDAO.get(docId);
    }

    // Method to retrieve all documents
    public List<Document> getAllDocuments() {
        return documentDAO.getAll();
    }
}

