package com.kencorhealth.campaign.dm.common;

public abstract class Input<T extends Identified> {
    // Override as needed
    public T convert() {
        return null;
    }
}
