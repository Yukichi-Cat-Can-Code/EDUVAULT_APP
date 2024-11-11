package com.example.eduvault_app.model;

public class TypeOfNotification {
    private int TYPENOTI_ID;
    private String TYPENOTI_NAME;

    public TypeOfNotification() {
    }

    public TypeOfNotification(int TYPENOTI_ID, String TYPENOTI_NAME) {
        this.TYPENOTI_ID = TYPENOTI_ID;
        this.TYPENOTI_NAME = TYPENOTI_NAME;
    }

    public int getTYPENOTI_ID() {
        return TYPENOTI_ID;
    }

    public void setTYPENOTI_ID(int TYPENOTI_ID) {
        this.TYPENOTI_ID = TYPENOTI_ID;
    }

    public String getTYPENOTI_NAME() {
        return TYPENOTI_NAME;
    }

    public void setTYPENOTI_NAME(String TYPENOTI_NAME) {
        this.TYPENOTI_NAME = TYPENOTI_NAME;
    }

    @Override
    public String toString() {
        return "TypeOfNotification{" +
                "TYPENOTI_ID=" + TYPENOTI_ID +
                ", TYPENOTI_NAME='" + TYPENOTI_NAME + '\'' +
                '}';
    }
}
