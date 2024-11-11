package com.example.eduvault_app.model;

public class Folder {
    private int FOLDER_ID;
    private int USER_ID;
    private String FOLDER_NAME;
    private String FOLDER_CREATEAT;

    public Folder() {
    }

    public Folder(int FOLDER_ID, int USER_ID, String FOLDER_NAME, String FOLDER_CREATEAT) {
        this.FOLDER_ID = FOLDER_ID;
        this.USER_ID = USER_ID;
        this.FOLDER_NAME = FOLDER_NAME;
        this.FOLDER_CREATEAT = FOLDER_CREATEAT;
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

    public String getFOLDER_NAME() {
        return FOLDER_NAME;
    }

    public void setFOLDER_NAME(String FOLDER_NAME) {
        this.FOLDER_NAME = FOLDER_NAME;
    }

    public String getFOLDER_CREATEAT() {
        return FOLDER_CREATEAT;
    }

    public void setFOLDER_CREATEAT(String FOLDER_CREATEAT) {
        this.FOLDER_CREATEAT = FOLDER_CREATEAT;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "FOLDER_ID=" + FOLDER_ID +
                ", USER_ID=" + USER_ID +
                ", FOLDER_NAME='" + FOLDER_NAME + '\'' +
                ", FOLDER_CREATEAT='" + FOLDER_CREATEAT + '\'' +
                '}';
    }
}
