package com.example.eduvault_app.model;

import java.util.Date;

public class Notification {
    private int NOTI_ID;
    private int TYPENOTI_ID;
    private int USER_ID;
    private String NOTI_CONTENT;
    private byte NOTI_READ;
    private Date NOTI_TIME;

    public Notification() {
    }

    public Notification(int NOTI_ID, int TYPENOTI_ID, int USER_ID, String NOTI_CONTENT, byte NOTI_READ, Date NOTI_TIME) {
        this.NOTI_ID = NOTI_ID;
        this.TYPENOTI_ID = TYPENOTI_ID;
        this.USER_ID = USER_ID;
        this.NOTI_CONTENT = NOTI_CONTENT;
        this.NOTI_READ = NOTI_READ;
        this.NOTI_TIME = NOTI_TIME;
    }

    public int getNOTI_ID() {
        return NOTI_ID;
    }

    public void setNOTI_ID(int NOTI_ID) {
        this.NOTI_ID = NOTI_ID;
    }

    public int getTYPENOTI_ID() {
        return TYPENOTI_ID;
    }

    public void setTYPENOTI_ID(int TYPENOTI_ID) {
        this.TYPENOTI_ID = TYPENOTI_ID;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(int USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getNOTI_CONTENT() {
        return NOTI_CONTENT;
    }

    public void setNOTI_CONTENT(String NOTI_CONTENT) {
        this.NOTI_CONTENT = NOTI_CONTENT;
    }

    public byte getNOTI_READ() {
        return NOTI_READ;
    }

    public void setNOTI_READ(byte NOTI_READ) {
        this.NOTI_READ = NOTI_READ;
    }

    public Date getNOTI_TIME() {
        return NOTI_TIME;
    }

    public void setNOTI_TIME(Date NOTI_TIME) {
        this.NOTI_TIME = NOTI_TIME;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "NOTI_ID=" + NOTI_ID +
                ", TYPENOTI_ID=" + TYPENOTI_ID +
                ", USER_ID=" + USER_ID +
                ", NOTI_CONTENT='" + NOTI_CONTENT + '\'' +
                ", NOTI_READ=" + NOTI_READ +
                ", NOTI_TIME=" + NOTI_TIME +
                '}';
    }
}
