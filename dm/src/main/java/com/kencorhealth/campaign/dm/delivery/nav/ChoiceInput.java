package com.kencorhealth.campaign.dm.delivery.nav;

public class ChoiceInput extends InputBased {
    private String title;
    private String fieldValue;

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return
            "ChoiceItem{" + "title=" + title +
            ", fieldValue=" + fieldValue + "}, " + super.toString();
    }
}
