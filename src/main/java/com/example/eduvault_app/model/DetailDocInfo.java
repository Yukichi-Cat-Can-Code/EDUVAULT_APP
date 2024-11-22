package com.example.eduvault_app.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DetailDocInfo {
    //Important
    private int DOC_ID;
    private String DOC_NAME;
    private LocalDateTime CREATEDATE;
    private String DOC_PATH;
    private short isDeleted;
    private String TYPEDOC_NAME;
    private String FULLNAME;
    private String FOLDER_NAME;
    private int TYPEDOC_ID;

    //Not very Important
    private int FOLDER_ID;
    private int USER_ID;
    private String SUMMARY;
    private int FOLDER_PARENT_ID;



    public DetailDocInfo() {
    }

    public DetailDocInfo(int DOC_ID, String DOC_NAME, LocalDateTime CREATEDATE, String DOC_PATH, short isDeleted, String TYPEDOC_NAME, String FULLNAME, String FOLDER_NAME) {
        this.DOC_ID = DOC_ID;
        this.DOC_NAME = DOC_NAME;
        this.CREATEDATE = CREATEDATE;
        this.DOC_PATH = DOC_PATH;
        this.isDeleted = isDeleted;
        this.TYPEDOC_NAME = TYPEDOC_NAME;
        this.FULLNAME = FULLNAME;
        this.FOLDER_NAME = FOLDER_NAME;
    }

    public DetailDocInfo(int DOC_ID, String DOC_NAME, int TYPEDOC_ID, Timestamp CREATEDATE, int USER_ID, String FULLNAME, String TYPEDOC_NAME) {
        this.DOC_ID = DOC_ID;
        this.DOC_NAME = DOC_NAME;
        this.TYPEDOC_ID = TYPEDOC_ID;
        this.CREATEDATE = CREATEDATE.toLocalDateTime();
        this.USER_ID = USER_ID;
        this.FULLNAME = FULLNAME;
        this.TYPEDOC_NAME = TYPEDOC_NAME;
    }

    public DetailDocInfo(int DOC_ID, String DOC_NAME, int TYPEDOC_ID, Timestamp CREATEDATE, int USER_ID, String FULLNAME, String TYPEDOC_NAME, String DOC_PATH, String FOLDER_NAME) {
        this.DOC_ID = DOC_ID;
        this.DOC_NAME = DOC_NAME;
        this.TYPEDOC_ID = TYPEDOC_ID;
        this.CREATEDATE = CREATEDATE.toLocalDateTime();
        this.USER_ID = USER_ID;
        this.FULLNAME = FULLNAME;
        this.TYPEDOC_NAME = TYPEDOC_NAME;
        this.DOC_PATH = DOC_PATH;
        this.FOLDER_NAME = FOLDER_NAME;
    }

    public int getDOC_ID() {
        return DOC_ID;
    }

    public void setDOC_ID(int DOC_ID) {
        this.DOC_ID = DOC_ID;
    }

    public String getDOC_NAME() {
        return DOC_NAME;
    }

    public void setDOC_NAME(String DOC_NAME) {
        this.DOC_NAME = DOC_NAME;
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

    public short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getTYPEDOC_NAME() {
        return TYPEDOC_NAME;
    }

    public void setTYPEDOC_NAME(String TYPEDOC_NAME) {
        this.TYPEDOC_NAME = TYPEDOC_NAME;
    }

    public String getFULLNAME() {
        return FULLNAME;
    }

    public void setFULLNAME(String FULLNAME) {
        this.FULLNAME = FULLNAME;
    }

    public String getFOLDER_NAME() {
        return FOLDER_NAME;
    }

    public void setFOLDER_NAME(String FOLDER_NAME) {
        this.FOLDER_NAME = FOLDER_NAME;
    }

    public int getTYPEDOC_ID() {
        return TYPEDOC_ID;
    }

    public void setTYPEDOC_ID(int TYPEDOC_ID) {
        this.TYPEDOC_ID = TYPEDOC_ID;
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

    public int getFOLDER_PARENT_ID() {
        return FOLDER_PARENT_ID;
    }

    public void setFOLDER_PARENT_ID(int FOLDER_PARENT_ID) {
        this.FOLDER_PARENT_ID = FOLDER_PARENT_ID;
    }

    public String getSUMMARY() {
        return SUMMARY;
    }

    public void setSUMMARY(String SUMMARY) {
        this.SUMMARY = SUMMARY;
    }

    @Override
    public String toString() {
        return "DetailDocInfo{" +
                "DOC_ID=" + DOC_ID +
                ", DOC_NAME='" + DOC_NAME + '\'' +
                ", CREATEDATE=" + CREATEDATE +
                ", DOC_PATH='" + DOC_PATH + '\'' +
                ", isDeleted=" + isDeleted +
                ", TYPEDOC_NAME='" + TYPEDOC_NAME + '\'' +
                ", FULLNAME='" + FULLNAME + '\'' +
                ", FOLDER_NAME='" + FOLDER_NAME + '\'' +
                ", TYPEDOC_ID=" + TYPEDOC_ID +
                ", FOLDER_ID=" + FOLDER_ID +
                ", USER_ID=" + USER_ID +
                ", SUMMARY='" + SUMMARY + '\'' +
                ", FOLDER_PARENT_ID=" + FOLDER_PARENT_ID +
                '}';
    }
}
