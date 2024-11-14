package com.example.eduvault_app.model;

import java.time.LocalDateTime;

public class Document {
    private int DOC_ID;
    private int FOLDER_ID;
    private int USER_ID;
    private int TYPEDOC_ID;
    private String DOC_NAME;
    private String SUMMARY;
    private LocalDateTime CREATEDATE;
    private String DOC_PATH;

    public Document() {
    }

    public Document(int DOC_ID, int FOLDER_ID, int USER_ID, int TYPEDOC_ID, String DOC_NAME, String SUMMARY, LocalDateTime CREATEDATE, String DOC_PATH) {
        this.DOC_ID = DOC_ID;
        this.FOLDER_ID = FOLDER_ID;
        this.USER_ID = USER_ID;
        this.TYPEDOC_ID = TYPEDOC_ID;
        this.DOC_NAME = DOC_NAME;
        this.SUMMARY = SUMMARY;
        this.CREATEDATE = CREATEDATE;
        this.DOC_PATH = DOC_PATH;
    }

    public int getDOC_ID() {
        return DOC_ID;
    }

    public void setDOC_ID(int DOC_ID) {
        this.DOC_ID = DOC_ID;
    }

    public int getFOLDER_ID() {
        return FOLDER_ID;
    }

    public void setFOLDER_ID(int FOLDER_ID) {
        this.FOLDER_ID = FOLDER_ID;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(int USER_ID) {
        this.USER_ID = USER_ID;
    }

    public int getTYPEDOC_ID() {
        return TYPEDOC_ID;
    }

    public void setTYPEDOC_ID(int TYPEDOC_ID) {
        this.TYPEDOC_ID = TYPEDOC_ID;
    }

    public String getDOC_NAME() {
        return DOC_NAME;
    }

    public void setDOC_NAME(String DOC_NAME) {
        this.DOC_NAME = DOC_NAME;
    }

    public String getSUMMARY() {
        return SUMMARY;
    }

    public void setSUMMARY(String SUMMARY) {
        this.SUMMARY = SUMMARY;
    }

    public LocalDateTime getCREATEDATE() {
        return CREATEDATE;
    }

    public void setCREATEDATE(LocalDateTime CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }

    public String getDOC_PATH() {
        return DOC_PATH;
    }

    public void setDOC_PATH(String DOC_PATH) {
        this.DOC_PATH = DOC_PATH;
    }

    @Override
    public String toString() {
        return "Document{" +
                "DOC_ID=" + DOC_ID +
                ", FOLDER_ID=" + FOLDER_ID +
                ", USER_ID=" + USER_ID +
                ", TYPEDOC_ID=" + TYPEDOC_ID +
                ", DOC_NAME='" + DOC_NAME + '\'' +
                ", SUMMARY='" + SUMMARY + '\'' +
                ", CREATEDATE=" + CREATEDATE +
                ", DOC_PATH='" + DOC_PATH + '\'' +
                '}';
    }
}

