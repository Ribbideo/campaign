package com.kencorhealth.campaign.dm.delivery.nav;

public class TextInput extends InputBased {
    private FieldType fieldType;
    private String title;
    private String hint;

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    public String toString() {
        return
            "TextInput{" + "title=" + title + ", hint=" + hint +
            ", fieldType=" + fieldType + "}, " + super.toString();
    }
}
