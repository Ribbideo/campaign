package com.kencorhealth.campaign.dm.delivery.nav;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kencorhealth.campaign.dm.delivery.UrlDisplay;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = InputBased.class, name = "input"),
    @JsonSubTypes.Type(value = UrlDisplay.class, name = "url")
})
public abstract class DisplayBased {
    // Empty
}