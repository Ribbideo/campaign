package com.kencorhealth.campaign.dm.delivery.nav;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = TextInput.class, name = "text-input"),
    @JsonSubTypes.Type(value = ChoiceInput.class, name = "choice-input"),
    @JsonSubTypes.Type(value = FileInput.class, name = "file-input")
})
public abstract class InputBased extends DisplayBased {
    private String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "InputBased{" + "fieldName=" + fieldName + '}';
    }
}
