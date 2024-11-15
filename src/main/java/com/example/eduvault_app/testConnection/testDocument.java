package com.example.eduvault_app.testConnection;

import com.example.eduvault_app.controller.DocumentController;
import com.example.eduvault_app.model.Document;
import java.time.LocalDateTime;

public class testDocument {
    public static void main(String[] args) {
        DocumentController documentController = new DocumentController();

//        // Example: Add a new document
        Document newDocument = new Document(1, 1, 1, 1, "Document1", "Summary of Document1", LocalDateTime.now(), "/path/to/document1", (short) 0);
//        int addResult = documentController.addDocument(newDocument);
//        System.out.println("Add Document Result: " + addResult);

//        // Set new values to update
//        newDocument.setDOC_NAME("Updated Document1");
//        newDocument.setSUMMARY("Updated Summary of Document1");
//        newDocument.setDOC_PATH("/new/path/to/document1");
//
//        // Call the update method
//        int updateResult = documentController.updateDocument(newDocument);
//        System.out.println("Update Document Result: " + updateResult);
//
        // Example: Get a document by ID
//        Document retrievedDocument = documentController.getDocument(1);
//        System.out.println("Retrieved Document: " + retrievedDocument);
//
        // Example: Get all documents
//        System.out.println("All Documents:");
//        for (Document doc : documentController.getAllDocuments()) {
//            System.out.println(doc);
//        }
//
        // Example: Delete a document by ID
        int deleteResult = documentController.deleteDocument(1);
        System.out.println("Delete Document Result: " + deleteResult);
    }
}

