package com.kencorhealth.campaign.dm.exception;

public class CampaignException extends CampaignBasedException {
    public CampaignException() {
        super();
    }

    public CampaignException(String message) {
        super(message);
    }

    public CampaignException(String message, Throwable cause) {
        super(message, cause);
    }

    public CampaignException(Throwable cause) {
        super(cause);
    }

    public CampaignException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public int errorCode() {
        return 500;
    }
}
