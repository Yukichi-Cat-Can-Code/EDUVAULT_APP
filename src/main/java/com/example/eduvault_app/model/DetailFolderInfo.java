package com.example.eduvault_app.model;

import java.time.LocalDateTime;

public class DetailFolderInfo {
    private int FOLDER_ID;
    private String FOLDER_NAME;
    private String FOLDER_PATH;
    private int USER_ID;
    private int PARENT_ID;
    private LocalDateTime FOLDER_CREATEAT;
    private int isDeleted;

    private String PARENT_NAME;
    private String FULLNAME;
    private String SUMMARY;
    private String FolderName;

    public DetailFolderInfo() {
    }

    public DetailFolderInfo(String folderName, String SUMMARY, int isDeleted, LocalDateTime FOLDER_CREATEAT, int PARENT_ID, int USER_ID, String FOLDER_PATH, String FOLDER_NAME, int FOLDER_ID) {
        FolderName = folderName;
        this.SUMMARY = SUMMARY;
        this.isDeleted = isDeleted;
        this.FOLDER_CREATEAT = FOLDER_CREATEAT;
        this.PARENT_ID = PARENT_ID;
        this.USER_ID = USER_ID;
        this.FOLDER_PATH = FOLDER_PATH;
        this.FOLDER_NAME = FOLDER_NAME;
        this.FOLDER_ID = FOLDER_ID;
    }

    public DetailFolderInfo(int FOLDER_ID, String FOLDER_NAME, int PARENT_ID, int USER_ID,String FULLNAME, LocalDateTime FOLDER_CREATEAT) {

        this.FULLNAME = FULLNAME;
        this.FOLDER_ID = FOLDER_ID;
        this.FOLDER_NAME = FOLDER_NAME;
        this.PARENT_ID = PARENT_ID;
        this.USER_ID = USER_ID;
        this.FOLDER_CREATEAT = FOLDER_CREATEAT;
    }


    public int getFOLDER_ID() {
        return FOLDER_ID;
    }

    public void setFOLDER_ID(int FOLDER_ID) {
        this.FOLDER_ID = FOLDER_ID;
    }

    public String getFOLDER_NAME() {
        return FOLDER_NAME;
    }

    public void setFOLDER_NAME(String FOLDER_NAME) {
        this.FOLDER_NAME = FOLDER_NAME;
    }

    public String getFOLDER_PATH() {
        return FOLDER_PATH;
    }

    public void setFOLDER_PATH(String FOLDER_PATH) {
        this.FOLDER_PATH = FOLDER_PATH;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(int USER_ID) {
        this.USER_ID = USER_ID;
    }

    public int getPARENT_ID() {
        return PARENT_ID;
    }

    public void setPARENT_ID(int PARENT_ID) {
        this.PARENT_ID = PARENT_ID;
    }

    public LocalDateTime getFOLDER_CREATEAT() {
        return FOLDER_CREATEAT;
    }

    public void setFOLDER_CREATEAT(LocalDateTime FOLDER_CREATEAT) {
        this.FOLDER_CREATEAT = FOLDER_CREATEAT;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getSUMMARY() {
        return SUMMARY;
    }

    public void setSUMMARY(String SUMMARY) {
        this.SUMMARY = SUMMARY;
    }

    public String getFolderName() {
        return FolderName;
    }

    public void setFolderName(String folderName) {
        FolderName = folderName;
    }

    public String getFULL_NAME() {
        return FULLNAME;
    }

    public void setFULL_NAME(String FULLNAME) {
        this.FULLNAME = FULLNAME;
    }

    public String getPARENT_NAME() {
        return PARENT_NAME;
    }

    public void setPARENT_NAME(String PARENT_NAME) {
        this.PARENT_NAME = PARENT_NAME;
    }



    @Override
    public String toString() {
        return "DetailFolderInfo{" +
                "FOLDER_ID=" + FOLDER_ID +
                ", FOLDER_NAME='" + FOLDER_NAME + '\'' +
                ", FOLDER_PATH='" + FOLDER_PATH + '\'' +
                ", USER_ID=" + USER_ID +
                ", PARENT_ID=" + PARENT_ID +
                ", FOLDER_CREATEAT=" + FOLDER_CREATEAT +
                ", isDeleted=" + isDeleted +
                ", PARENT_NAME='" + PARENT_NAME + '\'' +
                ", FULL_NAME='" + FULLNAME + '\'' +
                ", SUMMARY='" + SUMMARY + '\'' +
                ", FolderName='" + FolderName + '\'' +
                '}';
    }
}
