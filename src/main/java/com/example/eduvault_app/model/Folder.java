package com.example.eduvault_app.model;

import java.time.LocalDateTime;

public class Folder {
    private int FOLDER_ID;
    private int USER_ID;
    private int PARENT_ID;
    private String FOLDER_NAME;
    private LocalDateTime FOLDER_CREATEAT;
    private short isDeleted;

    public Folder() {
    }

    public Folder(int FOLDER_ID, int USER_ID, int PARENT_ID, String FOLDER_NAME, LocalDateTime FOLDER_CREATEAT, short isDeleted) {
        this.FOLDER_ID = FOLDER_ID;
        this.USER_ID = USER_ID;
        this.PARENT_ID = PARENT_ID;
        this.FOLDER_NAME = FOLDER_NAME;
        this.FOLDER_CREATEAT = FOLDER_CREATEAT;
        this.isDeleted = isDeleted;
    }

    public Folder(int i, String folderName, int i1, int i2) {
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

    public int getPARENT_ID() {
        return PARENT_ID;
    }

    public void setPARENT_ID(int PARENT_ID) {
        this.PARENT_ID = PARENT_ID;
    }

    public String getFOLDER_NAME() {
        return FOLDER_NAME;
    }

    public void setFOLDER_NAME(String FOLDER_NAME) {
        this.FOLDER_NAME = FOLDER_NAME;
    }

    public LocalDateTime getFOLDER_CREATEAT() {
        return FOLDER_CREATEAT;
    }

    public void setFOLDER_CREATEAT(LocalDateTime FOLDER_CREATEAT) {
        this.FOLDER_CREATEAT = FOLDER_CREATEAT;
    }

    public short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "FOLDER_ID=" + FOLDER_ID +
                ", USER_ID=" + USER_ID +
                ", PARENT_ID=" + PARENT_ID +
                ", FOLDER_NAME='" + FOLDER_NAME + '\'' +
                ", FOLDER_CREATEAT='" + FOLDER_CREATEAT + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}




