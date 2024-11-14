package com.example.eduvault_app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;


public class CreateDocController {

    @FXML
    private TextField documentTXT, authorTXT, tagsTXT;

    @FXML
    private TextArea descriptionTXT;

    @FXML
    private ChoiceBox<String> typeChoiceBox, storingLocationChoiceBox;

    @FXML
    private Button Cancel_BTN;

    @FXML
    private Button Clear_BTN;

    @FXML
    private Button Create_BTN;

    @FXML
    private AnchorPane root;

    // Initialize data
    public void initialize() {
        // Add choices to typeChoiceBox
        typeChoiceBox.getItems().addAll("word", "excel", "pdf");

        // Populate storingLocationChoiceBox with data from the database
        // This should ideally call a database method (mock example here)
        storingLocationChoiceBox.getItems().addAll("Path1", "Path2", "Create New");

        // Add event to "Create New" option
        storingLocationChoiceBox.setOnAction(event -> {
            if ("Create New".equals(storingLocationChoiceBox.getValue())) {
                // Show a prompt or text field for new folder name
                System.out.println("Enter name for new folder.");
            }
        });

        // Set button actions
        Cancel_BTN.setOnAction(event -> resetFields());
        Clear_BTN.setOnAction(event -> clearFields());
        Create_BTN.setOnAction(event -> createDocument());
    }

    private void resetFields() {
        documentTXT.clear();
        authorTXT.clear();
        tagsTXT.clear();
        descriptionTXT.clear();
        typeChoiceBox.setValue(null);
        storingLocationChoiceBox.setValue(null);
    }

    private void clearFields() {
        documentTXT.clear();
        authorTXT.clear();
        tagsTXT.clear();
        descriptionTXT.clear();
    }

    private void createDocument() {
        if (documentTXT.getText().isEmpty() || typeChoiceBox.getValue() == null || storingLocationChoiceBox.getValue() == null) {
            System.out.println("Required fields are missing.");
            return;
        }

        String selectedLocation = storingLocationChoiceBox.getValue();
        if ("Create New".equals(selectedLocation)) {
            createNewFolder("newFolderName", "defaultParentDirectory"); // Placeholder
        } else {
            saveDocumentToFolder(selectedLocation);
        }

        clearFields();
    }

    private void createNewFolder(String folderName, String parentDirectory) {
        System.out.println("Creating new folder: " + folderName + " in " + parentDirectory);
        // Implement folder creation logic here
    }

    private void saveDocumentToFolder(String folder) {
        String documentName = documentTXT.getText();
        String type = typeChoiceBox.getValue();
        String filePath = folder + File.separator + documentName;

        try {
            if ("word".equalsIgnoreCase(type)) {
                saveWordDocument(filePath + ".docx");
            } else if ("pdf".equalsIgnoreCase(type)) {
                savePDFDocument(filePath + ".pdf");
            } else if ("excel".equalsIgnoreCase(type)) {
                saveExcelDocument(filePath + ".xlsx");
            }
        } catch (IOException | DocumentException e) {
            System.out.println("Error saving document: " + e.getMessage());
        }
    }

    private void saveWordDocument(String filePath) throws IOException {
        try (FileOutputStream out = new FileOutputStream(new File(filePath));
             XWPFDocument document = new XWPFDocument()) {

            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText("This is a test Word document.");

            document.write(out);
            System.out.println("Word document written successfully to " + filePath);
        }
    }

    private void savePDFDocument(String filePath) throws IOException, DocumentException {
        Document pdfDocument = new Document();
        try (FileOutputStream out = new FileOutputStream(new File(filePath))) {
            PdfWriter.getInstance(pdfDocument, out);
            pdfDocument.open();
            pdfDocument.add(new Paragraph("This is a test PDF document."));
            System.out.println("PDF document written successfully to " + filePath);
        } finally {
            pdfDocument.close();
        }
    }

    private void saveExcelDocument(String filePath) throws IOException {
        try (FileOutputStream out = new FileOutputStream(new File(filePath));
             XSSFWorkbook workbook = new XSSFWorkbook()) {

            XSSFSheet sheet = workbook.createSheet("Sheet1");
            sheet.createRow(0).createCell(0).setCellValue("This is a test Excel document.");

            workbook.write(out);
            System.out.println("Excel document written successfully to " + filePath);
        }
    }
}

