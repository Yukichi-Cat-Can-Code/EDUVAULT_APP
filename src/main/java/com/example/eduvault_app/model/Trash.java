package com.example.eduvault_app.model;

import java.util.Date;

public class Trash {
    private int TRASH_ID;
    private int ITEM_ID;
    private String ITEM_TYPE;
    private Date TRASH_DELETEAT;
    private String  itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Trash() {
    }

    public Trash(int TRASH_ID, int ITEM_ID, String ITEM_TYPE, Date TRASH_DELETEAT) {
        this.TRASH_ID = TRASH_ID;
        this.ITEM_ID = ITEM_ID;
        this.ITEM_TYPE = ITEM_TYPE;
        this.TRASH_DELETEAT = TRASH_DELETEAT;
    }

    public Trash(int TRASH_ID, String itemName, String ITEM_TYPE, Date TRASH_DELETEAT) {
        this.TRASH_ID = TRASH_ID;
        this.itemName = itemName;
        this.ITEM_TYPE = ITEM_TYPE;
        this.TRASH_DELETEAT = TRASH_DELETEAT;
    }

    public int getTRASH_ID() {
        return TRASH_ID;
    }

    public void setTRASH_ID(int TRASH_ID) {
        this.TRASH_ID = TRASH_ID;
    }

    public int getITEM_ID() {
        return ITEM_ID;
    }

    public void setITEM_ID(int ITEM_ID) {
        this.ITEM_ID = ITEM_ID;
    }

    public String getITEM_TYPE() {
        return ITEM_TYPE;
    }

    public void setITEM_TYPE(String ITEM_TYPE) {
        this.ITEM_TYPE = ITEM_TYPE;
    }

    public Date getTRASH_DELETEAT() {
        return TRASH_DELETEAT;
    }

    public void setTRASH_DELETEAT(Date TRASH_DELETEAT) {
        this.TRASH_DELETEAT = TRASH_DELETEAT;
    }

    @Override
    public String toString() {
        return "Trash{" +
                "TRASH_ID=" + TRASH_ID +
                ", ITEM_ID=" + ITEM_ID +
                ", ITEM_TYPE='" + ITEM_TYPE + '\'' +
                ", TRASH_DELETEAT=" + TRASH_DELETEAT +
                '}';
    }
}
