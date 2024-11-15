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
    private TextField DocName_TXT, Author_TXT, Tags_TXT;

    @FXML
    private TextArea Description_TXT;

    @FXML
    private ChoiceBox<String> DocType_ChoiceBox, FileLocation_ChoiceBox;

    @FXML
    private Button Cancel_BTN;

    @FXML
    private Button Clear_BTN;

    @FXML
    private Button Create_BTN;

    @FXML
    private AnchorPane root;

    @FXML
    private Label DocName_Required_Lbl;

    @FXML
    private Label Type_Required_Lbl;

    @FXML
    private Label FileLocation_Required_Lbl;

    // Initialize data
    public void initialize() {
        // Add choices to DocType_ChoiceBox
        DocType_ChoiceBox.getItems().addAll("word", "excel", "pdf");

        // Populate FileLocation_ChoiceBox with data from the database
        // This should ideally call a database method (mock example here)
        FileLocation_ChoiceBox.getItems().addAll("Path1", "Path2", "Create New");

        // Add event to "Create New" option
        FileLocation_ChoiceBox.setOnAction(event -> {
            if ("Create New".equals(FileLocation_ChoiceBox.getValue())) {
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
        DocName_TXT.clear();
        Author_TXT.clear();
        Tags_TXT.clear();
        Description_TXT.clear();
        DocType_ChoiceBox.setValue(null);
        FileLocation_ChoiceBox.setValue(null);
    }

    private void clearFields() {
        DocName_TXT.clear();
        Author_TXT.clear();
        Tags_TXT.clear();
        Description_TXT.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateRequiredFields() {
        boolean isValid = true;

        // Reset all indicators
        DocName_Required_Lbl.setVisible(false);
        Type_Required_Lbl.setVisible(false);
        FileLocation_Required_Lbl.setVisible(false);

        // Check Document Name
        if (DocName_TXT.getText().isEmpty()) {
            System.out.println(DocName_Required_Lbl.isVisible());
            DocName_Required_Lbl.setVisible(true);
            isValid = false;
        }

        // Check Document Type
        if (DocType_ChoiceBox.getValue() == null) {
            System.out.println(Type_Required_Lbl.isVisible());
            Type_Required_Lbl.setVisible(true);
            isValid = false;
        }

        // Check File Location
        if (FileLocation_ChoiceBox.getValue() == null) {
            System.out.println(FileLocation_Required_Lbl.isVisible());
            FileLocation_Required_Lbl.setVisible(true);
            isValid = false;
        }

        return isValid;
    }



    private void createDocument() {
        if (DocName_TXT.getText().isEmpty() || DocType_ChoiceBox.getValue() == null || FileLocation_ChoiceBox.getValue() == null) {
            System.out.println("Required fields are missing.");
            showAlert("Required fields are missing.");
            validateRequiredFields();
            return;
        }

//        String selectedLocation = FileLocation_ChoiceBox.getValue();
//        if ("Create New".equals(selectedLocation)) {
//            createNewFolder("newFolderName", "defaultParentDirectory"); // Placeholder
//        } else {
//            saveDocumentToFolder(selectedLocation);
//        }

        // Validate required fields
        if (!validateRequiredFields()) {
            System.out.println("Required fields are missing.");
            return;
        }

        saveDocumentToFolder("E:\\TestDocument");

        clearFields();
    }

    private void createFolder(String folderName) {
        File folder = new File("E:\\TestDocument\\" + folderName);

        if (folder.mkdir()) {
            System.out.println("Creating new folder: " + folderName + " successfully!");
        } else {
            System.out.println("The folder already exists!");
        }
    }

    private void createFolder(String folderName, String parentDirectory) {
        File folder = new File(parentDirectory + File.separator + folderName);

        if (folder.mkdir()) {
            System.out.println("Creating new folder: " + folderName + " in " + parentDirectory + " successfully!");
        } else {
            System.out.println("The folder already exists or couldn't be created!");
        }
    }

    private void createMultiFolder(String[] folderList) {
        for(String folder : folderList) {
            createFolder(folder);
        }
    }

    private void createMultipleFolderInAFolder(String folderName, String parentDirectory) {

        System.out.println("Creating folder: " + folderName + " in " + parentDirectory);
    }


    private void saveDocumentToFolder(String folder) {
        String documentName = DocName_TXT.getText();
        String type = DocType_ChoiceBox.getValue();
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
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(filePath));
            PdfWriter.getInstance(pdfDocument, out);
            pdfDocument.open();
            pdfDocument.add(new Paragraph("This is a test PDF document."));
            System.out.println("PDF document written successfully to " + filePath);
        } finally {
            if (pdfDocument.isOpen()) {
                pdfDocument.close();
            }
            if (out != null) {
                out.close();
            }
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

