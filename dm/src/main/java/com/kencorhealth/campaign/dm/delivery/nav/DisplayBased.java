package com.kencorhealth.campaign.dm.delivery.nav;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kencorhealth.campaign.dm.common.Transformable;
import com.kencorhealth.campaign.dm.delivery.PdfDisplay;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = InputBased.class, name = "input"),
    @JsonSubTypes.Type(value = PdfDisplay.class, name = "url")
})
public abstract class DisplayBased implements Transformable {
    // Empty
}
