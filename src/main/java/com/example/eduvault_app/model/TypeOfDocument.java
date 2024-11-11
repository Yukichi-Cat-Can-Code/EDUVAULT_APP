package com.example.eduvault_app.model;

public class TypeOfDocument {
    private int TYPEDOC_ID;
    private String TYPEDOC_NAME;
    private String TYPEDOC_DESCRIPTION;

    public TypeOfDocument() {
    }

    public TypeOfDocument(int TYPEDOC_ID, String TYPEDOC_NAME, String TYPEDOC_DESCRIPTION) {
        this.TYPEDOC_ID = TYPEDOC_ID;
        this.TYPEDOC_NAME = TYPEDOC_NAME;
        this.TYPEDOC_DESCRIPTION = TYPEDOC_DESCRIPTION;
    }

    public int getTYPEDOC_ID() {
        return TYPEDOC_ID;
    }

    public void setTYPEDOC_ID(int TYPEDOC_ID) {
        this.TYPEDOC_ID = TYPEDOC_ID;
    }

    public String getTYPEDOC_NAME() {
        return TYPEDOC_NAME;
    }

    public void setTYPEDOC_NAME(String TYPEDOC_NAME) {
        this.TYPEDOC_NAME = TYPEDOC_NAME;
    }

    public String getTYPEDOC_DESCRIPTION() {
        return TYPEDOC_DESCRIPTION;
    }

    public void setTYPEDOC_DESCRIPTION(String TYPEDOC_DESCRIPTION) {
        this.TYPEDOC_DESCRIPTION = TYPEDOC_DESCRIPTION;
    }

    @Override
    public String toString() {
        return "TypeOfDocument{" +
                "TYPEDOC_ID=" + TYPEDOC_ID +
                ", TYPEDOC_NAME='" + TYPEDOC_NAME + '\'' +
                ", TYPEDOC_DESCRIPTION='" + TYPEDOC_DESCRIPTION + '\'' +
                '}';
    }
}
