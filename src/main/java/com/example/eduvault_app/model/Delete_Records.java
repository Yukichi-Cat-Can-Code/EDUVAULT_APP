package com.example.eduvault_app.model;

public class Delete_Records {
    private int DOC_ID;
    private int TRASH_ID;
    private int FOLDER_ID;
    private byte IS_DELETED;

    public Delete_Records() {
    }

    public Delete_Records(int DOC_ID, int TRASH_ID, int FOLDER_ID, byte IS_DELETED) {
        this.DOC_ID = DOC_ID;
        this.TRASH_ID = TRASH_ID;
        this.FOLDER_ID = FOLDER_ID;
        this.IS_DELETED = IS_DELETED;
    }

    public int getDOC_ID() {
        return DOC_ID;
    }

    public void setDOC_ID(int DOC_ID) {
        this.DOC_ID = DOC_ID;
    }

    public int getTRASH_ID() {
        return TRASH_ID;
    }

    public void setTRASH_ID(int TRASH_ID) {
        this.TRASH_ID = TRASH_ID;
    }

    public int getFOLDER_ID() {
        return FOLDER_ID;
    }

    public void setFOLDER_ID(int FOLDER_ID) {
        this.FOLDER_ID = FOLDER_ID;
    }

    public byte getIS_DELETED() {
        return IS_DELETED;
    }

    public void setIS_DELETED(byte IS_DELETED) {
        this.IS_DELETED = IS_DELETED;
    }

    @Override
    public String toString() {
        return "Delete_Records{" +
                "DOC_ID=" + DOC_ID +
                ", TRASH_ID=" + TRASH_ID +
                ", FOLDER_ID=" + FOLDER_ID +
                ", IS_DELETED=" + IS_DELETED +
                '}';
    }
}
