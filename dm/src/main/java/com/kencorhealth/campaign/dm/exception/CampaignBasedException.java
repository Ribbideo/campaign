package com.kencorhealth.campaign.dm.exception;

public abstract class CampaignBasedException extends Exception {
    public CampaignBasedException() {
        super();
    }

    public CampaignBasedException(String message) {
        super(message);
    }

    public CampaignBasedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CampaignBasedException(Throwable cause) {
        super(cause);
    }

    public CampaignBasedException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    public abstract int errorCode();
}
