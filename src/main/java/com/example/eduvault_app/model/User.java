package com.example.eduvault_app.model;

import java.util.Date;

public class User {

    private int USER_ID;
    private String USERNAME;
    private String PASSWORD;
    private String EMAIL;
    private String FULLNAME;
    private String AVATAR;
    private Date USER_CREATEAT;

    public User() {
    }

    public User(int USER_ID, String USERNAME, String PASSWORD, String EMAIL, String FULLNAME, String AVATAR, Date USER_CREATEAT) {
        this.USER_ID = USER_ID;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.EMAIL = EMAIL;
        this.FULLNAME = FULLNAME;
        this.AVATAR = AVATAR;
        this.USER_CREATEAT = USER_CREATEAT;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(int USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getFULLNAME() {
        return FULLNAME;
    }

    public void setFULLNAME(String FULLNAME) {
        this.FULLNAME = FULLNAME;
    }

    public String getAVATAR() {
        return AVATAR;
    }

    public void setAVATAR(String AVATAR) {
        this.AVATAR = AVATAR;
    }

    public Date getUSER_CREATEAT() {
        return USER_CREATEAT;
    }

    public void setUSER_CREATEAT(Date USER_CREATEAT) {
        this.USER_CREATEAT = USER_CREATEAT;
    }

    @Override
    public String toString() {
        return "User{" +
                "USER_ID=" + USER_ID +
                ", USERNAME='" + USERNAME + '\'' +
                ", PASSWORD='" + PASSWORD + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                ", FULLNAME='" + FULLNAME + '\'' +
                ", AVATAR='" + AVATAR + '\'' +
                ", USER_CREATEAT=" + USER_CREATEAT +
                '}';
    }
}
