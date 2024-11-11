package com.example.eduvault_app.model;

import java.util.Date;

public class Document {
    private int DOC_ID;
    private int FOLDER_ID;
    private int USSR_ID;
    private int TYPEDOC_ID;
    private String DOC_NAME;
    private String SUMMARY;
    private Date CREATEDATE;
    private String DOC_PATH;

    public Document() {
    }

    public Document(int DOC_ID, int FOLDER_ID, int USSR_ID, int TYPEDOC_ID, String DOC_NAME, String SUMMARY, Date CREATEDATE, String DOC_PATH) {
        this.DOC_ID = DOC_ID;
        this.FOLDER_ID = FOLDER_ID;
        this.USSR_ID = USSR_ID;
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

    public int getUSSR_ID() {
        return USSR_ID;
    }

    public void setUSSR_ID(int USSR_ID) {
        this.USSR_ID = USSR_ID;
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

    public Date getCREATEDATE() {
        return CREATEDATE;
    }

    public void setCREATEDATE(Date CREATEDATE) {
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
                ", USSR_ID=" + USSR_ID +
                ", TYPEDOC_ID=" + TYPEDOC_ID +
                ", DOC_NAME='" + DOC_NAME + '\'' +
                ", SUMMARY='" + SUMMARY + '\'' +
                ", CREATEDATE=" + CREATEDATE +
                ", DOC_PATH='" + DOC_PATH + '\'' +
                '}';
    }
}
